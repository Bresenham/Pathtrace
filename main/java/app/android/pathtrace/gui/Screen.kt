package app.android.pathtrace.gui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import app.android.pathtrace.pathtracer.RenderFragment
import java.util.concurrent.*

class Screen : SurfaceView, SurfaceHolder.Callback, Runnable {

    private var isRunning = false
    private var updateQueue = ArrayBlockingQueue<RenderFragment>(128)
    private lateinit var surfaceHolder : SurfaceHolder

    private val paint = Paint().apply{
        setARGB(255, 255, 0, 0)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        holder.addCallback(this)
    }

    fun pushChanges(data: RenderFragment) {
        updateQueue.add(data)
        if(!isRunning) {
            Thread(this).start()
        }
    }

    @ExperimentalUnsignedTypes
    override fun run() {
        isRunning = true
        while(updateQueue.isNotEmpty()) {
            val frag = updateQueue.remove()

            Log.d("Task", "Rendering Tile ${frag.id}")

            val ctx = surfaceHolder.lockCanvas(Rect(frag.fromX, frag.fromY, frag.fromX + frag.xLength, frag.fromY + frag.yLength))
            for (x in frag.fromX until frag.fromX + frag.xLength) {
                for (y in frag.fromY until frag.fromY + frag.yLength) {
                    val col = frag.pixelData[x - frag.fromX][y - frag.fromY]
                    paint.apply { setARGB(255, col.r.toInt(), col.g.toInt(), col.b.toInt()) }
                    ctx.drawPoint(
                        x.toFloat(),
                        y.toFloat(),
                        paint
                    )
                }
            }
            surfaceHolder.unlockCanvasAndPost(ctx)
        }

        isRunning = false
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        surfaceHolder = p0!!

        surfaceHolder.addCallback(this)
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT)
        Log.d("Task", "Surface Created called!")
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {

    }
}