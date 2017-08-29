package com.arthurnagy.droidconberlin.injection.fragment

import com.arthurnagy.droidconberlin.feature.agenda.MyAgendaFragment
import com.arthurnagy.droidconberlin.feature.schedule.ScheduleFragment
import com.arthurnagy.droidconberlin.feature.schedule.SchedulePagerFragment
import com.arthurnagy.droidconberlin.feature.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeMyAgendaFragment(): MyAgendaFragment

    @ContributesAndroidInjector
    abstract fun contributeSchedulePagerFragment(): SchedulePagerFragment

    @ContributesAndroidInjector
    abstract fun contributeScheduleFragment(): ScheduleFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

}