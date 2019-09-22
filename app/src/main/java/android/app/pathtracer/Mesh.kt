package android.app.pathtracer

class Mesh(val name : String, vararg val renderObjs : RenderObject) {

    fun setAsLight() {
        renderObjs.forEach { obj -> obj.isLight = true }
    }
}