package uz.mobiler.valutekurs.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.mobiler.valutekurs.models.Valute
@Dao
interface ValuteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertValue(valute: Valute)

    @Query("select * from valute")
    fun getAllValute() : List<Valute>

    @Query("select * from valute where id=:id order by date")
    fun getAllValuteHistory(id:Int) : List<Valute>

    @Query("select * from valute where date=:date and code=:code")
    fun getAllValuteByDate(date:String,code:String) : List<Valute>

 @Query("select * from valute where date<>:date and code=:code")
    fun getAllValuteByDateRv(date:String,code:String) : List<Valute>

}