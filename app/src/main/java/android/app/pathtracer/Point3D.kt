package android.app.pathtracer

import kotlin.math.sqrt

class Point3D(private val x : Double, private val y : Double, private val z : Double) {

    fun add(p2 : Point3D) : Point3D {
        return Point3D(x + p2.x, y + p2.y, z + p2.z)
    }

    fun sub(p2 : Point3D) : Point3D {
        return Point3D(x - p2.x, y - p2.y, z - p2.z)
    }

    fun times(t : Double) : Point3D {
        return Point3D(x * t, y * t, z * t)
    }

    fun normalize() : Point3D {
        val length = sqrt(x * x + y * y + z * z)
        return Point3D(x / length, y / length, z / length)
    }

    fun isEqualTo(p2 : Point3D) : Boolean {
        return x == p2.x && y == p2.y && z == p2.z
    }

    fun distanceTo(p2 : Point3D) : Double {
        val nP = this.sub(p2)
        return sqrt(nP.x * nP.x + nP.y * nP.y + nP.z * nP.z)
    }

    companion object {
        fun randomHemisphereDirection() : Point3D {
            var x : Double
            var y : Double
            var z : Double
            var d : Double
            do {
                x = -1.0 + Math.random()
                y = (-1.0 + Math.random())
                z = -1.0 + Math.random()
                d = sqrt(x * x + y * y + z * z)
            }  while(d > 1)

            return Point3D(x / d, y/ d, z / d).normalize()
        }
    }

    fun cross(p2 : Point3D) : Point3D {
        return Point3D(
            y * p2.z - z * p2.y,
            z * p2.x - x * p2.z,
            x * p2.y - y * p2.x
        )
    }

    fun dot(p2 : Point3D) : Double {
        return x * p2.x + y * p2.y + z  * p2.z
    }
}