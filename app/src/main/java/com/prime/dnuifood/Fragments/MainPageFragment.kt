package com.prime.dnuifood.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.prime.dnuifood.Adapters.ShopListAdapter
import com.prime.dnuifood.Beans.ShopBean
import com.prime.dnuifood.R
import com.prime.dnuifood.Retrofits.Retrofits
import kotlinx.android.synthetic.main.fragment_main_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPageFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_main_page,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       rv_mainpage.layoutManager = GridLayoutManager(context,1)
        Retrofits.doGetAllShops(callback)
    }
    private val callback = object : Callback<List<ShopBean>> {
        override fun onResponse(call: Call<List<ShopBean>>, response: Response<List<ShopBean>>) {
                rv_mainpage.adapter = ShopListAdapter(response.body() ?: emptyList())
        }

        override fun onFailure(call: Call<List<ShopBean>>, t: Throwable) {
            show("网络出错")
        }
    }

    private fun show(text : String = "") {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}