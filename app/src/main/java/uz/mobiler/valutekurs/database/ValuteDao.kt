package uz.mobiler.valutekurs.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uz.mobiler.valutekurs.models.Valute
@Dao
interface ValuteDao {

    @Insert
    fun insertValue(valute: Valute)

    @Query("select * from valute order by valute.code")
    fun getAllValute() : List<Valute>

}