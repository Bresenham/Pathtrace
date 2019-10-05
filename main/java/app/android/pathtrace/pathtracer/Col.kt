package app.android.pathtrace.pathtracer

import kotlin.math.min

class Col @ExperimentalUnsignedTypes constructor(val r : UByte, val g : UByte, val b : UByte) {

    @ExperimentalUnsignedTypes
    fun add(c2 : Col) : Col {
        return if(this.isBlack()) {
            c2
        } else {
            val nR = (r + c2.r).toInt().div(2).toUByte()
            val nG = (r + c2.r).toInt().div(2).toUByte()
            val nB = (r + c2.r).toInt().div(2).toUByte()

            Col(nR, nG, nB)
        }
    }

    @ExperimentalUnsignedTypes
    fun mult(c2 : Col) : Col {
        val nR = (r * c2.r).toInt().div(255).toUByte()
        val nG = (g * c2.g).toInt().div(255).toUByte()
        val nB = (b * c2.b).toInt().div(255).toUByte()
        return Col(nR, nG, nB)
    }

    @ExperimentalUnsignedTypes
    fun div(f : UByte) : Col {
        return Col((r / f).toUByte(), (g / f).toUByte(), (b / f).toUByte())
    }

    @ExperimentalUnsignedTypes
    fun isBlack() : Boolean {
        return r == 0.toUByte() && g == 0.toUByte() && b == 0.toUByte()
    }
}