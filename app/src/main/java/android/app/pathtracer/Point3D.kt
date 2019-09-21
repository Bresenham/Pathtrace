package android.app.pathtracer

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

            /*

                float u1 = (float)rand() / (float)RAND_MAX;
                float u2 = (float)rand() / (float)RAND_MAX;
                float z = 1.0f - 2.0f * u1;
                float r = sqrtf(fmaxf(0.0, 1.0f - z * z));
                float phi = 2.0f * (float)M_PI * u2;
                float x = r * cosf(phi);
                float y = r * sinf(phi);
                return norm((struct Point) {.x = x, .y = y, .z = z});

             */

            val u1 = Math.random()
            val u2 = Math.random()
            val z = 1.0 - 2.0 * u1
            val r = sqrt(max(0.0, 1.0 - z * z))
            val phi = 2.0 * Math.PI * u2
            val x = r * cos(phi)
            val y = r * sin(phi)
            return Point3D(x, y, z).normalize()
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