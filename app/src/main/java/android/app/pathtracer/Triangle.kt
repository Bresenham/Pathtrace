package android.app.pathtracer

import android.app.pathtracer.RenderObject.Companion.EPSILON

class Triangle(private val v0 : Point3D, private val v1 : Point3D, private val v2 : Point3D, val color : Col, val isLight : Boolean) : RenderObject {

    override fun intersect(r: Ray): Point3D {
        val edge1 = v1.sub(v0)
        val edge2 = v2.sub(v0)

        val h = r.d.cross(edge2)
        val a = edge1.dot(h)
        if(a > -EPSILON && a < EPSILON) {
            return Point3D(-1.0, -1.0, -1.0)
        }

        val f = 1.0 / a
        val s = r.o.sub(v0)
        val u = f * (s.dot(h))
        if(u < 0.0 || u > 1.0) {
            return Point3D(-1.0, -1.0, -1.0)
        }

        val q = s.cross(edge1)
        val v = f * r.d.dot(q)

        if(v < 0.0 || u + v > 1.0) {
            return Point3D(-1.0, -1.0, -1.0)
        }

        val t = f * edge2.dot(q)

        return when(t > EPSILON) {
            true -> r.o.add(r.d.times(t))
            false -> Point3D(-1.0, -1.0, -1.0)
        }
    }
}