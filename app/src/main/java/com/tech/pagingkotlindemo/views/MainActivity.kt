package com.tech.pagingkotlindemo.views


import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tech.pagingkotlindemo.R
import com.tech.pagingkotlindemo.adapter.PostRecyclerAdapter
import com.tech.pagingkotlindemo.models.PostItem
import com.tech.pagingkotlindemo.utils.PaginationListener
import com.tech.pagingkotlindemo.utils.PaginationListener.Companion.PAGE_START
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private var adapter: PostRecyclerAdapter? = null
    private var currentPage = PAGE_START
    private var iLastPage = false
    private val totalPage = 10
    private var iLoading = false
    private var itemCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeRefresh!!.setOnRefreshListener(this)
        recyclerView!!.setHasFixedSize(true)
        // use a linear layout manager
        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        val listPostItem: MutableList<PostItem> = arrayListOf()
        adapter = PostRecyclerAdapter(listPostItem)
        recyclerView!!.adapter = adapter
        doApiCall()

        /**
         * add scroll listener while user reach in bottom load more will call
         */
        recyclerView!!.addOnScrollListener(object : PaginationListener(layoutManager) {
            override val isLastPage: Boolean
                get() = iLastPage
            override val isLoading: Boolean
                get() = iLoading

            override fun loadMoreItems() {
                iLoading = true
                currentPage++
                doApiCall()
            }
        })
        recyclerView.setOnClickListener {
            Toast.makeText(this, "alo", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * do api call here to fetch data from server
     * In example i'm adding data manually
     */
    private fun doApiCall() {
        val items = ArrayList<PostItem>()
        Handler().postDelayed({
            for (i in 0..9) {
                itemCount++
                val postItem = PostItem()
                postItem.title = getString(R.string.text_title) + itemCount
                postItem.description = getString(R.string.text_description)
                items.add(postItem)
            }
            // do this all stuff on Success of APIs response
            /**
             * manage progress view
             */
            /**
             * manage progress view
             */
            if (currentPage != PAGE_START) adapter!!.removeLoading()
            adapter!!.addItems(items)
            swipeRefresh!!.isRefreshing = false

            // check weather is last page or not
            if (currentPage < totalPage) {
                adapter!!.addLoading()
            } else {
                iLastPage = true
            }
            iLoading = false
        }, 1500)
    }

    override fun onRefresh() {
        itemCount = 0
        currentPage = PAGE_START
        iLastPage = false
        adapter!!.clear()
        doApiCall()
    }

    companion object {

        private val TAG = "MainActivity"
    }
}
