package com.prime.dnuifood.Adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.prime.dnuifood.Beans.ShopBean
import com.prime.dnuifood.R
import com.prime.dnuifood.Server
import com.prime.dnuifood.ShopActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.shop_item.view.*
import org.jetbrains.anko.startActivity

class ShopListAdapter(var shops: List<ShopBean>) : RecyclerView.Adapter<ShopListAdapter.ViewHolder>() {
    override fun getItemCount(): Int = shops.size

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(View.inflate(p0.context, R.layout.shop_item, null))

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val shop = shops[p1]
        with(p0.itemView) {
            Picasso.get().load(Server.BaseUrl + shop.pic).into(iv_shopimage)
            tv_shopname.setText(shop.shopname)
            tv_shopaddr.setText(shop.address)
            tv_shopcomment.setText(shop.comment)
            tv_shopintro.setText(shop.intro)
            tv_shophone.setText(shop.phonenum)
            rb_shoplevel.rating = shop.level
            setOnClickListener {
                context.startActivity<ShopActivity>("shopname" to shop.shopname, "shop_id" to shop.shop_id)
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}