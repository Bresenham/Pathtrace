package app.android.pathtrace.pathtracer

class Triangle(private val v0 : Point3D, private val v1 : Point3D, private val v2 : Point3D, col : Col, isLight : Boolean) : RenderObject(col, isLight) {

    constructor(v0 : Point3D, v1 : Point3D, v2 : Point3D, col : Col, isLight : Boolean, normal : Point3D) : this(v0, v1, v2, col, isLight) {
        super.setNormal(normal)
    }

    override fun intersect(r: Ray): Float {
        val a = v1.sub(v0).dot(r.d.cross(v2.sub(v0)))
        if(a > -EPSILON && a < EPSILON) {
            return -1.0f
        }

        val f = 1.0 / a
        val u = f * (r.o.sub(v0).dot(r.d.cross(v2.sub(v0))))
        if(u < 0.0 || u > 1.0) {
            return -1.0f
        }

        val q = r.o.sub(v0).cross(v1.sub(v0))
        val v = f * r.d.dot(q)

        if(v < 0.0 || u + v > 1.0) {
            return -1.0f
        }

        val t = f * v2.sub(v0).dot(q)

        return when(t > EPSILON) {
            true -> t.toFloat()
            false -> -1.0f
        }
    }
}