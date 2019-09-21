package android.app.gui

import android.app.pathtracer.Col
import android.content.Context
import android.graphics.Paint
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*

class Screen : SurfaceView, SurfaceHolder.Callback, Runnable {

    private var stackWithChanges = Stack<Pair<Pair<Int, Int>, Array<Array<Col>>>>()
    private lateinit var surfaceHolder : SurfaceHolder

    private val paint = Paint().apply{
        setARGB(255, 255, 0, 0)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        holder.addCallback(this)
    }

    fun pushChanges(data: Stack<Pair<Pair<Int, Int>, Array<Array<Col>>>>) {
        stackWithChanges = data
        Thread(this).start()
    }


    override fun run() {
        val ctx = surfaceHolder.lockCanvas()
        while(!stackWithChanges.empty()) {
            val data = stackWithChanges.pop()

            val startX = data.first.first
            val startY = data.first.second
            val xLength = data.second.size
            val yLength = data.second[0].size

            for(x in startX until startX + xLength) {
                for(y in startY until startY + yLength) {
                    val col = data.second[x - startX][y - startY]
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