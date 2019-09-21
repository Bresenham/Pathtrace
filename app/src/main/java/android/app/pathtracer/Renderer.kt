package android.app.pathtracer

import kotlin.math.tan

class Renderer(private val s : Scene, private val width : Int, private val height : Int, private val traceDepth : Int) {


    private fun pixelToWorldCoordinates(x : Int, y : Int) : Ray {
        val fov = 160.0 * Math.PI / 180.0
        val zDir = 1.0 / tan(fov)
        val aspect = width.toDouble() / height.toDouble()

        val xH = x + 0.5
        val yH = y + 0.5

        val xDir = (xH / width.toDouble()) * 2.0 - 1.0
        val yDir = ((yH / height.toDouble()) * 2.0 - 1.0) * aspect


        return Ray(Point3D(0.0, 0.25, 50.25), Point3D(xDir, yDir, zDir))
    }

    private fun trace(r : Ray, currentTraceDepth : Int) : Col {
        if(currentTraceDepth == traceDepth) {
            return Col(0, 0, 0)
        }

        var hitDistance = 1e20
        var hitObject : RenderObject? = null
        s.objects.forEach { obj ->

            val localIntersectionPoint = obj.intersect(r)

            if(!localIntersectionPoint.isEqualTo(Point3D(-1.0, -1.0, -1.0))) {
                val distance = localIntersectionPoint.distanceTo(r.o)
                if(distance < hitDistance) {
                    hitDistance = distance
                    hitObject = obj
                }
            }

        }

        return when(hitObject != null) {
            true -> when(hitObject!!.isLight) {
                true -> hitObject!!.col
                false -> {
                    val hitPoint = r.o.add(r.d.times(hitDistance))
                    val returnColor = trace(Ray(hitPoint, Point3D.randomHemisphereDirection()), currentTraceDepth + 1)
                    hitObject!!.col.mult(returnColor).div(255.0)
                }
            }
            false -> Col(0, 0, 0)
        }
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