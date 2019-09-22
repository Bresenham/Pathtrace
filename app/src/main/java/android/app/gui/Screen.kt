package android.app.gui

import android.app.pathtracer.RenderFragment
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingDeque

class Screen : SurfaceView, SurfaceHolder.Callback, Runnable {

    private var isRunning = false
    private var updateQueue = ArrayBlockingQueue<RenderFragment>(24)
    private lateinit var surfaceHolder : SurfaceHolder

    private val paint = Paint().apply{
        setARGB(255, 255, 0, 0)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        holder.addCallback(this)
    }

    fun pushChanges(data: RenderFragment) {
        updateQueue.add(data)
        Log.d("Task", updateQueue.toString())
        if(!isRunning) {
            Thread(this).start()
        }
    }

    override fun run() {
        isRunning = true
        while(true) {
            if (updateQueue.isNotEmpty()) {
                val frag = updateQueue.remove()

                Log.d("Task", "Rendering tile ${frag.id}")

                val ctx = surfaceHolder.lockCanvas(Rect(frag.fromX, frag.fromY, frag.fromX + frag.xLength, frag.fromY + frag.yLength))
                for (x in frag.fromX until frag.fromX + frag.xLength) {
                    for (y in frag.fromY until frag.fromY + frag.yLength) {
                        val col = frag.pixelData[x - frag.fromX][y - frag.fromY]
                        paint.apply { setARGB(255, col.r, col.g, col.b) }
                        ctx.drawPoint(
                            x.toFloat(),
                            y.toFloat(),
                            paint
                        )
                    }
                }
                surfaceHolder.unlockCanvasAndPost(ctx)
            }
        }
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