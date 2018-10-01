package com.prime.case01_16110100617.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.prime.case01_16110100617.CommentActivity
import com.prime.case01_16110100617.OneFoodActivity
import com.prime.case01_16110100617.R
import com.prime.case01_16110100617.beans.ContentBean
import kotlinx.android.synthetic.main.usrorder_item.view.*

class UsrOrderAdapter(var showlist : List<ContentBean>) : RecyclerView.Adapter<UsrOrderAdapter.ViewHolder>() {
    override fun getItemCount(): Int = showlist.size
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder =
            ViewHolder(View.inflate(p0.context, R.layout.usrorder_item,null))

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        with(p0.itemView){
            order_tv_foodname.text = showlist[p1].foodname
            order_tv_shopname.text = showlist[p1].shopname
            order_tv_num.text = "数量：${showlist[p1].num}"
            order_tv_sum.text = "总价：${showlist[p1].sum}"
            bt_comment.setOnClickListener { context.startActivity(Intent(context,CommentActivity::class.java).putExtra("mod","add").putExtra("item_id",showlist[p1].item_id))}
            setOnClickListener { context.startActivity(Intent(context,OneFoodActivity::class.java).putExtra("food_id",showlist[p1].food_id).putExtra("foodprice",showlist[p1].price)) }
        }

    }
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
}