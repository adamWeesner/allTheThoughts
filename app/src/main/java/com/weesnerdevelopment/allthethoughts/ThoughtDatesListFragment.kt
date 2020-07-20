package com.weesnerdevelopment.allthethoughts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_thoughts_list.*

class ThoughtDatesListFragment : Fragment() {
    private val thoughtsDateAdapter = GroupAdapter<GroupieViewHolder>()
    private lateinit var auth: Auth
    private var thoughtItems: List<Thought> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_thoughts_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        recycler_view_thoughts.apply {
            adapter = thoughtsDateAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        auth = FireAuth(requireActivity() as AppCompatActivity)
        if (auth.current != null) {
            FireBackend(
                auth.current ?: throw IllegalArgumentException("Somehow auth was not valid")
            ).apply {
                getAll {
                    if (thoughtItems == it?.reversed()) return@getAll

                    thoughtItems = it?.reversed() ?: listOf()

                    val singleDates = mutableListOf<String>()

                    thoughtItems.forEach {
                        if (!singleDates.contains(formatDate(it.time))) singleDates.add(
                            formatDate(
                                it.time
                            )!!
                        )
                    }

                    singleDates.map { date ->
                        ThoughtDateItem(date) {
                            findNavController().navigate(
                                ThoughtDatesListFragmentDirections.actionThoughtDatesListFragmentToThoughtsListFragment(
                                    thoughtItems.filter { formatDate(it.time) == date }
                                        .toTypedArray()
                                        ?: arrayOf()
                                )
                            )
                        }
                    }.also {
                        thoughtsDateAdapter.update(it)
                    }
                }
            }
        }
    }
}
