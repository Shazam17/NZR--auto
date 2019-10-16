package com.example.nzr.modules.startScreen

import android.content.Context
import com.example.nzr.data.rest.models.board
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

interface StartContract{


    interface StartView{
        fun toNextScreen()
        fun getActivity():StartActivity
        fun getCode() : String?
    }

    interface StartPresenter{
        fun handleSignInResult(completedTask: Task<GoogleSignInAccount>)
        fun signIn()
    }

    interface StartModel{

    }

}

interface DepartmentContract{

    interface DepartmentView{
        fun initAdapter(list:List<board>?)
    }

    interface  DepartmentPresenter{
        fun fetchDepartments()
    }

    interface DepartmentModel{

    }


}