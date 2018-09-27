package com.prime.case01_16110100617.fragments


import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.prime.case01_16110100617.R
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

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.collectionshop -> VP_collection.currentItem = 0
            R.id.collectionfood -> VP_collection.currentItem = 1
        }
        true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bnv_collection.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        var frags = listOf(CollectShopFragment(),CollectFoodFragment())
        VP_collection.adapter = object : FragmentPagerAdapter(childFragmentManager){
            override fun getItem(p0: Int): Fragment = frags[p0]
            override fun getCount(): Int = frags.size
        }

        VP_collection.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(p0: Int) {
                when (p0) {
                    0 -> bnv_collection.selectedItemId = R.id.collectionshop
                    1 -> bnv_collection.selectedItemId = R.id.collectionfood
                }
            }

            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }
        })

    }
}
