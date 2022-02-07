package uz.mobiler.valutekurs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.mobiler.valutekurs.R
import uz.mobiler.valutekurs.databinding.ViewPagerItemBinding
import uz.mobiler.valutekurs.models.Valute

class ViewPagerAdapters : ListAdapter<Valute, ViewPagerAdapters.Vh>(MyDiffUtill()) {
    inner class Vh(var viewPagetItemBinding: ViewPagerItemBinding) :
        RecyclerView.ViewHolder(viewPagetItemBinding.root) {
        fun onBind(valute: Valute){
            viewPagetItemBinding.valute=valute
            Picasso.get().load("https://nbu.uz/local/templates/nbu/images/flags/${valute.code}.png").into(viewPagetItemBinding.flags)
        }
    }

    class MyDiffUtill : DiffUtil.ItemCallback<Valute>() {
        override fun areItemsTheSame(oldItem: Valute, newItem: Valute): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Valute, newItem: Valute): Boolean {
            return oldItem.equals(newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.view_pager_item,parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }
}