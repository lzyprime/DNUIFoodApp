package com.prime.case01_16110100617.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.prime.case01_16110100617.R
import com.prime.case01_16110100617.beans.ContentBean
import kotlinx.android.synthetic.main.content_item.view.*

class ContentListAdapter(var contentlist : List<ContentBean>) : RecyclerView.Adapter<ContentListAdapter.ViewHolder>(){

    override fun getItemCount(): Int = contentlist.size
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ContentListAdapter.ViewHolder =
           ViewHolder(View.inflate(p0.context, R.layout.content_item,null))


    override fun onBindViewHolder(holder: ContentListAdapter.ViewHolder, positon: Int) {
        var show = contentlist[positon]
        with(holder.itemView){
            tv_usrname.text = show.username
            tv_date.text = show.comment_time
            tv_content.text = show.content
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}