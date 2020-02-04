/*
 * Copyright (C) 2019-2020  Vladimir Berlev
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

package io.re4

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import io.re4.timeline.TimelineViewModel
import kotlinx.android.synthetic.main.main_activity.*
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))

        val timelineViewModel: TimelineViewModel by viewModels()

        timelineViewModel.date.observe(this,  Observer<LocalDate>{ date ->
            toolbar.title = date.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
            toolbar.subtitle = date.year.toString()
        })
    }

    public fun createEvent(view: View) {
        val intent = Intent(this, CreateEventActivity::class.java)
        startActivity(intent)
    }

    external fun ping(): Int

    companion object {
        init {
            System.loadLibrary("re4-jni")
        }
    }
}
