package com.ircarren.ghkanban.data.localDataBase

import android.content.Context
import androidx.room.Room
import com.ircarren.ghkanban.data.localDataBase.appDataBase.AppDataBase

object DataBaseBuilder {
    private var INSTANCE: AppDataBase? = null
    // get instance of database
    fun getInstance(context: Context): AppDataBase {
        if (INSTANCE == null) {
            synchronized(AppDataBase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java, "ghkanban.db"
                ).build()
            }
        }
        return INSTANCE!!
    }

    // build
    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "ghkanban.db"
        ).build()

}