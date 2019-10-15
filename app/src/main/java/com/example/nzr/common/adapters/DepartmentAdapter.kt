package com.example.nzr.common.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nzr.data.rest.models.board
import java.util.zip.Inflater

class DepartmentAdapter(val departs :List<board>) :RecyclerView.Adapter<DepartmentAdapter.DepartHolder>(){


    class DepartHolder(var view : View) : RecyclerView.ViewHolder(){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return departs.size
    }

    override fun onBindViewHolder(holder: DepartHolder, position: Int) {

    }
}