package uz.mobiler.valutekurs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import uz.mobiler.valutekurs.R
import uz.mobiler.valutekurs.databinding.ItemHomeRvBinding
import uz.mobiler.valutekurs.models.Valute

class HomeRvAdapters(var list:ArrayList<Valute>) :RecyclerView.Adapter<HomeRvAdapters.Vh>() {

    inner class Vh(var itemHomeRvBinding: ItemHomeRvBinding) :
        RecyclerView.ViewHolder(itemHomeRvBinding.root) {
            fun onBind(valute: Valute){
                itemHomeRvBinding.valute=valute
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        var itemHomeRvBinding=DataBindingUtil.inflate<ItemHomeRvBinding>(LayoutInflater.from(parent.context),R.layout.item_home_rv,parent,false)
        return Vh(itemHomeRvBinding)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}