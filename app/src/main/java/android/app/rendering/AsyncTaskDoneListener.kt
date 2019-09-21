package android.app.rendering

import android.app.pathtracer.Col

interface AsyncTaskDoneListener {
    fun pushChanges(data : Pair<Pair<Int, Int>, Array<Array<Col>>>)
}