package android.app.pathtracer

import android.util.Log
import kotlin.math.abs
import kotlin.math.tan

class Renderer(private val s : Scene, private val width : Int, private val height : Int, private val traceDepth : Int) {

    private fun pixelToWorldCoordinates(x : Int, y : Int) : Ray {
        val fov = 160.0 * Math.PI / 180.0
        val zDir = 1.0 / tan(fov)
        val aspect = height.toDouble() / width.toDouble()

        val xH = x + 0.5
        val yH = y + 0.5

        val xDir = (xH / width.toDouble()) * 2.0 - 1.0
        val yDir = ((yH / height.toDouble()) * 2.0 - 1.0) * aspect

        return Ray(Point3D(10.0, 0.25, 15.25), Point3D(xDir, yDir, zDir))
    }

    private fun trace(r : Ray, currentTraceDepth : Int) : Col {
        if(currentTraceDepth == traceDepth) {
            return Col(0, 0, 0)
        }

        var hitDistance = 1e20
        var hitObject : RenderObject? = null

        s.objects.forEach { mesh ->
            mesh.renderObjs.forEach { obj ->
                val distance = obj.intersect(r)
                if (distance != -1.0 && distance < hitDistance) {
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
                    val rndPoint = Point3D.randomHemisphereDirection()
                    val target = hitPoint.add(hitObject!!.getNormal()).add(rndPoint)
                    val returnColor = trace(Ray(hitPoint, target.sub(hitPoint).normalize()), currentTraceDepth + 1).div(2.0)
                    hitObject!!.col.mult(returnColor).div(255.0)
                }
            }
            false -> Col(0, 0, 0)
        }
    }

    fun renderOneStep(frag : RenderFragment) {
        var mean = 0
        val length = frag.xLength * frag.yLength
        for(x in frag.fromX until frag.fromX + frag.xLength) {
            for(y in frag.fromY until frag.fromY + frag.yLength) {
                val r = pixelToWorldCoordinates(x, y)
                val col = trace(r, 1)
                frag.pixelData[x - frag.fromX][y - frag.fromY] = frag.pixelData[x - frag.fromX][y - frag.fromY].add(col)
                mean += col.r
            }
        }

        if(mean != 0) {
            mean /= length
            var varianceSum = 0
            for(x in 0 until frag.xLength) {
                for(y in 0 until frag.yLength) {
                    varianceSum += (frag.pixelData[x][y].r - mean) * (frag.pixelData[x][y].r - mean)
                }
            }
            frag.variance = abs(varianceSum / length / 10)
        } else {
            frag.variance = 0
        }

        Log.d("Priority", "Fragment $frag.id received variance of ${frag.variance}")
    }
}