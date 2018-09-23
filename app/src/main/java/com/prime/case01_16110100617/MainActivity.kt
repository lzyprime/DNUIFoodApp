package com.prime.case01_16110100617

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.prime.case01_16110100617.fragments.Collection
import com.prime.case01_16110100617.fragments.MainPage
import com.prime.case01_16110100617.fragments.Search
import com.prime.case01_16110100617.fragments.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.mainpage -> mainVP.currentItem = 0
            R.id.collection -> mainVP.currentItem = 1
            R.id.search -> mainVP.currentItem = 2
            R.id.user -> mainVP.currentItem = 3
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainmenu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        var frags  = listOf(MainPage(),Collection(),Search(),User())
        mainVP.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(p0: Int): Fragment = frags.get(p0)
            override fun getCount(): Int = frags.size
        }
        mainVP.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageSelected(p0: Int){
                when(p0){
                    0 -> mainmenu.selectedItemId = R.id.mainpage
                    1 -> mainmenu.selectedItemId = R.id.collection
                    2 -> mainmenu.selectedItemId = R.id.search
                    3 -> mainmenu.selectedItemId = R.id.user
                }
            }

            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }
        })
    }
}
