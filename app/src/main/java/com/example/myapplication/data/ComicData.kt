package com.example.myapplication.data

data class ComicResults(
val id: String,
val title: String,
val description: String,
val thumbnail: ComicThumbnail
) {
    data class ComicThumbnail(val path: String, val extension: String)
}
data class ComicResponses(val data: ComicData)

data class ComicData(val results: List<ComicResults>)

