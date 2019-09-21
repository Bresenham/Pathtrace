package android.app.pathtracer

import kotlin.math.tan

class Renderer(private val s : Scene, private val width : Int, private val height : Int, private val traceDepth : Int) {


    private fun pixelToWorldCoordinates(x : Int, y : Int) : Ray {
        val aspectRatio = width / (height.toDouble())
        val fov = 135

        val pX = (2.0 * ((x + 0.5) / width) - 1.0) * tan(fov / 2.0 * Math.PI / 180.0) * aspectRatio
        val pY = (1.0 - 2.0 * ((y + 0.5) / height) * tan(fov / 2.0 * Math.PI / 180.0))
        val d = Point3D(pX, pY, -1.0).normalize()
        return Ray(Point3D(0.0, 0.0, 0.0), d)
    }

    private fun trace(r : Ray, currentTraceDepth : Int) : Col {
        return Col(0, 255, 0)
    }

    fun renderOneStep(startX : Int, xLength : Int, startY : Int, yLength : Int, fillColorArray : Array<Array<Col>>) {
        for(x in startX until startX + xLength) {
            for(y in startY until startY + yLength) {
                val r = pixelToWorldCoordinates(x, y)
                val col = trace(r, 1)
                fillColorArray[x - startX][y - startY] = col
            }
        }
    }
}