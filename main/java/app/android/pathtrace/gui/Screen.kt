package app.android.pathtrace.gui

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import app.android.pathtrace.pathtracer.RenderFragment
import app.android.pathtrace.rendering.UpdateRunnable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.*

class Screen : SurfaceView, SurfaceHolder.Callback {

    private lateinit var surfaceHolder : SurfaceHolder

    private val workQueue : BlockingQueue<Runnable> = LinkedBlockingQueue<Runnable>(32)
    private val threadPool : ThreadPoolExecutor = ThreadPoolExecutor(2, 128, 1000, TimeUnit.SECONDS, workQueue)

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        holder.addCallback(this)
    }

    fun pushChanges(frag: RenderFragment) {
        threadPool.execute(
            UpdateRunnable(surfaceHolder, frag)
        )
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