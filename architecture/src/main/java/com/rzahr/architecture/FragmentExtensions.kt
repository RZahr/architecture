package com.rzahr.architecture

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun Fragment.createLinearRecyclerView(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>, withDecoration: Boolean = false) {

    recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    recyclerView.setHasFixedSize(true)
    recyclerView.adapter = adapter

    if (withDecoration) recyclerView.addItemDecoration(
        DividerItemDecoration(recyclerView.context,
            DividerItemDecoration.VERTICAL)
    )
}