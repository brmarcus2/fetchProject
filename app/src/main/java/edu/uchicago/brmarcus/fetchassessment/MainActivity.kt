package edu.uchicago.brmarcus.fetchassessment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uchicago.brmarcus.fetchassessment.viewModels.MainViewModel
import edu.uchicago.brmarcus.fetchassessment.viewModels.MyViewModelFactory
import edu.uchicago.brmarcus.fetchassessment.data.Repository
import edu.uchicago.brmarcus.fetchassessment.data.RetrofitService

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    val adapter = Adapter()
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //set up the progress spinner
        spinner = findViewById((R.id.progressBar))
        spinner.visibility = View.VISIBLE
        //set up the recyclerview
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.addItemDecoration(DividerItemDecoration(baseContext, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        //set up the viewModel
        viewModel = ViewModelProvider(this, MyViewModelFactory(Repository(retrofitService))).get(MainViewModel::class.java)

        //observe the items in the viewModel and set them in the adaptor when they change
        viewModel.itemList.observe(this, Observer {
            spinner.visibility = View.GONE
            adapter.setItemList(it)
        })
        viewModel.errorMessage.observe(this, Observer {
        })
        //getAllItems when the mainActivity is created
        viewModel.getAllItems()
    }
}