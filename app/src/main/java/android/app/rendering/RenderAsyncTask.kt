package android.app.rendering

import android.app.pathtracer.RenderFragment
import android.app.pathtracer.Renderer
import android.os.AsyncTask


class RenderAsyncTask(private val notifier : AsyncTaskDoneListener, private val renderer : Renderer) : AsyncTask<RenderFragment, Int, Int>() {

    override fun doInBackground(vararg p0: RenderFragment?) : Int {
        renderer.renderOneStep(p0[0]!!)
        return p0[0]!!.id
    }

    override fun onPostExecute(tileID: Int) {
        notifier.notifyFinish(tileID)
    }
}