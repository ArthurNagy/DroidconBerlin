package com.arthurnagy.droidcon.feature.agenda

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import com.arthurnagy.droidcon.architecture.repository.SessionRepository
import com.arthurnagy.droidcon.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidcon.feature.agenda.list.MyAgendaAdapter
import com.arthurnagy.droidcon.feature.shared.ViewModelBoundAdapter
import com.arthurnagy.droidcon.model.Session
import com.arthurnagy.droidcon.util.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MyAgendaViewModel @Inject constructor(
        private val sessionRepository: SessionRepository) : DroidconViewModel() {
    val adapter = MyAgendaAdapter()
    val swipeRefreshState = ObservableBoolean()
    val selectedSession = MutableLiveData<Session>()

    init {
        adapter.setItemClickListener(object : ViewModelBoundAdapter.AdapterItemClickListener {
            override fun onItemClicked(position: Int) {
                selectedSession.value = adapter.getItem(position)
            }
        })
    }

    fun subscribe() {
        disposables += sessionRepository.stream()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { sessions ->
                    val mySessions = sessions.filter { session ->
                        Session.isIntermission(session) || session.isSaved
                    }
                    adapter.replace(mySessions.sortedWith(compareBy { it.startDate }))
                }
    }

    fun unsubscribe() {
        disposables.clear()
    }

    fun load() {
        swipeRefreshState.set(true)
        disposables += sessionRepository.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    swipeRefreshState.set(false)
                }, {
                    swipeRefreshState.set(false)
                })
    }

    fun refresh() {
        swipeRefreshState.set(true)
        disposables += sessionRepository.refresh()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    swipeRefreshState.set(false)
                }, {
                    swipeRefreshState.set(false)
                })
    }

    fun removeSavedSessionFromAgenda() {
        adapter.removeItemToBeRemoved(doOnRemove = { session ->
            session.isSaved = false
            disposables += sessionRepository.update(session)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({}, {})
        })
    }

    fun isHeaderItem(position: Int): Boolean = when (position) {
        0 -> true
        else -> {
            val previousItemCalendar = Calendar.getInstance()
            val currentItemCalendar = Calendar.getInstance()
            previousItemCalendar.time = adapter.getItem(position - 1).startDate
            currentItemCalendar.time = adapter.getItem(position).startDate
            previousItemCalendar[Calendar.DAY_OF_MONTH] != currentItemCalendar[Calendar.DAY_OF_MONTH]
        }
    }

    fun getHeaderItemTitle(position: Int): String
            = SimpleDateFormat(STICKY_DATE_PATTERN, Locale.getDefault()).format(adapter.getItem(position).startDate)

    companion object {
        private const val STICKY_DATE_PATTERN = "MMMM d"
    }

}