package com.example.nzr.modules.kanbanBoards

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.nzr.R
import com.example.nzr.common.adapters.KanbanPagerAdapter
import com.example.nzr.data.rest.models.board
import com.example.nzr.data.rest.models.listsCards
import kotlinx.android.synthetic.main.activty_kanban.*

class KanbanBoardActivity :AppCompatActivity() ,KanbanContract.KanbanView{


    var presenter =  KanbanPresenter(this)

    var boardIdIn : String = ""
    lateinit var adapter : KanbanPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_kanban)
        boardIdIn = intent.extras!!.getString("id")!!
        Log.d("kanban",boardIdIn)

        presenter.fetchListsRep()
    }

    override fun initPagerAdapter(lists: List<listsCards>){
        adapter = KanbanPagerAdapter(lists,this)
        pager.adapter = adapter
    }

    override fun getBoardId():String {
        return boardIdIn
    }

    override fun getActivity() : Activity{
        return this
    }
}