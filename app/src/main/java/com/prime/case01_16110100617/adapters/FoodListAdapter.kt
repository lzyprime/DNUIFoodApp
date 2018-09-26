package com.prime.case01_16110100617.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.prime.case01_16110100617.OneFoodActivity
import com.prime.case01_16110100617.R
import com.prime.case01_16110100617.beans.Foodbean
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.food_item.view.*

class FoodListAdapter(var foodlist : List<Foodbean>) : RecyclerView.Adapter<FoodListAdapter.ViewHolder>(){

    override fun getItemCount(): Int = foodlist.size
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FoodListAdapter.ViewHolder =
            ViewHolder(View.inflate(p0.context, R.layout.food_item,null))

    override fun onBindViewHolder(holder: FoodListAdapter.ViewHolder, positon: Int) {
        var show = foodlist[positon]
        with(holder.itemView){
            tv_foodname.text = show.foodname
            tv_foodintro.text = show.intro
            tv_price.text = "￥${show.price}"
            if(show.recommand.equals("1"))
                tv_recommand.text = "推荐！"
            Picasso.get().load("http://172.24.10.175:8080/foodService${show.pic}").into(iv_food)
            setOnClickListener {
                context.startActivity(Intent(context, OneFoodActivity::class.java)
                        .putExtra("food_id",show.food_id)
                        .putExtra("pic",show.pic))
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}