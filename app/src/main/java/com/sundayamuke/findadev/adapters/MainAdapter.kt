package com.sundayamuke.findadev.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sundayamuke.findadev.databinding.ItemDevBinding
import com.sundayamuke.findadev.databinding.ItemUserBinding
import com.sundayamuke.findadev.model.Dev
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val VIEW_TYPE_HEADER = 0
private const val VIEW_TYPE_ITEM = 1

class ItemClickListener(
    val profileClickListener: (dev: Dev) -> Unit,
    val devClickListener: (dev: Dev) -> Unit
) {
    fun profileClick(dev: Dev) = profileClickListener(dev)
    fun devClick(dev: Dev) = devClickListener(dev)
}

class MainAdapter(
    private val user: Dev,
    private val clickListener: ItemClickListener
)
    : ListAdapter<DataItem, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_ITEM -> DevViewHolder.from(parent)
            VIEW_TYPE_HEADER -> UserViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when(holder) {
            is DevViewHolder -> {
                val devItem = getItem(position) as DataItem.DevReportItem
                holder.bind(devItem.dev, clickListener)
            }

            is UserViewHolder -> {
                holder.bind(user, clickListener)
            }
        }
    }

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<Dev>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.User)
                else -> listOf(DataItem.User) + list.map {
                    DataItem.DevReportItem(it)
                }
            }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }


    override fun submitList(list: List<DataItem>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.User -> VIEW_TYPE_HEADER
            is DataItem.DevReportItem -> VIEW_TYPE_ITEM
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

    class DevViewHolder private constructor(private var binding: ItemDevBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(dev: Dev, clickListener: ItemClickListener) {
            binding.dev = dev
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): DevViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDevBinding.inflate(layoutInflater, parent, false)
                return DevViewHolder(binding)
            }
        }
    }

    class UserViewHolder private constructor(private var binding: ItemUserBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(dev: Dev, clickListener: ItemClickListener) {
            binding.dev = dev
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
                return UserViewHolder(binding)
            }
        }
    }
}

/**
 *A sealed class is a class with all it subclasses defined in it.
 * As a result, no other subclass of DataItem can be created elsewhere
 * */
sealed class DataItem {
    data class DevReportItem(val dev: Dev): DataItem() {
        override val name: String = dev.fullName
    }

    object User: DataItem() {
        override val name: String = ""
    }

    abstract val name: String
}