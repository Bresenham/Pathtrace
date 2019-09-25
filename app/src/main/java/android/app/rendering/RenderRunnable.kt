package android.app.rendering

import android.app.pathtracer.RenderFragment
import android.app.pathtracer.Renderer
import android.os.Handler
import android.os.Message

class RenderRunnable(private val renderer : Renderer, private val frag : RenderFragment, private val hndlr : Handler) : Runnable {
    override fun run() {
        renderer.renderOneStep(frag)
        val msg = hndlr.obtainMessage()
        msg.obj = frag
        hndlr.sendMessage(msg)
    }
}