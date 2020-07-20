package com.weesnerdevelopment.allthethoughts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_thoughts_list.*

class ThoughtsListFragment : Fragment() {
    private val thoughtsAdapter = GroupAdapter<GroupieViewHolder>()
    private lateinit var backend: Backend
    private lateinit var auth: Auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_thoughts_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FireAuth(requireActivity() as AppCompatActivity)
        backend = FireBackend(
            auth.current ?: throw IllegalArgumentException("Somehow auth was not valid")
        )

        recycler_view_thoughts.apply {
            adapter = thoughtsAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        backend.getAll {
            it?.map {
                ThoughtItem(it)
            }?.also {
                thoughtsAdapter.update(it)
            }
        }
    }
}
