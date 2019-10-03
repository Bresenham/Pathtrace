package app.android.pathtrace.rendering

import android.os.Handler
import android.util.Log
import app.android.pathtrace.pathtracer.RenderFragment
import app.android.pathtrace.pathtracer.Renderer
import app.android.pathtrace.pathtracer.Scene

class RenderRunnable(private val s : Scene, private val width: Int, private val height : Int, private val traceDepth: Int, private val frag : RenderFragment, private val hndlr : Handler) : Runnable {

    override fun run() {
        Log.d("Task", "Started Tile ${frag.id}")
        Renderer.renderOneStep(s, width, height, traceDepth, frag)
        val msg = hndlr.obtainMessage()
        msg.obj = frag
        hndlr.sendMessage(msg)
    }

}