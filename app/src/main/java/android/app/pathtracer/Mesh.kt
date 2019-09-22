package android.app.pathtracer

class Mesh(val name : String, vararg val renderObjs : RenderObject) {

    fun setAsLight() : Mesh {
        renderObjs.forEach { obj -> obj.isLight = true }
        return this
    }

    fun setColor(col : Col) : Mesh {
        renderObjs.forEach { obj -> obj.col = col }
        return this
    }
}