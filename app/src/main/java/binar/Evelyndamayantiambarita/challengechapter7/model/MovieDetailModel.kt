package binar.Evelyndamayantiambarita.challengechapter7.model

data class MovieDetailModel(
    val id: Int,
    val title: String,
    val poster_path: String,
    val backdrop_path: String,
    val release_date: String,
    val genres: List<Genres>,
    val overview: String,
) {
    data class Genres(
        val id: Int,
        val title: String
    )
}