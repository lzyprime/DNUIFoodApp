package com.prime.dnuifood

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.prime.dnuifood.Adapters.CommentListAdaper
import com.prime.dnuifood.Beans.FoodBean
import com.prime.dnuifood.Fragments.AddCartAlert
import com.prime.dnuifood.Fragments.InsertOrderAlert
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_food.*
import org.jetbrains.anko.*

class FoodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        rv_foodcomment.layoutManager = GridLayoutManager(this@FoodActivity, 1)
        commentList()
        showfood()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        rb_insertorder.setOnClickListener { InsertOrderAlert(this, food, usr_id).create().show() }
        rb_addcart.setOnClickListener { AddCartAlert(this,usr_id,food_id).create().show() }
    }

    private fun updateCollect() = doAsync {
        val result = Server.isCollected(usr_id, food_id, "0")
        uiThread {
            if (result.collected == "1")
                menu!!.findItem(R.id.isCollected).setIcon(R.drawable.ic_collected)
            else
                menu!!.findItem(R.id.isCollected).setIcon(R.drawable.ic_notcollected)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.iscollected, menu)
        this.menu = menu
        updateCollect()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.isCollected -> doAsync {
                val result = Server.collectFood(usr_id, food_id)
                uiThread {
                    if (result.success == "1")
                        updateCollect()
                }
            }
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun commentList() = doAsync {
        val commentlist = Server.getAllCommentsByFood(food_id)
        uiThread {
            rv_foodcomment.adapter = CommentListAdaper(usr_id, commentlist, food_id)
        }
    }

    fun showfood() = doAsync {
        food = Server.getFoodById(food_id)
        uiThread {
            Picasso.get().load(Server.BaseUrl + food.pic).into(iv_foodimage)
            tv_foodname.text = food.foodname
            tv_foodprice.text = "￥${food.price}"
            tv_intro.text = food.intro
            it.title = food.foodname
        }
    }

    private val food_id get() = intent.getStringExtra("food_id")
    private val share get() = getSharedPreferences("DNUIFood", Activity.MODE_PRIVATE)
    private val usr_id get() = share.getString("usr_id", "")
    private var menu: Menu? = null
    private lateinit var food: FoodBean
}
