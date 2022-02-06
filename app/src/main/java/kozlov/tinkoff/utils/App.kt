package kozlov.tinkoff.utils

import android.app.Application
import kozlov.tinkoff.di.DaggerApplicationComponent

class App: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }

}