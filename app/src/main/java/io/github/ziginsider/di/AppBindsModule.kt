package io.github.ziginsider.di

import dagger.Binds
import dagger.Module
import io.github.ziginsider.base.AppResources
import io.github.ziginsider.base.AppResourcesImpl

@Module
interface AppBindsModule {

    @Binds
    fun bindAppResources(appResources: AppResourcesImpl): AppResources
}
