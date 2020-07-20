package com.weesnerdevelopment.allthethoughts

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_thought.*

class ThoughtItem(
    private val thought: Thought
) : Item() {
    override fun getLayout() = R.layout.item_thought

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            thought.apply {
                text_thought_time.text = formatTime(time)
                text_thought.text = value
            }
        }
    }
}
