package com.example.nzr.common.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nzr.R
import com.example.nzr.data.rest.models.board
import com.example.nzr.data.rest.models.genericBoardShort
import com.example.nzr.modules.kanbanBoards.KanbanBoardActivity
import kotlinx.android.synthetic.main.card_kanban.view.*

class DepartmentAdapter(var departs :MutableList<genericBoardShort>,val context: Context) :RecyclerView.Adapter<DepartmentAdapter.DepartHolder>(){


    class DepartHolder(var view : View,val context: Context) : RecyclerView.ViewHolder(view){
        val name = view.textCardKanban
        lateinit var id :String
        var vendor:Boolean = false
        init{
                view.setOnClickListener{
                    var intent = Intent(context,KanbanBoardActivity::class.java)
                    intent.putExtra("id",id)
                    if(vendor){
                        intent.putExtra("vendor","trello")

                    }else{
                        intent.putExtra("vendor","yandex")
                    }
                    context.startActivity(intent)
                }
        }
    }
    fun update(list :MutableList<genericBoardShort>){
        departs.clear()
        departs.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartHolder {
        return DepartmentAdapter.DepartHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.card_kanban, parent, false),context)
    }

    override fun getItemCount(): Int {
        return departs.size
    }

    override fun onBindViewHolder(holder: DepartHolder, position: Int) {
        holder.name.text = departs.get(position).name
        holder.id = departs.get(position).id

    }
}