package com.prime.dnuifood

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.prime.dnuifood.Fragments.CollectedFragment
import com.prime.dnuifood.Fragments.MainPageFragment
import com.prime.dnuifood.Fragments.SearchFragment
import com.prime.dnuifood.Fragments.UsrFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bnv_main.setOnNavigationItemSelectedListener(bnvlistener)
        vp_main.adapter = vpadapter
        vp_main.addOnPageChangeListener(vplistener)
        fabt_shopcar.setOnClickListener { startActivity<ShopCarActivity>() }
    }

    private val bnvlistener = BottomNavigationView.OnNavigationItemSelectedListener {it ->
        when(it.itemId) {
            R.id.mainpage -> vp_main.currentItem = 0
            R.id.collected -> vp_main.currentItem = 1
            R.id.search -> vp_main.currentItem = 2
            R.id.usr -> vp_main.currentItem = 3
        }
        true
    }

    private val frags = listOf(MainPageFragment(),CollectedFragment(),SearchFragment(),UsrFragment())

    private val vpadapter = object : FragmentPagerAdapter(supportFragmentManager){
        override fun getCount(): Int = frags.size
        override fun getItem(p0: Int): Fragment = frags[p0]
    }

    private val vplistener = object : ViewPager.OnPageChangeListener {
        override fun onPageSelected(p0: Int) {
            when(p0) {
                0 -> bnv_main.selectedItemId = R.id.mainpage
                1 -> bnv_main.selectedItemId = R.id.collected
                2 -> bnv_main.selectedItemId = R.id.search
                3 -> bnv_main.selectedItemId = R.id.usr
            }
        }

        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
        override fun onPageScrollStateChanged(p0: Int) {}
    }
}
