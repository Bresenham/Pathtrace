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