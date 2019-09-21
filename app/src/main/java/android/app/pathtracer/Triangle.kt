package android.app.pathtracer

class Triangle(private val v0 : Point3D, private val v1 : Point3D, private val v2 : Point3D, col : Col, isLight : Boolean) : RenderObject(col, isLight) {

    override fun intersect(r: Ray): Double {
        val edge1 = v1.sub(v0)
        val edge2 = v2.sub(v0)

        val h = r.d.cross(edge2)
        val a = edge1.dot(h)
        if(a > -EPSILON && a < EPSILON) {
            return -1.0
        }

        val f = 1.0 / a
        val s = r.o.sub(v0)
        val u = f * (s.dot(h))
        if(u < 0.0 || u > 1.0) {
            return -1.0
        }

        val q = s.cross(edge1)
        val v = f * r.d.dot(q)

        if(v < 0.0 || u + v > 1.0) {
            return -1.0
        }

        val t = f * edge2.dot(q)

        return when(t > EPSILON) {
            true -> t
            false -> -1.0
        }
    }
}