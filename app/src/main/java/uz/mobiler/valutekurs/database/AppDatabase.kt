package uz.mobiler.valutekurs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.mobiler.valutekurs.models.Valute

@Database(entities = [Valute::class],version = 1)
abstract class AppDatabase :RoomDatabase() {
    abstract fun valuteDao():ValuteDao
    companion object{
        private var inctance:AppDatabase?=null
        @Synchronized
        fun getInctance(context: Context):AppDatabase{
            if (inctance==null){
                inctance=Room.databaseBuilder(context,AppDatabase::class.java,"valute.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return inctance!!
        }
    }
}