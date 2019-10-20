package com.example.nzr.modules.addCard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nzr.R
import kotlinx.android.synthetic.main.activity_create_card.*

class AddCardActivity  :AppCompatActivity(), AddCardContract.AddCardView{


    var presenter = AddCardPresenter(this)
    var vendor = intent.extras?.getBoolean("vendor")
    var id = intent.extras?.getString("id")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)


        buttonCreateCard.setOnClickListener{
            presenter.createCard(cardName.text.toString(),id!!,vendor!!)
        }

    }



}