package android.app.rendering

import android.app.pathtracer.RenderFragment

interface AsyncTaskDoneListener {
    fun notifyFinish(frag : RenderFragment)
}