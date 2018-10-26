package com.prime.dnuifood.Fragments

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prime.dnuifood.Adapters.CollectListAdapter
import com.prime.dnuifood.R
import com.prime.dnuifood.Server
import kotlinx.android.synthetic.main.fragment_collected.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.uiThread

class CollectedFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
       = inflater.inflate(R.layout.fragment_collected,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_collect.layoutManager = GridLayoutManager(context,3)
        getCollectList("0")
        rb_shop.onClick { getCollectList("0") }
        rb_food.onClick { getCollectList("1") }
    }

    fun getCollectList(flag: String) = doAsync {
        val list = Server.getAllUsrCollection(usr_id, flag)
        uiThread {
            rv_collect.adapter = CollectListAdapter(list)
        }
    }

    private val share get() = context!!.getSharedPreferences("DNUIFood",Activity.MODE_PRIVATE)
    private val usr_id get() = share.getString("usr_id","")
}