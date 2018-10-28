package com.prime.dnuifood.Fragments


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prime.dnuifood.Beans.FoodBean

import com.prime.dnuifood.R
import com.prime.dnuifood.Server
import kotlinx.android.synthetic.main.fragment_insert_order.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread


class InsertOrderFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_insert_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var num = 1
        var sum = num * foodBean.price
        tv_num.text = "$num"
        tv_sum.text = "$sum"
        rb_decnum.setOnClickListener {
            if (num > 1)
                num--
            tv_num.text = "$num"
            sum = num * foodBean.price
            tv_sum.text = "$sum"
        }
        rb_addnum.setOnClickListener {
            num++
            tv_num.text = "$num"
            sum = num * foodBean.price
            tv_sum.text = "$sum"
        }
        rb_cancel.setOnClickListener {
            dismiss()
        }
        rb_ok.setOnClickListener {
            val addr = et_addr.text.toString()
            if (addr != "")
                doAsync {
                    val re = Server.insertOrder(usr_id, foodBean.food_id, num, sum, addr)
                    uiThread {
                        if (re.success == "1")
                            toast("下单成功").also { dismiss() }
                        else
                            toast("下单失败")
                    }
                }
            else
                toast("地址不能为空")
        }
        super.onViewCreated(view, savedInstanceState)
    }

    lateinit var foodBean: FoodBean
    lateinit var usr_id: String
}
