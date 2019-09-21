package android.app.pathtracer

class Col(val r : Int, val g : Int, val b : Int) {

    fun add(c2 : Col) : Col {
        return Col(r + c2.r, g + c2.g, b + c2.b)
    }

    fun mult(c2 : Col) : Col {
        return Col(r * c2.r, g * c2.g, b * c2.b)
    }

    fun div(f : Double) : Col {
        return Col((r / f).toInt(), (g / f).toInt(), (b / f).toInt())
    }
}