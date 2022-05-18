package ikhwan.binar.miniprojectproductflavor.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ikhwan.binar.miniprojectproductflavor.R
import ikhwan.binar.miniprojectproductflavor.data.room.Favorite
import ikhwan.binar.miniprojectproductflavor.data.room.FavoriteDatabase
import kotlinx.android.synthetic.main.item_country.view.btn_favorite
import kotlinx.android.synthetic.main.item_country.view.img_country
import kotlinx.android.synthetic.main.item_country.view.tv_capital
import kotlinx.android.synthetic.main.item_country.view.tv_country
import kotlinx.android.synthetic.main.item_country.view.tv_cpopulation
import kotlinx.android.synthetic.main.item_country.view.tv_region
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class FavoriteAdapter(private val listCountry: List<Favorite>): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_country_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listCountry[position]

        holder.itemView.apply {
            val txtCapital = "Capital : ${data.capital}"
            val txtPopulation = "Population : ${data.population}"
            val txtRegion = "Region : ${data.region}"
            tv_country.text = data.name
            tv_capital.text = txtCapital
            tv_cpopulation.text = txtPopulation
            Glide.with(holder.itemView).load(data.flag).into(img_country)
            tv_region.text = txtRegion
            val mDb = FavoriteDatabase.getInstance(this.context)
            btn_favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            btn_favorite.setOnClickListener {
                GlobalScope.async {
                    val results = mDb!!.favoriteDao().deleteFavorite(data)
                    (holder.itemView.context as FavoriteActivity).runOnUiThread {
                        if (results != 0) {
                            btn_favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                            Toast.makeText(
                                it.context,
                                "Removed from Favorite",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                it.context,
                                "Failed to Remove",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    (holder.itemView.context as FavoriteActivity).fetchData()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listCountry.size
    }
}