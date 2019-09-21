package android.app.gui

import android.app.pathtracer.RenderFragment
import android.content.Context
import android.graphics.Paint
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class Screen : SurfaceView, SurfaceHolder.Callback, Runnable {

    private lateinit var stackWithChanges : MutableList<RenderFragment>
    private lateinit var surfaceHolder : SurfaceHolder

    private val paint = Paint().apply{
        setARGB(255, 255, 0, 0)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        holder.addCallback(this)
    }

    fun pushChanges(data: MutableList<RenderFragment>) {
        stackWithChanges = data
        Thread(this).start()
    }

    override fun run() {
        val ctx = surfaceHolder.lockCanvas()

        stackWithChanges.forEach { frag ->
            for(x in frag.fromX until frag.fromX + frag.xLength) {
                for(y in frag.fromY until frag.fromY + frag.yLength) {
                    val col = frag.pixelData[x - frag.fromX][y - frag.fromY]
                    ctx.drawPoint(x.toFloat(), y.toFloat(), paint.apply { setARGB(255, col.r, col.g, col.b) })
                }
            }
        }

        surfaceHolder.unlockCanvasAndPost(ctx)
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        if(!::surfaceHolder.isInitialized) {
            surfaceHolder = p0!!

            surfaceHolder.addCallback(this)
            surfaceHolder.setFormat(PixelFormat.TRANSLUCENT)
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {

    }
}