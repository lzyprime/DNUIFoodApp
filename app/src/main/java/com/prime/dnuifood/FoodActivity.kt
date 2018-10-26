package com.prime.dnuifood

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.prime.dnuifood.Adapters.CommentListAdaper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_food.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.uiThread

class FoodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        rv_foodcomment.layoutManager = GridLayoutManager(this@FoodActivity, 1)
        commentList()
        showfood()
//        rb_insertorder.onClick {
//                alert {
//                    title = "订单信息"
//                }
//        }
    }



    fun commentList() = doAsync {
        val commentlist = Server.getAllCommentsByFood(food_id)
        uiThread {
            rv_foodcomment.adapter = CommentListAdaper(usr_id, commentlist)
        }
    }

    fun showfood() = doAsync {
            val food = Server.getFoodById(food_id)
        uiThread {
            Picasso.get().load(Server.BaseUrl + food.pic).into(iv_foodimage)
            tv_foodname.text = food.foodname
            tv_foodprice.text = "￥${food.price}"
            tv_intro.text = food.intro
            it.title = food.foodname
            if (food.recommand == "0")
                tv_recommand.visibility = View.INVISIBLE
        }
    }

    private val food_id get() = intent.getStringExtra("food_id")
    private val share get() = getSharedPreferences("DNUIFood",Activity.MODE_PRIVATE)
    private val usr_id get() = share.getString("usr_id", "")
}
