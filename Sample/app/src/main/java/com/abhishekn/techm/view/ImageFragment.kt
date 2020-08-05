package com.abhishekn.techm.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.abhishekn.techm.R
import com.abhishekn.techm.adapter.ImageAdapter
import com.abhishekn.techm.model.Row
import com.abhishekn.techm.network.ServiceApi
import com.abhishekn.techm.network.Utility
import com.abhishekn.techm.viewmodel.MainActivityViewModel


class ImageFragment : Fragment() {
    private lateinit var mViewModel : MainActivityViewModel
    private lateinit var mView: View
    private val serviceAPI by lazy {
        ServiceApi.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_image, container, false)
        mViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        initView()
        return mView
    }

    // Initialize swipe to refresh View
    private fun initView() {
        val swipeItems = mView?.findViewById<SwipeRefreshLayout>(R.id.swipe_items)
        swipeItems.setOnRefreshListener {
            swipeItems.setColorSchemeColors(resources.getColor(R.color.colorAccent))
            if (swipeItems.isRefreshing()) {
                initSwipe()
                swipeItems.isRefreshing = false
            }
        }

        // Observe Livedata to reflect changes if there is any new data added to database
        mViewModel.imageData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    initRecyclerView(it)
                } else {
                    callImageAPI()
                }
            }
        })
    }

    // Check Network connectivity before calling the API, if there is no network then show Connectivity error
    private fun callImageAPI() {
        if (Utility.isNetworkAvailable(this.activity)){
            loadImageAPI()
        }
        else{
            showToast("Network Connectivity Error")
        }
    }

    // call Image API to load the image data from Network
    private fun loadImageAPI(){
        mViewModel.loadImageAPI().observe(viewLifecycleOwner, Observer {
            if (it is List<*>){
                val rowList = it as List<Row>
                initRecyclerView(rowList)
            }
            else{showToast(it.toString())}
        })
    }

    // Initiate recyclerview & assign adapter to it
    private fun initRecyclerView(row: List<Row>) {
        val recyclerView = mView?.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        recyclerView.adapter = ImageAdapter(this.activity, row)
    }

    // Pull to Refresh feature load Api again
    private fun initSwipe() {
        callImageAPI()
    }

    private fun showToast(message: String){
        Toast.makeText(this.activity, message, Toast.LENGTH_LONG).show()
    }
    companion object {
        @JvmStatic
        fun newInstance() = ImageFragment()
    }
}