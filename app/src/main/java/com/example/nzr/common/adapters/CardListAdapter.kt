package com.example.nzr.common.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nzr.R
import com.example.nzr.data.rest.models.cardShort
import kotlinx.android.synthetic.main.card_kanban.view.*
import java.util.zip.Inflater

class CardListAdapter(list:List<cardShort>,val context :Context) :RecyclerView.Adapter<CardListAdapter.CardHolder>(){

    var cardList:List<cardShort> = list

    class CardHolder(val view: View) : RecyclerView.ViewHolder(view){
        var text : TextView = view.name
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.text.text = cardList.get(position).name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
            return CardHolder(LayoutInflater.from(context)
                .inflate(R.layout.card_kanban,parent,false))
    }
}