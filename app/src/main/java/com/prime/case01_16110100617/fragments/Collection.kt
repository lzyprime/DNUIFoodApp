package com.prime.case01_16110100617.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.prime.case01_16110100617.R
import com.prime.case01_16110100617.RetrofitInterfaces
import kotlinx.android.synthetic.main.fragment_collection.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class Collection : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collection, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var usr_id = context!!.getSharedPreferences("case01_16110100617",Context.MODE_PRIVATE).getString("usrid","")
        bnv_collection.setOnNavigationItemReselectedListener {  }
    }
}
