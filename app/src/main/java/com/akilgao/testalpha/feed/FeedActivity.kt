package com.akilgao.testalpha.feed

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.akilgao.testalpha.R
import java.io.File
import java.util.ArrayList

class FeedActivity : Activity() {

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: MainAdapter? = null
    private var mData: ArrayList<MainData>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        mRecyclerView = findViewById(R.id.rv_list)

        mData = ArrayList()
        mData?.add(MainData("first", file("111.mp4")))
        mData?.add(MainData("second", file("222.mp4")))
        mData?.add(MainData("third", file("333.mp4")))
        mData?.add(MainData("forth", file("444.mp4")))
        mData?.add(MainData("fifth", file("555.mp4")))
        mData?.add(MainData("sixth", file("111.mp4")))
        mData?.add(MainData("seventh", file("222.mp4")))
        mData?.add(MainData("eighth", file("333.mp4")))
        mData?.add(MainData("ninth", file("444.mp4")))
        mData?.add(MainData("tenth", file("555.mp4")))

        mAdapter = MainAdapter(mData)
        mRecyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView?.adapter = mAdapter
        mRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (mAdapter != null) {
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    val pos = linearLayoutManager!!.findFirstCompletelyVisibleItemPosition()
                    mAdapter?.onScroll(dx, dy, recyclerView.findViewHolderForAdapterPosition(pos) as MainViewHolder?)
                }
            }
        })
    }

    private fun file(name: String): String {
        return Uri.fromFile(File(externalCacheDir, name)).toString()
    }
}
