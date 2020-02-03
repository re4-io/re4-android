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

import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import java.time.LocalDate

class TimelineDataSource : ItemKeyedDataSource<LocalDate, LocalDate>() {
    override fun loadInitial(params: LoadInitialParams<LocalDate>, callback: LoadInitialCallback<LocalDate>) {
        val result = 0L.until(params.requestedLoadSize - 1)
                .map { i -> params.requestedInitialKey?.plusDays(i) }
        callback.onResult(result)
    }

    override fun loadAfter(params: LoadParams<LocalDate>, callback: LoadCallback<LocalDate>) {
        val result = 1L.until(params.requestedLoadSize)
                .map { i -> params.key.plusDays(i) }
        callback.onResult(result)
    }

    override fun loadBefore(params: LoadParams<LocalDate>, callback: LoadCallback<LocalDate>) {
        val result = params.requestedLoadSize.toLong().downTo(1)
                .map { i -> params.key.minusDays(i) }
        callback.onResult(result)
    }

    override fun getKey(item: LocalDate): LocalDate {
        return item
    }

    class Factory : DataSource.Factory<LocalDate, LocalDate>() {
        override fun create(): DataSource<LocalDate, LocalDate>
                = TimelineDataSource()
    }
}