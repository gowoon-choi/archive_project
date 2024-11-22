package com.nexters.fullstack.base

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexters.fullstack.util.DiffUtilCallback

abstract class BaseAdapter<ITEM : Any> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected val items = mutableListOf<ITEM>()
    private var itemClickListener : ((View, Int, ITEM?) -> Unit)? = null

    override fun getItemCount(): Int {
        return items.size
    }

    fun clearItems(){
        items.clear()
    }

    fun addItem(item: ITEM) {
        items.add(item)
        notifyDataSetChanged()
    }

    fun addItems(items: List<ITEM>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): ITEM {
        return items[position]
    }

    fun calDiff(newItems : MutableList<ITEM>){
        val diffUtilCallback = DiffUtilCallback(items, newItems)
        val diffResult : DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtilCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setItemClickListener(itemClickListener : (View, Int, ITEM?) -> Unit){
        this.itemClickListener = itemClickListener
    }

    fun getItemClickListener() = itemClickListener
}