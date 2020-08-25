package com.partyremote.android.ui.components

import android.content.Context
import android.util.AttributeSet
import com.partyremote.android.R
import com.partyremote.android.entities.PartySession
import kotlinx.android.synthetic.main.ui_party_session_view.view.*
import sk.backbone.parent.ui.components.StateSavingLinearLayout

class PartySessionView : StateSavingLinearLayout {
    constructor(context: Context) : super(context){
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        inflate(context, R.layout.ui_party_session_view, this)
    }

    fun bindData(partySession: PartySession){
        val isOwner = partySession.ownerPassword != null

        val title: String = if(isOwner){
            "${partySession.title}"
        }
        else {
            "${partySession.title} (${partySession.author})"
        }

        ui_main_session_view_name_of_session_and_owner.text = title
        ui_main_session_view_songs_count.text = partySession.songsCount.toString()




    }
}