package android.app.gui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.min

class Screen : SurfaceView, SurfaceHolder.Callback {

    private val paint : Paint = Paint().apply{
        setARGB(255, 255, 0, 0)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        holder.addCallback(this)
        setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSLUCENT)
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        val ctx = holder.lockCanvas()
        ctx.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (min(width / 2, height / 2)).toFloat(),
            paint
        )

        holder.unlockCanvasAndPost(ctx)
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {

    }
}