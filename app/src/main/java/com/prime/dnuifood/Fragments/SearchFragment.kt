package com.prime.dnuifood.Fragments

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.prime.dnuifood.Adapters.FoodListAdapter
import com.prime.dnuifood.Adapters.SearchAdapter
import com.prime.dnuifood.R
import com.prime.dnuifood.Server
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SearchFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_search,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText == "") {
                   setHistory()
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                var searchs = share?.getStringSet("Searchs", mutableSetOf())?: mutableSetOf()
                searchs.add(query)
                shareEditor?.putStringSet("Searchs",searchs)?.commit()
                doAsync {
                            val foodlist = Server.search(query ?: "")
                            uiThread {
                                rv_search.layoutManager = GridLayoutManager(context,2)
                                rv_search.adapter = FoodListAdapter(foodlist)
                            }
                        }
                return true
            }
        })
        searchView.setOnSearchClickListener {
            setHistory()
        }
    }

    fun clearHistory() {
        shareEditor?.putStringSet("Searchs", mutableSetOf())
        rv_search.adapter = SearchAdapter(this, listOf())
    }

    fun setHistory() {
        val history = share?.getStringSet("Searchs", mutableSetOf())?.toList() ?: listOf()
        rv_search.layoutManager = GridLayoutManager(context,1)
        rv_search.adapter = SearchAdapter(this@SearchFragment,history)
    }

    fun setQuery(text:String) {
        searchView.setQuery(text,false)
    }
    private val share get() = activity?.getSharedPreferences("DNUIFood",Activity.MODE_PRIVATE)
    private val shareEditor get() = share?.edit()

}