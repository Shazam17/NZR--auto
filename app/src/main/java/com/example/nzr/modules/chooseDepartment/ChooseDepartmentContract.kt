package com.example.nzr.modules.chooseDepartment

import com.example.nzr.data.rest.models.GenericBoardShort

interface ChooseDepartmentContract{

    interface DepartmentView{
        fun updateAdapter(depList:MutableList<GenericBoardShort>)
        fun setRefresh(refresh:Boolean)
    }

    interface  DepartmentPresenter{
        fun fetchDepartments()
    }

}