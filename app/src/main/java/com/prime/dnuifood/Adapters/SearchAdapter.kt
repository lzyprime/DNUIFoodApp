package com.prime.dnuifood.Adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.prime.dnuifood.Fragments.SearchFragment
import com.prime.dnuifood.R
import kotlinx.android.synthetic.main.search_item.view.*

class SearchAdapter(val search: SearchFragment,val history: List<String>) : RecyclerView.Adapter<SearchAdapter.Holder>() {

    override fun getItemCount(): Int = history.size
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder =
        Holder(View.inflate(p0.context, R.layout.search_item,null))

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        with(p0.itemView) {
            tv_histroy.text = history[p1]
            tv_histroy.setOnClickListener {
                search.setQuery(history[p1])
            }
            if (p1 == history.size - 1) {
                rb_clearHistroy.visibility = View.VISIBLE
                rb_clearHistroy.setOnClickListener {
                    search.clearHistory()
                }
            }
        }
    }
    inner class Holder(view : View) : RecyclerView.ViewHolder(view)
}