package com.netcracker.appfordemo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.netcracker.appfordemo.databinding.ActivityMainBinding
import com.netcracker.appfordemo.uimodel.UserDevice
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.support.v4.app.NavUtils
import android.widget.Toast
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity(), RepositoryRecyclerViewAdapter.OnItemClickListener, View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val repositoryRecyclerViewAdapter = RepositoryRecyclerViewAdapter(arrayListOf(), this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.repositoryRv.layoutManager = LinearLayoutManager(this)
        binding.repositoryRv.adapter = repositoryRecyclerViewAdapter

        //viewModel.loadUserDevices(intent.getStringExtra("id"))
        if (viewModel.parent_id == null) {
            viewModel.parent_id = intent.getStringExtra("id")
        }
        toast("login: "+intent.getStringExtra("login")+"pass: "+intent.getStringExtra("pass"))

        viewModel.userDevices.observe(this,
               Observer<ArrayList<UserDevice>> { it?.let{ repositoryRecyclerViewAdapter.replaceData(it)} })
    }

    override fun onClick(view: View?) {
        val mainIntent = Intent(applicationContext, BarcodeMainActivity::class.java)
        mainIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivityForResult(mainIntent,0)

//        NavUtils.navigateUpTo(this,mainIntent)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == RESULT_OK ) {
            if (data.hasExtra("id")) {
                binding.viewModel?.parent_id = data.getStringExtra("id")
            }
        }
    }


    override fun onItemClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
