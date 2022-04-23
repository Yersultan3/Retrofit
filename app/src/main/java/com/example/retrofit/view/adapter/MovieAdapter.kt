package com.example.retrofit.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.model.api.Movie
import com.example.retrofit.R
import com.example.retrofit.databinding.MovieItemBinding
import com.squareup.picasso.Picasso

class MovieAdapter(
//    private val list: List<Movie>,
    val itemClickListener: RecyclerViewItemClick? = null
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Any>() {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return when {
                oldItem is Movie && newItem is Movie -> {
                    oldItem.id == newItem.id
                }
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return when {
                oldItem is Movie && newItem is Movie -> {
                    oldItem as Movie == newItem as Movie
                }
                else -> false
            }
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_POST -> {
                val binding: MovieItemBinding =
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.movie_item,
                        parent,
                        false
                    )
                MovieViewHolder(binding)
            }
            else -> {
                throw IllegalStateException("Incorrect ViewType found")
            }
        }

//        val binding: MovieItemBinding =
//            DataBindingUtil.inflate(
//                inflater,
//                R.layout.movie_item,
//                parent,
//                false
//            )
//        return MovieViewHolder(binding)
    }

//    override fun getItemCount(): Int = list.size
    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list: List<Any>?) {
        differ.submitList(list)
    }

    companion object {
        const val VIEW_TYPE_POST = 0
        const val VIEW_TYPE_POST2 = 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val viewHolder = holder as MovieViewHolder

        when (holder.itemViewType) {
            VIEW_TYPE_POST -> {
                val viewHolder = holder as MovieViewHolder
                viewHolder.initContent(differ.currentList[position] as Movie)
                Picasso.get().load("https://image.tmdb.org/t/p/w500" + (differ.currentList[position] as Movie).poster_path).into(viewHolder.binding.moviePoster)

            }
        }

//        viewHolder.initContent(list[position])
//        Picasso.get().load("https://image.tmdb.org/t/p/w500" + list[position].poster_path).into(viewHolder.binding.moviePoster)
    }

    inner class MovieViewHolder(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        fun initContent(data: Movie?)
        fun initContent(data: Movie?) {
            binding.data = data
            binding.executePendingBindings()

            binding.clMain.setOnClickListener {
                itemClickListener?.itemClick(data!!)
            }
        }
    }
//
//    fun clearAll() {
//        (list as? ArrayList<Movie>)?.clear()
//        notifyDataSetChanged()
//    }

    override fun getItemViewType(position: Int): Int =
        when (differ.currentList[position]) {
            is Movie -> VIEW_TYPE_POST
            else -> throw IllegalStateException("Incorrect ViewType found")
        }

    interface RecyclerViewItemClick {
        fun itemClick( item: Movie)
    }
}


