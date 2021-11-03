package com.submission.githubuser1.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.submission.githubuser1.datasource.local.dao.UserDao
import com.submission.githubuser1.datasource.local.dao.UserDetailDao
import com.submission.githubuser1.datasource.remote.response.User
import com.submission.githubuser1.datasource.remote.response.UserDetail

/****************************************************
 * Created by Indra Muliana
 * On Wednesday, 03/11/2021 19.40
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

@Database(
    entities = [
        User::class,
        UserDetail::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getUserDetailDao(): UserDetailDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        val dbInstance
            get() = INSTANCE

        fun initDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "github_user_db")
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                // return instance
                instance
            }
        }
    }

}