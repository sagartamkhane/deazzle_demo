package com.deazzle.demo.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.deazzle.demo.R
import com.deazzle.demo.R.layout
import com.deazzle.demo.data.api.ApiHelper
import com.deazzle.demo.data.api.RetrofitBuilder
import com.deazzle.demo.data.model.UserResp
import com.deazzle.demo.ui.base.ViewModelFactory
import com.deazzle.demo.ui.main.adapter.MainAdapter
import com.deazzle.demo.ui.main.viewmodel.MainViewModel
import com.deazzle.demo.utils.RecyclerViewClickListener
import com.deazzle.demo.utils.Status.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() ,RecyclerViewClickListener{

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        setupViewModel()
        setupUI()
        setupObservers()
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf(), this)
       /* recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )*/
        var layout   = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.layoutManager = layout
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getUsers().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(users: UserResp) {
        adapter.apply {
            addUsers(users.results)
            notifyDataSetChanged()
        }
    }
    override fun onRecyclerViewItemClick(view: View, pos: Int) {

        when (view.id) {

            R.id.btnAccept ->
                recyclerView.scrollToPosition(pos)

            R.id.btnDecline ->
                recyclerView.scrollToPosition(pos)

        }

    }
}
