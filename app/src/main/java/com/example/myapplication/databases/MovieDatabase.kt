package com.example.myapplication.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.CharacterResult
import com.example.myapplication.utilis.Constant

@Database(entities = [CharacterResult::class], version = Constant.DbConstant.DB_VERSION)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDaos

}