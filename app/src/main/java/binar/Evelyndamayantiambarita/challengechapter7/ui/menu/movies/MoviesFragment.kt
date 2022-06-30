package binar.Evelyndamayantiambarita.challengechapter7.ui.menu.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import binar.Evelyndamayantiambarita.challengechapter7.data.api.tmdb.MoviesPopularResponse
import binar.Evelyndamayantiambarita.challengechapter7.data.api.tmdb.MoviesUpcomingResponse
import binar.Evelyndamayantiambarita.challengechapter7.databinding.FragmentMoviesBinding
import binar.Evelyndamayantiambarita.challengechapter7.ui.menu.moviedetails.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    val viewModel: MoviesViewModel by viewModels()
    lateinit var moviePopularAdapter: MoviesPopularAdapter
    lateinit var movieUpcommingAdapter: MoviesUpcomingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        moviePopularAdapter =
            MoviesPopularAdapter(listener = object : MoviesPopularAdapter.EventListener {
                override fun onClick(item: MoviesPopularResponse.ResultMoviesPopular) {
                    val intent = Intent(activity, MovieDetailsActivity::class.java)
                    val bundle = Bundle()
                    item.id?.let { bundle.putInt("id", it) }
                    item.title?.let { bundle.putString("title", it) }
                    item.poster_path?.let { bundle.putString("poster_path", it) }
                    intent.putExtras(bundle)
                    startActivity(intent)
                }

            }, emptyList())

        movieUpcommingAdapter =
            MoviesUpcomingAdapter(listener = object : MoviesUpcomingAdapter.EventListener {
                override fun onClick(item: MoviesUpcomingResponse.ResultMoviesUpcoming) {
                    val intent = Intent(activity, MovieDetailsActivity::class.java)
                    val bundle = Bundle()
                    item.id?.let { bundle.putInt("id", it) }
                    item.title?.let { bundle.putString("title", it) }
                    item.poster_path?.let { bundle.putString("poster_path", it) }
                    intent.putExtras(bundle)
                    startActivity(intent)
                }

            }, emptyList())

        binding.rvMoviesNowplaying.adapter = moviePopularAdapter
        binding.rvMoviesUpcoming.adapter = movieUpcommingAdapter

        viewModel.onViewLoaded()

        bindViewModel()

        return root
    }

    private fun bindViewModel() {
        viewModel.shouldShowMoviePopular.observe(requireActivity()) {
            moviePopularAdapter.updateList(it.results)
        }

        viewModel.shouldShowMovieUpcoming.observe(requireActivity()) {
            movieUpcommingAdapter.updateList(it.results)
        }

        viewModel.shouldShowProfile.observe(requireActivity()) {
            binding.tvMoviesUsername.text = "Hi, " + it.name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}