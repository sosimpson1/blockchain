package com.example.myapplication.data

data class SeriesResults(
    val id: String,
    val title: String,
    val description: String,
    val thumbnail: SeriesThumbnail
) {
    data class SeriesThumbnail(val path: String, val extension: String)
}

data class SeriesResponses(val data: SeriesDatas)

data class SeriesDatas(val results: List<SeriesResults>)