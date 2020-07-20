package com.weesnerdevelopment.allthethoughts

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_thought.*
import java.text.SimpleDateFormat
import java.util.*

class ThoughtItem(
    private val thought: Thought
) : Item() {
    override fun getLayout() = R.layout.item_thought

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            thought.apply {
                text_thought_time.text = formatDate(time)
                text_thought.text = value
            }
        }
    }
}

fun formatDate(date: Long?): String? {
    val pattern = "MMM dd, yyyy h:mm:ss a"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return simpleDateFormat.format(date)
}
