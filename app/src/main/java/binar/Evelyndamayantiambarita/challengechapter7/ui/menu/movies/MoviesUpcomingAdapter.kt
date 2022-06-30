package binar.Evelyndamayantiambarita.challengechapter7.ui.menu.movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.Evelyndamayantiambarita.challengechapter7.Constant
import binar.Evelyndamayantiambarita.challengechapter7.data.api.tmdb.MoviesPopularResponse
import binar.Evelyndamayantiambarita.challengechapter7.data.api.tmdb.MoviesUpcomingResponse
import binar.Evelyndamayantiambarita.challengechapter7.databinding.ListItemMoviesBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class MoviesUpcomingAdapter(private val listener: EventListener, private var list: List<MoviesUpcomingResponse.ResultMoviesUpcoming>)
    : RecyclerView.Adapter<MoviesUpcomingAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemMoviesBinding)
        : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<MoviesUpcomingResponse.ResultMoviesUpcoming>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        Glide.with(holder.binding.root.context)
            .load(Constant.ImageUrl.IMAGE_URL + item.poster_path)
            .transform(RoundedCorners(20))
            .into(holder.binding.ivListMoviesImage)

        holder.binding.tvListMoviesTitle.text = item.title

        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface EventListener {
        fun onClick(item: MoviesUpcomingResponse.ResultMoviesUpcoming)
    }

}