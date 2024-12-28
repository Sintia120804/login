package com.sintia.crud_berita_user.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sintia.crud_berita_user.R
import com.sintia.crud_berita_user.model.ModelBerita

class BeritaAdapter (
    private val onClick : (ModelBerita) -> Unit
) : ListAdapter<ModelBerita, BeritaAdapter.BeritaViewHolder>(BeritaCallBack){

    class BeritaViewHolder (itemView: View, val onClick: (ModelBerita) -> Unit) :
        RecyclerView.ViewHolder(itemView)
    {
        private val imgBerita : ImageView = itemView.findViewById(R.id.imgBerita)
        private val judulBerita: TextView = itemView.findViewById(R.id.judulBerita)
        private val tglBerita: TextView = itemView.findViewById(R.id.tglBerita)

        //cek produk yang sudah ada saat ini
        private var currentBerita: ModelBerita? = null

        init {
            itemView.setOnClickListener(){
                currentBerita?.let{
                    onClick(it)
                }
            }
        }

        fun bind(berita : ModelBerita){
            currentBerita = berita
            //set data ke widget
            judulBerita.text = berita.judul
            tglBerita.text = berita.tgl_berita

            //set gambar
            Glide.with(itemView).load("http://192.168.157.145/beritadb/gambar_berita/"
                    +berita.gambar_berita).centerCrop().into(imgBerita)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeritaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_berita, parent, false)
        return BeritaViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: BeritaViewHolder, position: Int) {
        val berita = getItem(position)
        holder.bind(berita)
    }
}

object BeritaCallBack : DiffUtil.ItemCallback<ModelBerita>(){
    override fun areItemsTheSame(oldItem: ModelBerita, newItem: ModelBerita): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ModelBerita, newItem: ModelBerita): Boolean {
        return oldItem == newItem
    }

}