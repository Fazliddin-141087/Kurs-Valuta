package uz.mobiler.valutekurs.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.squareup.picasso.Picasso
import uz.mobiler.valutekurs.R
import uz.mobiler.valutekurs.databinding.ItemSpinnerEnBinding
import uz.mobiler.valutekurs.models.Valute

class SpinnerAdapters(var list: ArrayList<Valute>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): Valute {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = ItemSpinnerEnBinding.inflate(LayoutInflater.from(p2?.context), p2, false)
        Picasso.get().load("https://nbu.uz/local/templates/nbu/images/flags/${list[p0].code}.png")
            .error(R.drawable.uzb)
            .into(view.img)
        view.tv.text = list[p0].code
        return view.root
    }

}