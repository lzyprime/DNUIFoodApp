package com.prime.dnuifood.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prime.dnuifood.Adapters.ShopListAdapter
import com.prime.dnuifood.R
import com.prime.dnuifood.Server
import kotlinx.android.synthetic.main.fragment_main_page.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPageFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_main_page,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       rv_mainpage.layoutManager = GridLayoutManager(context,1)
        getAllShops()
    }

    fun getAllShops() = doAsync {
        val shops = Server.getAllShops()
        uiThread {
            rv_mainpage.adapter = ShopListAdapter(shops)
        }
    }
}