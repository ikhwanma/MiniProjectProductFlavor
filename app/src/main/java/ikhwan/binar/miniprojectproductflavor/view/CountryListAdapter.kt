package ikhwan.binar.miniprojectproductflavor.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ikhwan.binar.latihanlistcountryretrofit.model.GetCountryResponseItem
import ikhwan.binar.miniprojectproductflavor.R
import ikhwan.binar.miniprojectproductflavor.data.room.Favorite
import ikhwan.binar.miniprojectproductflavor.data.room.FavoriteDatabase
import kotlinx.android.synthetic.main.item_country.view.*
import kotlinx.android.synthetic.main.item_country_list.view.*
import kotlinx.android.synthetic.main.item_country_list.view.btn_favorite
import kotlinx.android.synthetic.main.item_country_list.view.img_country
import kotlinx.android.synthetic.main.item_country_list.view.tv_capital
import kotlinx.android.synthetic.main.item_country_list.view.tv_country
import kotlinx.android.synthetic.main.item_country_list.view.tv_cpopulation
import kotlinx.android.synthetic.main.item_country_list.view.tv_region
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class CountryListAdapter(private val listCountry: List<GetCountryResponseItem>) :
    RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryListAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_country_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CountryListAdapter.ViewHolder, position: Int) {
        val data = listCountry[position]
        val img = data.flags.png

        holder.itemView.apply {
            val txtCapital = "Capital : ${data.capital}"
            val txtPopulation = "Population : ${data.population}"
            val txtRegion = "Region : ${data.region}"
            tv_country.text = data.name
            tv_capital.text = txtCapital
            tv_cpopulation.text = txtPopulation
            Glide.with(holder.itemView).load(data.flags.png).into(img_country)
            tv_region.text = txtRegion
            val mDb = FavoriteDatabase.getInstance(this.context)
            GlobalScope.async {
                val res = mDb!!.favoriteDao().getFavorite()
                Log.d("iniRes", res.toString())

                (holder.itemView.context as MainActivity).runOnUiThread {
                    if (res.isNullOrEmpty()) {
                        btn_favorite.setOnClickListener {
                            GlobalScope.async {
                                val result = mDb.favoriteDao().addFavorite(
                                    Favorite(
                                        null,
                                        data.name,
                                        data.capital,
                                        data.population,
                                        img,
                                        data.region
                                    )
                                )
                                (holder.itemView.context as MainActivity).runOnUiThread {
                                    if (result != 0.toLong()) {
                                        btn_favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                                        Toast.makeText(
                                            it.context,
                                            "Added to Favorite",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            it.context,
                                            "Failed to Add",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    } else {
                        for (datas in res) {
                            if (datas.name == data.name) {
                                btn_favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                                btn_favorite.setOnClickListener {
                                    GlobalScope.async {
                                        val results = mDb.favoriteDao().deleteFavorite(datas)
                                        (holder.itemView.context as MainActivity).runOnUiThread {
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
                                    }
                                }
                                break
                            } else {
                                btn_favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                                btn_favorite.setOnClickListener {
                                    GlobalScope.async {
                                        val result = mDb.favoriteDao().addFavorite(
                                            Favorite(
                                                null,
                                                data.name,
                                                data.capital,
                                                data.population,
                                                img,
                                                data.region
                                            )
                                        )
                                        (holder.itemView.context as MainActivity).runOnUiThread {
                                            if (result != 0.toLong()) {
                                                btn_favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                                                Toast.makeText(
                                                    it.context,
                                                    "Added to Favorite",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    it.context,
                                                    "Failed to Add",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listCountry.size
    }
}