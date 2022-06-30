package binar.Evelyndamayantiambarita.challengechapter7.ui.menu.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import binar.Evelyndamayantiambarita.challengechapter7.data.local.FavoriteEntity
import binar.Evelyndamayantiambarita.challengechapter7.databinding.FragmentFavoriteBinding
import binar.Evelyndamayantiambarita.challengechapter7.model.FavoriteListModel
import binar.Evelyndamayantiambarita.challengechapter7.ui.menu.moviedetails.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    val viewModel: FavoriteViewModel by viewModels()
    lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        favoriteAdapter =
            FavoriteAdapter(listener = object : FavoriteAdapter.EventListener {
                override fun onClick(item: FavoriteEntity) {
                    val intent = Intent(activity, MovieDetailsActivity::class.java)
                    val bundle = Bundle()
                    item.favoriteId.let { bundle.putInt("id", it) }
                    item.title.let { bundle.putString("title", it) }
                    item.poster_path.let { bundle.putString("poster_path", it) }
                    intent.putExtras(bundle)
                    startActivity(intent)
                }


            }, emptyList())

        binding.rvFavorite.adapter = favoriteAdapter

        bindViewModel()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewLoaded()
    }

    private fun bindViewModel() {
        viewModel.shouldShowMoviesFavorite.observe(requireActivity()) {
            favoriteAdapter.updateList(it)
        }
    }


}