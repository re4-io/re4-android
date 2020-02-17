/*
 * Copyright (C) 2020  Vladimir Berlev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.re4.timeline

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.re4.R
import io.re4.month.MonthViewModel
import kotlinx.android.synthetic.main.timeline_fragment.view.*
import java.time.LocalDate

class TimelineFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager

    val timelineViewModel: TimelineViewModel by activityViewModels()
    val monthViewModel: MonthViewModel by activityViewModels()

    val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setPrefetchDistance(30)
            .setEnablePlaceholders(true)
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.timeline_fragment, container, false)

        recyclerView = view.recyclerView

        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        recyclerView.addItemDecoration(DividerItemDecoration(
                recyclerView.context, layoutManager.orientation))

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val vh: TimelineDateViewHolder = recyclerView.findViewHolderForAdapterPosition(
                        layoutManager.findFirstVisibleItemPosition()) as TimelineDateViewHolder
                timelineViewModel.date.value = vh.date
            }
        })

        loadDate(timelineViewModel.date.value!!)

        monthViewModel.date.observe(viewLifecycleOwner, Observer {
            date -> loadDate(date)
        })

        return view
    }

    private fun loadDate(date: LocalDate) {
        LivePagedListBuilder<LocalDate, LocalDate>(TimelineDataSource.Factory(), config)
                .setInitialLoadKey(date)
                .build()
                .observe(viewLifecycleOwner, Observer { list ->
                    val adapter = TimelineDateAdapter()
                    recyclerView.swapAdapter(adapter, false)
                    recyclerView.stopScroll()
                    adapter.submitList(list)
                })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.timeline, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.today -> {
                loadDate(LocalDate.now())
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}