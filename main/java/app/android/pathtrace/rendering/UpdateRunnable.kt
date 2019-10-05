package app.android.pathtrace.rendering

import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.SurfaceHolder
import app.android.pathtrace.pathtracer.RenderFragment

class UpdateRunnable(private val srfcHldr : SurfaceHolder, private val frag : RenderFragment) : Runnable {

    @ExperimentalUnsignedTypes
    override fun run() {

        val ctx = srfcHldr.lockCanvas(Rect(frag.fromX, frag.fromY, frag.fromX + frag.xLength, frag.fromY + frag.yLength))

        Log.d("Task", "Rendering Tile ${frag.id}")

        for (x in frag.fromX until frag.fromX + frag.xLength) {
            for (y in frag.fromY until frag.fromY + frag.yLength) {
                val cols = frag.pixelData[x - frag.fromX][y - frag.fromY]

                var r = 0U
                var g = 0U
                var b = 0U

                for(i in cols.indices) {
                    r += cols[i].r.toUInt()
                    g += cols[i].g.toUInt()
                    b += cols[i].b.toUInt()
                }

                r = r.div(cols.size.toUInt())
                g = g.div(cols.size.toUInt())
                b = b.div(cols.size.toUInt())

                ctx.drawPoint(
                    x.toFloat(),
                    y.toFloat(),
                    Paint().apply{
                        setARGB(255, r.toInt(), g.toInt(), b.toInt())
                    }
                )
            }
        }

        srfcHldr.unlockCanvasAndPost(ctx)
    }
}