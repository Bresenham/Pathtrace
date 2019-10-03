package app.android.pathtrace.pathtracer

import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin
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

    fun times(p2 : Point3D) : Point3D {
        return Point3D(x * p2.x , y * p2.y, z * p2.z)
    }

    fun normalize() : Point3D {
        val length = this.length()
        return Point3D(x / length, y / length, z / length)
    }

    fun length() : Double {
        return sqrt(x * x + y * y + z * z)
    }

    companion object {
        fun randomHemisphereDirection() : Point3D {
            /*
            val u1 = Math.random()
            val u2 = Math.random()
            val z = 1.0 - 2.0 * u1
            val r = sqrt(max(0.0, 1.0 - z * z))
            val phi = 2.0 * Math.PI * u2
            val x = r * cos(phi)
            val y = r * sin(phi)
            return Point3D(x, y, z).normalize()
            */

            lateinit var p : Point3D

            do{
                p = Point3D(Math.random(), Math.random(), Math.random()).sub(Point3D(1.0, 1.0, 1.0)).times(2.0)
            } while (p.length() >= 1.0);

            return p
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