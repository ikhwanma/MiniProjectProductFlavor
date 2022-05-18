package ikhwan.binar.miniprojectproductflavor.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ikhwan.binar.latihanlistcountryretrofit.model.GetCountryResponseItem
import ikhwan.binar.miniprojectproductflavor.R
import kotlinx.android.synthetic.main.item_country_list.view.*


class CountryListAdapter(private val listCountry: List<GetCountryResponseItem>) : RecyclerView.Adapter<CountryListAdapter.ViewHolder>(){
    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)

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

        holder.itemView.apply {
            val txtCapital = "Capital : ${data.capital}"
            val txtPopulation = "Population : ${data.population}"
            val txtRegion = "Region : ${data.region}"
            tv_country.text = data.name
            tv_capital.text = txtCapital
            tv_cpopulation.text = txtPopulation
            Glide.with(holder.itemView).load(data.flags.png).into(img_country)
            tv_region.text = txtRegion
        }
    }

    override fun getItemCount(): Int {
        return listCountry.size
    }
}