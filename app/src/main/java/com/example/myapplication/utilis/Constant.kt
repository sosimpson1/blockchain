package com.example.myapplication.utilis

import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class Constant {
    companion object {
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"
        val timeStamp = Timestamp(System.currentTimeMillis()).time.toString()
        const val PUBLIC_KEY = "6d69ede0428b918f89a6f0e1100ed01f"
        private const val PRIVATE_KEY = "a799a9840ed927b8c455de7df7bf9784459beeea"

        fun hash(): String {
            val input = "$timeStamp$PRIVATE_KEY$PUBLIC_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
    }

    object AppModuleKey {
        const val API_KEY = "apikey"
        const val TIMESTAMP = "ts"
        const val HASH_KEY = "hash"
    }

    object DbConstant {
        const val DB_NAME = "movie_database"
        const val TABLE_NAME = "characters_table"
        const val DB_VERSION = 1
    }

    object CharacterConstant {
        const val SEARCH = "Search Character"
        const val SERIESNOTAVAILABLE = "Series not available at this time "
        const val COMICNOTAVAILABLE = "Series not available at this time "
        const val SERIESCLICK = "Series: CLick to see more."
        const val COMICCLICK = "Comic: CLick to see more."
        const val URLCLICK = "URL Links."
        const val MOVIECHARACTER = "Movie Character Explorer."
        const val SEARCHS = "Search."
        const val ICONSEARCH = "Search Icon"




    }
}