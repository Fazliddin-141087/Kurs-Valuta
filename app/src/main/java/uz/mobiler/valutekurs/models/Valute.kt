package uz.mobiler.valutekurs.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Valute(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val cb_price: String,
    val code: String,
    val date: String,
    val nbu_buy_price: String,
    val nbu_cell_price: String,
    val title: String
) : Serializable