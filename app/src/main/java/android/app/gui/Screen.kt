package android.app.gui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Screen : View {

    constructor(context: Context, attr: AttributeSet) : super(context, attr)


    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(  (canvas.width / 2).toFloat(),
                            (canvas.height / 2).toFloat(),
                            (Math.min(canvas.width / 2, canvas.height) / 2).toFloat(),
                            Paint().apply{
                                setARGB(255, 255, 0, 0)
                            }  )
    }
}