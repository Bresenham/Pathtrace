package android.app.rendering

import android.app.pathtracer.Col
import android.app.pathtracer.RenderFragment
import android.app.pathtracer.Renderer
import android.os.AsyncTask


class RenderAsyncTask(private val listener : AsyncTaskDoneListener,  private val renderer : Renderer) : AsyncTask<Int, Void, RenderFragment>() {

    var fromX : Int = 0
    var xLength: Int = 0

    var fromY: Int = 0
    var yLength: Int = 0

    override fun doInBackground(vararg p0: Int?): RenderFragment {
        fromX = p0[0]!!
        xLength = p0[1]!!

        fromY = p0[2]!!
        yLength = p0[3]!!

        val fragment = RenderFragment(fromX, xLength, fromY, yLength, Array(xLength) {Array(yLength) {
            Col(
                0,
                0,
                0
            )
        } })

        renderer.renderOneStep(fragment)

        return fragment
    }

    override fun onPostExecute(result: RenderFragment) {
        listener.pushChanges(result)
    }
}