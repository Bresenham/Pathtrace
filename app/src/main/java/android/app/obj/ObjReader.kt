package android.app.obj

import android.app.pathtracer.Col
import android.app.pathtracer.Point3D
import android.app.pathtracer.RenderObject
import android.app.pathtracer.Triangle

class ObjReader {

    companion object {
        fun parseObj(s : String) : Array<RenderObject> {

            var triangles = ArrayList<RenderObject>()
            var vertices = ArrayList<Point3D>()

            s.split("\n").forEach {line ->
                /* Vertex Definition */
                if(line[0] == 'v' && (line[1] == ' ' || line[1] == '\t')) {
                    val points = line.split(line[1])
                    val x = points[1].toDouble()
                    val y = points[2].toDouble()
                    val z = points[3].toDouble()
                    vertices.add(Point3D(x, y, z))
                } else if(line[0] == 'f') { /* Triangle Definition */
                    val vtx = line.split(line[1])
                    val v1 = vtx[1].split('/')[0].toInt() - 1
                    val v2 = vtx[2].split('/')[0].toInt() - 1
                    val v3 = vtx[3].split('/')[0].toInt() - 1
                    triangles.add(Triangle(vertices[v1], vertices[v2], vertices[v3], Col(255, 255, 255), false))
                }
            }

            return triangles.toTypedArray()
        }
    }
}