package com.weesnerdevelopment.allthethoughts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_thought.*

class AddThoughtFragment : Fragment() {
    private lateinit var backend: Backend
    private lateinit var auth: Auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_thought, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        auth = FireAuth(requireActivity() as AppCompatActivity)
        backend = FireBackend(
            auth.current ?: throw IllegalArgumentException("Somehow auth was not valid")
        )

        edit_text_new_thought.addTextChangedListener {
            button_save_thought.isEnabled = !it.isNullOrBlank()
        }

        button_save_thought.apply {
            isEnabled = false
            setOnClickListener {
                backend.add(
                    Thought(
                        owner = auth.current?.id,
                        value = edit_text_new_thought.text.toString()
                    )
                ) {
                    findNavController().popBackStack()
                }
            }
        }
    }
}
