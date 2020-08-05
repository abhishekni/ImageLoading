package com.abhishekn.techm.db

import android.content.Context
import android.os.strictmode.InstanceCountViolation
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.abhishekn.techm.model.Row
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Row::class), version = 1, exportSchema = false)
abstract class ImageRoomDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao
    companion object{
        @Volatile
        private var INSTANCE: ImageRoomDatabase? =null

        fun getDatabaseInstance(
            context: Context,
            scope: CoroutineScope
        ) : ImageRoomDatabase{
            val temp = INSTANCE
            if (temp != null){
                return temp
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                ImageRoomDatabase::class.java,
                "Image_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(ImageDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        public class ImageDatabaseCallback(
            private val scope: CoroutineScope):RoomDatabase.Callback(){
            override fun onOpen(db: SupportSQLiteDatabase){
                super.onOpen(db)
                INSTANCE?.let {
                    scope.launch {
                        populateDatabase(it.imageDao())
                    }
                }
            }
        }

        fun populateDatabase(imageDao: ImageDao){
//            var imageRes = Row("","","")
//            imageDao.insertImage(imageRes)
//            imageRes = Row("","","")
//            imageDao.insertImage(imageRes)
        }
    }
}
