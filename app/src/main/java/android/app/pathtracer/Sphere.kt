package android.app.pathtracer

import kotlin.math.sqrt

class Sphere(private val center : Point3D, private val radius : Double, col : Col, isLight : Boolean) : RenderObject(col, isLight) {

    override fun intersect(r: Ray): Double {

        val oc = r.o.sub(center)

        val a = r.d.dot(r.d)
        val b = 2.0 * oc.dot(r.d)
        val c = oc.dot(oc) - radius * radius

        val discriminant = b * b - 4 * a * c

        return when(discriminant < 0) {
            true -> -1.0
            false -> {
                (-b - sqrt(discriminant)) / (2.0 * a)
            }
        }
    }
}