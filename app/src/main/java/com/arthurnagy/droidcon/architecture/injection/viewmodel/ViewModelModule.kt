package com.arthurnagy.droidcon.architecture.injection.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.arthurnagy.droidcon.architecture.viewmodel.DroidconViewModelFactory
import com.arthurnagy.droidcon.feature.agenda.MyAgendaViewModel
import com.arthurnagy.droidcon.feature.info.InfoViewModel
import com.arthurnagy.droidcon.feature.schedule.SchedulePagerViewModel
import com.arthurnagy.droidcon.feature.schedule.list.ScheduleViewModel
import com.arthurnagy.droidcon.feature.session.SessionDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @ViewModelKey(MyAgendaViewModel::class)
    @IntoMap
    abstract fun bindMyAgendaViewModel(myAgendaViewModel: MyAgendaViewModel): ViewModel

    @Binds
    @ViewModelKey(SchedulePagerViewModel::class)
    @IntoMap
    abstract fun bindSchedulePagerViewModel(schedulePagerViewModel: SchedulePagerViewModel): ViewModel

    @Binds
    @ViewModelKey(ScheduleViewModel::class)
    @IntoMap
    abstract fun bindScheduleViewModel(scheduleViewModel: ScheduleViewModel): ViewModel

    @Binds
    @ViewModelKey(InfoViewModel::class)
    @IntoMap
    abstract fun bindSettingsViewModel(infoViewModel: InfoViewModel): ViewModel

    @Binds
    @ViewModelKey(SessionDetailViewModel::class)
    @IntoMap
    abstract fun bindSessionDetailViewModel(sessionDetailViewModel: SessionDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: DroidconViewModelFactory): ViewModelProvider.Factory

}
