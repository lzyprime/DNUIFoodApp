package com.prime.dnuifood

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.prime.dnuifood.Adapters.FoodListAdapter
import com.prime.dnuifood.Beans.CollectedBean
import com.prime.dnuifood.Beans.FoodBean
import com.prime.dnuifood.Beans.SuccessBean
import kotlinx.android.synthetic.main.activity_shop.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        title = shopname
        rv_foodlist.layoutManager = GridLayoutManager(this@ShopActivity, 2)
        getFoodByShop()
    }

    private fun getFoodByShop() = doAsync {
        val foods = Server.getFoodByShop(shop_id)
        uiThread {
            rv_foodlist.adapter = FoodListAdapter(foods)
        }
    }

    private fun updateCollect() = doAsync {
            val result = Server.isCollected(usr_id,shop_id,"0")
        uiThread {
            if(result.collected == "1")
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
        if (item?.itemId == R.id.isCollected)
            doAsync {
                val result = Server.collectShop(usr_id,shop_id)
                uiThread {
                    if (result.success == "1")
                        updateCollect()
                }
            }
        return super.onOptionsItemSelected(item)
    }

    private val share get() = getSharedPreferences("DNUIFood", Activity.MODE_PRIVATE)
    private val usr_id get() = share.getString("usr_id", "")
    private val shop_id get() = intent.getStringExtra("shop_id")
    private val shopname get() = intent.getStringExtra("shopname")
    private var menu: Menu? = null
}
