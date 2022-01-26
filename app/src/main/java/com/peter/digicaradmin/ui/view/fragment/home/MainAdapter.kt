package com.peter.digicaradmin.ui.view.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peter.digicaradmin.R
import com.peter.digicaradmin.data.model.ConsultationModel
import kotlinx.android.synthetic.main.item_consultation.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainAdapter @ExperimentalCoroutinesApi constructor(
    private val data: ArrayList<ConsultationModel>
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_consultation, parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.name.text = data[position].name
        holder.itemView.timeTxt.text = data[position].timeTxt
        holder.itemView.txt.text = data[position].txt
    }

    override fun getItemCount(): Int = data.size


}