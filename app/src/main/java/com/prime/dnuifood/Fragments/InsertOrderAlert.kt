package com.prime.dnuifood.Fragments

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AlertDialogLayout
import android.view.View
import com.prime.dnuifood.Beans.FoodBean
import com.prime.dnuifood.R
import com.prime.dnuifood.Server
import kotlinx.android.synthetic.main.insert_order_alert.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class InsertOrderAlert(context: Context,food : FoodBean,usr_id : String) : AlertDialog.Builder(context) {
    init {
        setTitle("确认订单")
        val view = View.inflate(context, R.layout.insert_order_alert, null)
        var num = 1
        var sum = food.price
        with(view) {
            tv_num.text = "$num"
            tv_sum.text = "$sum"
            rb_decnum.setOnClickListener {
                if (num > 1) num--
                tv_num.text = "$num"
                sum = num * food.price
                tv_sum.text = "$sum"
            }
            rb_addnum.setOnClickListener {
                num++
                tv_num.text = "$num"
                sum = num * food.price
                tv_sum.text = "$sum"
            }
        }
        setView(view)
        setPositiveButton("确认") { dialogInterface: DialogInterface, i: Int ->
            val addr = view.et_addr.text.toString()
            if (addr != "")
                doAsync {
                    val re = Server.insertOrder(usr_id,food.food_id,num,sum,addr)
                    uiThread {
                        if(re.success == "1")
                            context.toast("购买成功")
                        else
                            context.toast("购买失败")
                    }
                }
            else
                context.toast("地址不能为空")
        }
        setNegativeButton("取消",null)
    }
}