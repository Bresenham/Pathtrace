package app.android.pathtrace.pathtracer

import kotlin.math.tan

class Renderer {

    companion object {

        @ExperimentalUnsignedTypes
        fun renderOneStep(s : Scene, width: Int, height : Int, traceDepth: Int, frag : RenderFragment) {
            for(x in frag.fromX until frag.fromX + frag.xLength) {
                for(y in frag.fromY until frag.fromY + frag.yLength) {
                    val r = pixelToWorldCoordinates(height, width, x, y)
                    val col = trace(s, r, traceDepth, 1)
                    frag.pixelData[x - frag.fromX][y - frag.fromY].add(col)
                }
            }
        }

        private fun pixelToWorldCoordinates(height : Int, width : Int, x : Int, y : Int) : Ray {
            val fov = 160.0 * Math.PI / 180.0
            val zDir = 1.0 / tan(fov)
            val aspect = height.toDouble() / width.toDouble()

            val xH = x + 0.5
            val yH = y + 0.5

            val xDir = (xH / width.toDouble()) * 2.0 - 1.0
            val yDir = ((yH / height.toDouble()) * 2.0 - 1.0) * aspect

            return Ray(Point3D(10.0f, 0.25f, 15.25f), Point3D(xDir.toFloat(), yDir.toFloat(), zDir.toFloat()))
        }

        @ExperimentalUnsignedTypes
        private fun trace(s : Scene, r : Ray, maxDepth : Int, currentTraceDepth : Int) : Col {
            if(currentTraceDepth == maxDepth) {
                return Col(0.toUByte(), 0.toUByte(), 0.toUByte())
            }

            var hitDistance = 1e20f
            var hitObject : RenderObject? = null

            for(m in s.objects.indices) {
                val mesh = s.objects[m]
                for(o in mesh.renderObjs.indices) {
                    val obj = mesh.renderObjs[o]
                    val distance = obj.intersect(r)
                    if (distance != -1.0f && distance < hitDistance) {
                        hitDistance = distance
                        hitObject = obj
                    }
                }
            }

            return when(hitObject != null) {
                true -> when(hitObject.isLight) {
                    true -> hitObject.col
                    false -> {
                        val hitPoint = r.o.add(r.d.times(hitDistance))
                        val returnColor = trace(s, Ray(hitPoint, hitPoint.add(hitObject.getNormal()).add(Point3D.randomHemisphereDirection()).sub(hitPoint).normalize()), maxDepth, currentTraceDepth + 1)
                        hitObject.col.mult(returnColor)
                    }
                }
                false -> Col(0.toUByte(), 0.toUByte(), 0.toUByte())
            }
        }

    }
}