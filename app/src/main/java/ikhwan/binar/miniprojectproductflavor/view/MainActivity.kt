package ikhwan.binar.miniprojectproductflavor.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ikhwan.binar.miniprojectproductflavor.R
import ikhwan.binar.miniprojectproductflavor.data.datastore.DataStoreManager
import ikhwan.binar.miniprojectproductflavor.viewmodel.MainCountryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel : MainCountryViewModel by viewModels()
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataStoreManager = DataStoreManager(this)

        fetchData()

        btn_fav.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
            finish()
        }
    }

    fun fetchData() {
        dataStoreManager.boolean.asLiveData().observe(this){
            switch_rv.isChecked = it
            viewModel.getCountry()
            if (it){
                viewModel.liveDataCountry.observe(this){ data ->
                    rv_country.layoutManager = LinearLayoutManager(this)
                    rv_country.adapter = CountryListAdapter(data!!)
                }
            }else{
                viewModel.liveDataCountry.observe(this){ data ->
                    rv_country.layoutManager = GridLayoutManager(this, 2)
                    rv_country.adapter = CountryAdapter(data!!)
                }
            }

            switch_rv.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    GlobalScope.launch {
                        dataStoreManager.saveData(true)
                    }
                } else {
                    GlobalScope.launch {
                        dataStoreManager.saveData(false)
                    }
                }
            }
        }
    }
}