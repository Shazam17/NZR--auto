package com.example.nzr.modules.kanbanBoards

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nzr.data.rest.models.ListsCards
import kotlinx.android.synthetic.main.activty_kanban.*
import android.view.Menu
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.modules.addCard.AddCardActivity


class KanbanBoardActivity :AppCompatActivity() ,KanbanContract.KanbanView{


    private var presenter =  KanbanPresenter(this)

    private var trelloId : String? = null
    private var yandexId : String? = null
    private var name : String? = null
    lateinit var adapter : KanbanPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.nzr.R.layout.activty_kanban)
        trelloId = intent.extras?.getString("trello")?:"no"
        yandexId = intent.extras?.getString("yandex")?:"no"
        name = intent.extras!!.getString("name")
        title = name
        Log.d("kanban","trello id = ${trelloId} yandexId = ${yandexId}")
        if(trelloId != null && yandexId != null){
            presenter.fetch()
        }else{
            if(trelloId != null){
                presenter.fetchListsRepTrello()
            }
            if(yandexId != null){
                presenter.fetchListsRepYandex()
            }
        }
        swipeToRefreshKanban.setOnRefreshListener {
            presenter.updateList()
        }
    }

    override fun setRefresh(refresh:Boolean){
        swipeToRefreshKanban.setRefreshing(refresh)
    }
    override fun onResume() {
        super.onResume()
        Log.d("kanban","resuming")
        presenter.updateList()
    }

    override fun initPagerAdapter(lists: List<ListsCards>){
        adapter = KanbanPagerAdapter(lists, this)
        adapter.notifyDataSetChanged()
        pager.adapter = adapter
    }

    override fun getTrelloBoardId():String? {
        return trelloId
    }

    override fun getYandexBoardId():String? {
        return yandexId
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
                if(presenter.lists.isEmpty() && trelloId != "no"){
                    //var builder = AlertDialog.Builder(this)
                    //builder.setTitle("Enter name of list")
                    //builder.setPositiveButton("Enter name of list")
                    presenter.createTrelloBoard()
                }
                if(presenter.lists.isNotEmpty()){
                    //TODO добавить логику добавления списка
                    var intent = Intent(this,AddCardActivity::class.java)
                    intent.putExtra("trelloListId",presenter.getTrelloListId(adapter.current))
                    intent.putExtra("yandexId",yandexId)
                    startActivity(intent)
                }
                if( yandexId != "no"){
                    var intent = Intent(this,AddCardActivity::class.java)
                    intent.putExtra("yandexId",yandexId)
                    intent.putExtra("trelloListId","no")
                    startActivity(intent)
                }

                return true
            }
            else ->
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item)
        }
    }
}