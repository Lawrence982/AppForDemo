package com.netcracker.appfordemo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.netcracker.appfordemo.databinding.RvItemRepositoryBinding
import com.netcracker.appfordemo.uimodel.UserDevice

/**
 * Created by mozil on 19.01.2018.
 */
class RepositoryRecyclerViewAdapter(private var items: ArrayList<UserDevice>,
                                    private var listener: OnItemClickListener)
    : RecyclerView.Adapter<RepositoryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = RvItemRepositoryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun replaceData(arrayList: ArrayList<UserDevice>) {
        items = arrayList
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: RvItemRepositoryBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(devices: UserDevice, listener: OnItemClickListener?) {
            binding.device = devices
            if (listener != null) {
                binding.root.setOnClickListener({ _ -> listener.onItemClick(layoutPosition) })
            }

            binding.executePendingBindings()
        }
    }

}