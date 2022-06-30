package binar.Evelyndamayantiambarita.challengechapter7.ui.menu.moviedetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.Evelyndamayantiambarita.challengechapter7.databinding.ListItemTagBinding
import binar.Evelyndamayantiambarita.challengechapter7.model.MovieDetailModel

class GenreAdapter(private var list: List<MovieDetailModel.Genres>)
    : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListItemTagBinding)
        : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<MovieDetailModel.Genres>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemTagBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvTags.text = item.title
    }

    override fun getItemCount(): Int {
        return list.size
    }

}