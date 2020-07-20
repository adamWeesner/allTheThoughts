package com.weesnerdevelopment.allthethoughts

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_thought_date.*

class ThoughtDateItem(
    private val date: String,
    private val click: () -> Unit
) : Item() {
    override fun getLayout() = R.layout.item_thought_date

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            layout_thought_date.setOnClickListener {
                click()
            }
            text_thought_date.text = date
        }
    }
}
