package com.prime.case01_16110100617.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.prime.case01_16110100617.R
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.searchlog_item.view.*

class SearchLogAdapter(var logs : List<String>) : RecyclerView.Adapter<SearchLogAdapter.ViewHolder>(){

    override fun getItemCount(): Int =logs.size
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder =
            ViewHolder(View.inflate(p0.context, R.layout.searchlog_item,null))

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        with(p0.itemView){
            tv_searchlog.text = logs[p1]
        }
    }
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
}