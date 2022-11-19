package io.github.ziginsider.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import io.github.ziginsider.ui.view.MainActivity
import javax.inject.Singleton

@Component(modules = [AppModule::class, AppBindsModule::class, ApiModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}