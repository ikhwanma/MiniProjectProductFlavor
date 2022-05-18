package ikhwan.binar.miniprojectproductflavor.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ikhwan.binar.miniprojectproductflavor.R
import ikhwan.binar.miniprojectproductflavor.data.room.FavoriteDatabase
import ikhwan.binar.miniprojectproductflavor.viewmodel.MainCountryViewModel
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private val viewModel : MainCountryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        fetchData()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun fetchData() {
        val mDb = FavoriteDatabase.getInstance(this)
        GlobalScope.launch {
            val result = mDb!!.favoriteDao().getFavorite()
            runOnUiThread {
                if (result != null){
                    rv_country.layoutManager = LinearLayoutManager(this@FavoriteActivity)
                    rv_country.adapter = FavoriteAdapter(result)
                }
            }
        }
    }
}