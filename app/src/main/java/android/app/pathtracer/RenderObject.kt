package android.app.pathtracer

abstract class RenderObject(val col : Col, val isLight : Boolean) {
    companion object {
        const val EPSILON = 10e-6
    }

    abstract fun intersect(r : Ray) : Point3D
}