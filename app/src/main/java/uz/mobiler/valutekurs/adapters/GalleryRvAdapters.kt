package uz.mobiler.valutekurs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.mobiler.valutekurs.R
import uz.mobiler.valutekurs.databinding.ItemGalleryRvBinding
import uz.mobiler.valutekurs.models.Valute

class GalleryRvAdapters(var list: ArrayList<Valute>) :RecyclerView.Adapter<GalleryRvAdapters.Vh>() {
    inner class Vh(var itemGalleryRvBinding: ItemGalleryRvBinding) :RecyclerView.ViewHolder(itemGalleryRvBinding.root){
        fun onBind(valute: Valute){
            itemGalleryRvBinding.valute=valute

             if (valute.nbu_buy_price.isEmpty() && valute.nbu_cell_price.isEmpty()){
                 itemGalleryRvBinding.sum1.text="Mavjud emas"
                 itemGalleryRvBinding.sum3.text="Mavjud emas"
             }else{
                 itemGalleryRvBinding.sum1.text="${valute.nbu_buy_price} UZS"
                 itemGalleryRvBinding.sum3.text="${valute.nbu_cell_price} UZS"
             }

            Picasso.get().load("https://nbu.uz/local/templates/nbu/images/flags/${valute.code}.png").into(itemGalleryRvBinding.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        var itemGalleryRvBinding=DataBindingUtil.inflate<ItemGalleryRvBinding>(LayoutInflater.from(parent.context),
            R.layout.item_gallery_rv,parent,false)
        return Vh(itemGalleryRvBinding)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int =list.size
}