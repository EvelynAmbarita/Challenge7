package binar.Evelyndamayantiambarita.challengechapter7.ui.menu.moviedetails

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import binar.Evelyndamayantiambarita.challengechapter7.R
import binar.Evelyndamayantiambarita.challengechapter7.databinding.ActivityMovieDetailsBinding
import binar.Evelyndamayantiambarita.challengechapter7.ui.menu.MenuActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailViewModel by viewModels()
    lateinit var genreAdapter: GenreAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val id = intent.extras!!.getInt("id")

        genreAdapter = GenreAdapter(emptyList())

        binding.rvDetailTag.adapter = genreAdapter
        viewModel.getMovieDetail(id = id)
        viewModel.searchFavoriteId(favoriteId = id)
        bindView()
        bindViewModel()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun bindView() {
        binding.ivDetailBack.setOnClickListener {
            onBackPressed()
            finish()
        }

        binding.btnDetailsFavorite.setOnClickListener {
            val movieId = intent.extras!!.getInt("id")
            val title = intent.extras!!.getString("title")
            val posterPath = intent.extras!!.getString("poster_path")
            viewModel.addToFavorite(
                movieId = movieId,
                title = title.toString(),
                poster_path = posterPath.toString()
            )
        }

        binding.btnDetailsUnfavorite.setOnClickListener {
            val movieId = intent.extras!!.getInt("id")
            viewModel.removeFromFavorite(movieId = movieId)
        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowMovieDetail.observe(this) {
            genreAdapter.updateList(it.genres)
            binding.tvDetailTitle.text = it.title
            binding.tvDetailReleaseDate.text = it.release_date
            binding.tvDetailsSynopsisContent.text = it.overview

            Glide.with(applicationContext)
                .load(it.poster_path)
                .transform(RoundedCorners(20))
                .into(binding.ivDetailPoster)

            Glide.with(applicationContext)
                .load(it.backdrop_path)
                .into(binding.ivDetailImage)
        }
        viewModel.shouldShowFavoriteButton.observe(this) {
            binding.btnDetailsFavorite.visibility = View.GONE
            binding.btnDetailsUnfavorite.visibility = View.VISIBLE
        }
        viewModel.shouldShowUnfavoriteButton.observe(this) {
            binding.btnDetailsFavorite.visibility = View.VISIBLE
            binding.btnDetailsUnfavorite.visibility = View.GONE
        }
        viewModel.shouldFavorited.observe(this) {
            binding.btnDetailsFavorite.visibility = View.GONE
            binding.btnDetailsUnfavorite.visibility = View.VISIBLE
        }
    }

}