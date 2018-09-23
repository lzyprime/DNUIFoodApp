package com.prime.case01_16110100617

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.prime.case01_16110100617.fragments.StartLogin
import com.prime.case01_16110100617.fragments.StartSignIn
import kotlinx.android.synthetic.main.start.*

class Start : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start)
        startmenu.setOnNavigationItemSelectedListener {it ->
            when (it.itemId){
                R.id.login_item -> startVP.currentItem = 0
                R.id.signin_item -> startVP.currentItem = 1
        }
            true
            }

        var frags = listOf(StartLogin(),StartSignIn())
        startVP.adapter = object : FragmentPagerAdapter(supportFragmentManager){
            override fun getItem(p0: Int): Fragment = frags.get(p0)
            override fun getCount(): Int = frags.size
        }

        startVP.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageSelected(p0: Int) {
                when(p0){
                    0 -> startmenu.selectedItemId = R.id.login_item
                    1 -> startmenu.selectedItemId = R.id.signin_item
                }
            }

            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
        })
    }
}
