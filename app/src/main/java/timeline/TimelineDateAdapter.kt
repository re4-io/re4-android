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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import io.re4.databinding.TimelineDateViewBinding
import java.time.LocalDate

class TimelineDateAdapter : PagedListAdapter<LocalDate, TimelineDateViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : TimelineDateViewHolder {

        val view = TimelineDateViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TimelineDateViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimelineDateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LocalDate>() {
            override fun areContentsTheSame(oldItem: LocalDate, newItem: LocalDate)
                    = oldItem == newItem

            override fun areItemsTheSame(oldItem: LocalDate, newItem: LocalDate)
                    = oldItem == newItem
        }
    }
}