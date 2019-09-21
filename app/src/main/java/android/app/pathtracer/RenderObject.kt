package android.app.pathtracer

interface RenderObject {
    companion object {
        const val EPSILON = 10e-6
    }

    fun intersect(r : Ray) : Point3D
}