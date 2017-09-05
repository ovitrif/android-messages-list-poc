package poc.bringme.groupie.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity

class Router(private val activity: AppCompatActivity) {

    fun to(clazz: Class<*>) {
        activity.startActivity(Intent(activity, clazz))
    }
}
