package app.android.pathtrace.pathtracer

import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin
import kotlin.math.sqrt

class Point3D(private val x : Float, private val y : Float, private val z : Float) {

    fun add(p2 : Point3D) : Point3D {
        return Point3D(x + p2.x, y + p2.y, z + p2.z)
    }

    fun sub(p2 : Point3D) : Point3D {
        return Point3D(x - p2.x, y - p2.y, z - p2.z)
    }

    fun times(t : Float) : Point3D {
        return Point3D(x * t, y * t, z * t)
    }

    fun times(p2 : Point3D) : Point3D {
        return Point3D(x * p2.x , y * p2.y, z * p2.z)
    }

    fun normalize() : Point3D {
        val length = this.length()
        return Point3D(x / length, y / length, z / length)
    }

    fun length() : Float {
        return sqrt(x * x + y * y + z * z)
    }

    companion object {
        fun randomHemisphereDirection() : Point3D {

            lateinit var p : Point3D

            do{
                p = Point3D(Math.random().toFloat(), Math.random().toFloat(), Math.random().toFloat()).sub(Point3D(1.0f, 1.0f, 1.0f)).times(2.0f)
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

    fun dot(p2 : Point3D) : Float {
        return x * p2.x + y * p2.y + z  * p2.z
    }
}