package com.example.nzr.modules.kanbanBoards

import android.app.Activity
import com.example.nzr.common.mvp.IView
import com.example.nzr.data.rest.models.listsCards
import com.example.nzr.modules.startScreen.StartActivity

interface KanbanContract{

    interface KanbanPresenter{

    }

    interface KanbanView : IView{
        override fun getActivity(): Activity
        fun initPagerAdapter(lists: List<listsCards>)
        fun getBoardId():String
    }

    interface KanbanModel{

    }
}