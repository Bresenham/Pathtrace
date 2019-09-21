package android.app.pathtracer

class RenderFragment(val fromX : Int, val xLength : Int, val fromY : Int, val yLength : Int) {

    val pixelData : Array<Array<Col>> = Array(xLength) {
        Array(yLength){ Col(0, 0, 0) }
    }
}