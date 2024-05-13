package com.example.myapplication.data

import android.os.Parcelable
import androidx.room.*
import com.example.myapplication.utilis.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constant.DbConstant.TABLE_NAME)
data class CharacterResult(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var description: String = "",
    @Embedded
    var thumbnail: Thumbnail = Thumbnail("", ""),
    @Embedded
    var comics: Comics = Comics(""),
    @Embedded
    var series: Series = Series(""),
    @Ignore
    var urls: List<Url> = ArrayList(),
    var isFavorite: Boolean = false
) : Parcelable {

    @Parcelize
    data class Thumbnail(val path: String, val extension: String) : Parcelable

    @Parcelize
    data class Url(val type: String, val url: String) : Parcelable

    @Parcelize
    data class Comics(@ColumnInfo(name = "comic_available") val available: String) : Parcelable

    @Parcelize
    data class Series(@ColumnInfo(name = "series_available") val available: String) : Parcelable

}

// changed characctcerResult to MovieCharacter also CharacterResponse
@Parcelize
data class CharacterDatas(val results: List<CharacterResult>) : Parcelable

@Parcelize
data class CharacterResponses(val data: CharacterDatas) : Parcelable

