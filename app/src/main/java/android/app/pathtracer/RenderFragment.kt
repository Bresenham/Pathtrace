package android.app.pathtracer

class RenderFragment(val id : Int, val fromX : Int, val xLength : Int, val fromY : Int, val yLength : Int) : Comparable<RenderFragment> {

    var variance = Int.MAX_VALUE

    override fun compareTo(other: RenderFragment): Int {
        return other.variance - this.variance
    }

    val pixelData : Array<Array<Col>> = Array(xLength) {
        Array(yLength){ Col(0, 0, 0) }
    }
}