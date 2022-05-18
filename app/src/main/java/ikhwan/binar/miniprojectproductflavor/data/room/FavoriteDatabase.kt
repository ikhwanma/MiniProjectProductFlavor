package ikhwan.binar.miniprojectproductflavor.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao() : FavoriteDao

    companion object{
        private var INSTANCE: FavoriteDatabase? = null

        fun getInstance(context: Context): FavoriteDatabase? {
            if(INSTANCE == null){
                synchronized(FavoriteDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FavoriteDatabase::class.java, "Favorite.db").build()
                }
            }
            return INSTANCE
        }
    }
}