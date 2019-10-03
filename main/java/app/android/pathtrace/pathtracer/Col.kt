package app.android.pathtrace.pathtracer

import kotlin.math.min

class Col @ExperimentalUnsignedTypes constructor(val r : UByte, val g : UByte, val b : UByte) {

    @ExperimentalUnsignedTypes
    fun add(c2 : Col) : Col {
        val nR = min((r + c2.r).toInt(), 255).toUByte()
        val nG = min((g + c2.g).toInt(), 255).toUByte()
        val nB = min((b + c2.b).toInt(), 255).toUByte()
        return Col(nR, nG, nB)
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
}