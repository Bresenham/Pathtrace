package android.app.rendering

import android.app.pathtracer.RenderFragment
import android.app.pathtracer.Renderer
import android.os.AsyncTask


class RenderAsyncTask(private val notifier : AsyncTaskDoneListener, private val renderer : Renderer) : AsyncTask<RenderFragment, Unit, Unit>() {

    override fun doInBackground(vararg p0: RenderFragment?) {
        renderer.renderOneStep(p0[0]!!)
    }

    override fun onPostExecute(result: Unit) {
        notifier.notifyFinish()
    }
}