package com.example.nzr.modules.kanbanBoards

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.nzr.R
import com.example.nzr.common.adapters.KanbanPagerAdapter
import com.example.nzr.data.rest.models.listsCards
import kotlinx.android.synthetic.main.activty_kanban.*
import android.view.Menu
import android.content.Intent
import android.view.MenuItem
import com.example.nzr.modules.AddCard.AddCardActivity


class KanbanBoardActivity :AppCompatActivity() ,KanbanContract.KanbanView{


    var presenter =  KanbanPresenter(this)

    var boardIdIn : String = ""
    lateinit var adapter : KanbanPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.nzr.R.layout.activty_kanban)
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.example.nzr.R.menu.menu_board, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            com.example.nzr.R.id.add  ->{
                var intent = Intent(this,AddCardActivity::class.java)
                startActivity(intent)
                return true

            }


            else ->
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item)
        }
    }
}