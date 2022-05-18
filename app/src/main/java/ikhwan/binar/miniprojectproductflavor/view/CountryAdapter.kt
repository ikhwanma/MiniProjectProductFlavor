package ikhwan.binar.miniprojectproductflavor.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ikhwan.binar.latihanlistcountryretrofit.model.GetCountryResponseItem
import ikhwan.binar.miniprojectproductflavor.R
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter (private val listCountry: List<GetCountryResponseItem>) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listCountry[position]

        holder.itemView.apply {
            tv_country.text = data.name
            tv_capital.text = data.capital
            tv_cpopulation.text = data.population.toString()
            Glide.with(holder.itemView).load(data.flags.png).into(img_country)
            tv_region.text = data.region
        }
    }

    override fun getItemCount(): Int {
        return listCountry.size
    }
}