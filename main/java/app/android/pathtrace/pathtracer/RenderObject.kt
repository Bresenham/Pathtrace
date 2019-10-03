package app.android.pathtrace.pathtracer

abstract class RenderObject(var col : Col, var isLight : Boolean) {

    private lateinit var normal : Point3D

    companion object {
        const val EPSILON = 10e-6
    }

    protected fun setNormal(n : Point3D) {
        this.normal = n
    }

    fun getNormal() : Point3D {
        return this.normal
    }

    abstract fun intersect(r : Ray) : Double
}