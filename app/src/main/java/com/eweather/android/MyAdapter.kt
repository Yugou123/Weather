package com.eweather.android

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.list_item.view.*

class MyAdapter(var context:Context?,var myList:ArrayList<String>,private val onClick:(String)->Unit) :RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    class MyViewHolder(itemView:View,val onClick:(String)->Unit): ViewHolder(itemView) {

        var textItem:TextView?=null
        init {
            textItem = itemView.findViewById(R.id.item_text)
            itemView.setOnClickListener {
                onClick(it.findViewById<TextView>(R.id.item_text).text.toString())
                Log.d("VH","onclick")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(view,onClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textItem?.text = myList[position]
    }

    override fun getItemCount(): Int {
        return myList.size
    }
}