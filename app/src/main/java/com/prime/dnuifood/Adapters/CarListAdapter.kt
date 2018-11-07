package com.prime.dnuifood.Adapters

import android.preference.CheckBoxPreference
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.prime.dnuifood.Beans.CarListBean
import com.prime.dnuifood.R
import com.prime.dnuifood.Server
import com.prime.dnuifood.ShopCarActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_shop_car.*
import kotlinx.android.synthetic.main.cart_item.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.okButton

class CarListAdapter(var carts: MutableList<CarListBean>, val usr_id: String, val activity: ShopCarActivity) :
    RecyclerView.Adapter<CarListAdapter.ViewHolder>() {
    override fun getItemCount(): Int = carts.size
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(View.inflate(p0.context, R.layout.cart_item, null))

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val onecar = carts[p1]
        with(p0.itemView)
        {
            Picasso.get().load(Server.BaseUrl + onecar.pic).into(imageView)
            tv_foodname.text = onecar.foodname
            tv_price.text = "￥${onecar.price}"
            tv_num.text = onecar.num.toString()
            tv_sum.text = "总金额：￥${onecar.price * onecar.num}"
            rb_addnum.setOnClickListener {
                carts[p1].num++
                tv_num.text = carts[p1].num.toString()
                tv_sum.text = "总金额：￥${carts[p1].price * carts[p1].num}"
            }
            rb_decnum.setOnClickListener {
                if (carts[p1].num > 1) {
                    carts[p1].num--
                    tv_num.text = carts[p1].num.toString()
                    tv_sum.text = "总金额：￥${carts[p1].price * carts[p1].num}"
                }
            }

            tv_shopname.text = onecar.shopname
            cb_cart.visibility = if (showcb) View.VISIBLE else View.INVISIBLE
            cb_cart.isChecked = onecar.ischecked
            cb_cart.setOnClickListener {
                carts[p1].ischecked = cb_cart.isChecked
                if (!cb_cart.isChecked)
                    activity.cb_selectall.isChecked = false
                else if (carts.count { it.ischecked } == carts.size)
                    activity.cb_selectall.isChecked = true
            }

            tv_delete.setOnClickListener {
                activity.alert {
                    title = "确认删除"
                    okButton {
                        activity.deleteCarItem(onecar.item_id)
                        carts.removeAt(p1)
                        notifyDataSetChanged()
                    }
                    cancelButton { it.dismiss() }
                }.show()
            }

            setOnLongClickListener {
                showcb = true
                notifyDataSetChanged()
                true
            }
        }

    }

    var showcb = false

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}