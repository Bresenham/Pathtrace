package android.app.pathtracer

class RenderFragment(public val id : Int, val fromX : Int, val xLength : Int, val fromY : Int, val yLength : Int) {

    val pixelData : Array<Array<Col>> = Array(xLength) {
        Array(yLength){ Col(0, 0, 0) }
    }
}