package com.example.nzr.modules.startScreen

import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.models.board

class DepartmentPresenter(var view:DepartmentContract.DepartmentView) : DepartmentContract.DepartmentPresenter{


    override fun fetchDepartments(): List<board> {
        RetrofitFabric().getTrello().getAllBoards()
    }
}