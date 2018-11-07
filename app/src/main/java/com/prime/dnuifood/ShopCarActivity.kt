package com.prime.dnuifood

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.MenuRes
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.prime.dnuifood.Adapters.CarListAdapter
import com.prime.dnuifood.Beans.CarListBean
import kotlinx.android.synthetic.main.activity_shop_car.*
import org.jetbrains.anko.*
import org.jetbrains.anko.coroutines.experimental.asReference
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange

class ShopCarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_car)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        rv_shopcarlist.layoutManager = GridLayoutManager(this, 1)
        carList()
        cb_selectall.setOnClickListener {
            if (cb_selectall.isChecked) selectallitem()
            else selectallitem(false)
        }

        bt_delete.setOnClickListener {
            if (rvadapter.carts.count { it.ischecked } == 0)
                toast("未选中任何项")
            else
                alert {
                    title = "确认删除"
                    okButton {
                        for (i in rvadapter.carts)
                            deleteCarItem(i.item_id)
                        rvadapter.carts.removeIf { it.ischecked }
                        rvadapter.notifyDataSetChanged()
                    }
                    cancelButton { it.dismiss() }
                }.show()

        }

        bt_enter.setOnClickListener {
            if (rvadapter.carts.count { it.ischecked } == 0)
                toast("未选中任何项")
            else {
                var sum = 0.0
                for (i in rvadapter.carts)
                    if (i.ischecked)
                        sum += i.price * i.num
                alert {
                    title = "确认订单"
                    var a = ""
                    customView {
                        linearLayout {
                            textView("总金额：￥$sum")
                            editText {
                                hint = "收货地址"
                                addTextChangedListener(object : TextWatcher {
                                    override fun afterTextChanged(s: Editable?) {
                                        a = s?.toString()?: ""
                                    }

                                    override fun beforeTextChanged(
                                        s: CharSequence?,
                                        start: Int,
                                        count: Int,
                                        after: Int
                                    ) {
                                    }

                                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                                })
                            }
                        }
                    }
                    okButton { toast(a) }
                    cancelButton { it.dismiss() }
                }.show()
            }
        }
    }

    private fun carList() = doAsync {
        val res = Server.getMyCartByUser(usr_id)
        uiThread {
            rvadapter = CarListAdapter(res.toMutableList(), usr_id, this@ShopCarActivity)
            rv_shopcarlist.adapter = rvadapter
        }
    }

    fun deleteCarItem(item_id: String) = doAsync {
        Server.deleteCartItem(item_id)
        uiThread {
        }
    }

    private fun selectallitem(v: Boolean = true) {
        rvadapter.showcb = v
        for (i in rvadapter.carts)
            i.ischecked = v
        rvadapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }


    private val share get() = getSharedPreferences("DNUIFood", Activity.MODE_PRIVATE)
    private val usr_id get() = share.getString("usr_id", "")
    private lateinit var rvadapter: CarListAdapter
}
