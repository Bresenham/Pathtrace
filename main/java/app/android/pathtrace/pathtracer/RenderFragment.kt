package app.android.pathtrace.pathtracer

class RenderFragment(val id : Int, val fromX : Int, val xLength : Int, val fromY : Int, val yLength : Int) {

    @ExperimentalUnsignedTypes
    val pixelData : Array<Array<Col>> = Array(xLength) {
        Array(yLength){ Col(0.toUByte(), 0.toUByte(), 0.toUByte()) }
    }
}