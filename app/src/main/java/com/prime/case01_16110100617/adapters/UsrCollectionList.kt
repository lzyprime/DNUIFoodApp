package com.prime.case01_16110100617.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.prime.case01_16110100617.R
import com.prime.case01_16110100617.beans.CollectionshopfoodBean
import kotlinx.android.synthetic.main.food_item.view.*
import kotlinx.android.synthetic.main.shop_item.view.*

class UsrCollectionList(var showlist: List<CollectionshopfoodBean>) : RecyclerView.Adapter<UsrCollectionList.ViewHolder>() {

    override fun getItemCount(): Int = showlist.size

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        if (showlist[0].flag.equals("0"))
            return ViewHolder(View.inflate(p0.context, R.layout.shop_item, null))
        else
            return ViewHolder(View.inflate(p0.context, R.layout.food_item, null))
    }

    override fun onBindViewHolder(holder : ViewHolder, p1: Int) {
        var show = showlist[p1]
        if(show.flag.equals("0")) {
            with(holder.itemView) {
                com.squareup.picasso.Picasso.get().load("http://172.24.10.175:8080/foodService${show.pic}").into(iv_shop)
                tv_shopname.text = show.shopname
                tv_adress.text = show.address

                setOnClickListener {
                    context.startActivity(android.content.Intent(context, com.prime.case01_16110100617.OneShopActivity::class.java)
                            .putExtra("shop_id", show.shop_id)
                            .putExtra("shopname",show.shopname))
                }
            }
        }
        else
        {
            with(holder.itemView){
                tv_foodname.text = show.foodname
                tv_price.text = "￥${show.price}"
                com.squareup.picasso.Picasso.get().load("http://172.24.10.175:8080/foodService${show.pic}").into(iv_food)
                setOnClickListener {
                    context.startActivity(android.content.Intent(context, com.prime.case01_16110100617.OneFoodActivity::class.java)
                            .putExtra("food_id",show.food_id))
                }
            }
        }
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}