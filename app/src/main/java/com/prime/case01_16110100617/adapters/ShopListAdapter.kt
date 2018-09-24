package com.prime.case01_16110100617.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.prime.case01_16110100617.OneShopActivity
import com.prime.case01_16110100617.R
import com.prime.case01_16110100617.beans.ShopBean
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.shop_item.view.*

class ShopListAdapter(var shoplist: List<ShopBean>) : RecyclerView.Adapter<ShopListAdapter.ViewHolder>() {

    override fun getItemCount(): Int = shoplist.size
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ShopListAdapter.ViewHolder =
            ViewHolder(View.inflate(p0.context, R.layout.shop_item, null))

    override fun onBindViewHolder(holder: ShopListAdapter.ViewHolder, positon: Int) {
        var show = shoplist[positon]
        with(holder.itemView) {
            Picasso.get().load("http://172.24.10.175:8080/foodService${show.pic}").into(iv_shop)
            tv_shopname.text = show.shopname
            tv_intro.text = show.intro
            tv_adress.text = show.address
            shopratingBar.rating = show.level.toFloat()

            setOnClickListener {
                context.startActivity(Intent(context, OneShopActivity::class.java)
                        .putExtra("shop_id", show.shop_id)
                        .putExtra("shopname",show.shopname))
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}