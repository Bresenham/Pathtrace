package app.android.pathtrace.pathtracer

import java.util.*

class RenderFragment(val id : Int, val fromX : Int, val xLength : Int, val fromY : Int, val yLength : Int) {

    @ExperimentalUnsignedTypes
    val pixelData : Array<Array<LinkedList<Col>>> = Array(xLength) {
        Array(yLength){ LinkedList<Col>() }
    }
}