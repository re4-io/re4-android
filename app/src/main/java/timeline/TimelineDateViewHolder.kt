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

import android.graphics.Color
import android.util.TypedValue
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import io.re4.R
import io.re4.databinding.TimelineDateViewBinding
import java.time.DayOfWeek
import java.time.LocalDate

class TimelineDateViewHolder(private val view: TimelineDateViewBinding)
    : RecyclerView.ViewHolder(view.root) {
    var date: LocalDate? = null

    private val colorWeekDay: Int
    private val colorWeekendDay: Int

    init {
        val context = view.root.context
        val theme = context.theme

        val value = TypedValue()
        theme.resolveAttribute(R.attr.colorAccent, value, true)
        colorWeekendDay = value.data

        val typedArray = theme.obtainStyledAttributes(
                R.style.TextAppearance_AppCompat, intArrayOf(android.R.attr.textColorSecondary))
        colorWeekDay = typedArray.getColor(0, Color.RED)
        typedArray.recycle()
    }

    fun bind(date: LocalDate?) {
        this.date = date

        when(date!!.dayOfWeek) {
            DayOfWeek.SATURDAY, DayOfWeek.SUNDAY -> {
                view.dayweek.setTextColor(colorWeekendDay)
                view.daymonth.setTextColor(colorWeekendDay)
            }
            else -> {
                view.dayweek.setTextColor(colorWeekDay)
                view.daymonth.setTextColor(colorWeekDay)
            }
        }

        view.date = date

        view.root.setOnClickListener {
            val action = TimelineFragmentDirections.timelineFragmentToDayFragment()
            view.root.findNavController().navigate(action)
        }
    }
}
