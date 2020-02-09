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

package io.re4.month

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import io.re4.R
import io.re4.timeline.TimelineViewModel
import kotlinx.android.synthetic.main.month_fragment.view.*
import java.time.LocalDate
import java.time.ZoneId

class MonthFragment : Fragment() {
    private lateinit var calendar_view: CalendarView

    private val timelineViewModel: TimelineViewModel by activityViewModels()
    private val monthViewModel: MonthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.month_fragment, container)
        calendar_view = view.calendar_view

        calendar_view.setOnDateChangeListener { _, year, month, dayOfMonth ->
            monthViewModel.date.value = LocalDate.of(year, month + 1, dayOfMonth)
        }

        timelineViewModel.date.observe(viewLifecycleOwner, Observer { date ->
            calendar_view.date = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        })

        return view
    }
}