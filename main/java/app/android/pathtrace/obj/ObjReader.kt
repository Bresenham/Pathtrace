package app.android.pathtrace.obj

import app.android.pathtrace.pathtracer.*

class ObjReader {

    companion object {
        @ExperimentalUnsignedTypes
        fun parseObj(s : String) : Array<Mesh> {

            val meshes = ArrayList<Mesh>()
            val triangles = ArrayList<RenderObject>()
            val normals = ArrayList<Point3D>()
            val vertices = ArrayList<Point3D>()
            var currentObjName = ""

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
                    val vn = vtx[1].split('/')[2].toInt() - 1
                    triangles.add(Triangle(vertices[v1], vertices[v2], vertices[v3], Col(255.toUByte(), 255.toUByte(), 255.toUByte()), false, normals[vn]))
                } else if(line[0] == 'v' && line[1] == 'n') { /* Normal Definition */
                    val points = line.split(line[2])
                    val x = points[1].toDouble()
                    val y = points[2].toDouble()
                    val z = points[3].toDouble()
                    normals.add(Point3D(x, y, z))
                } else if(line[0] == 'o') { /* Object Definition */
                    if(triangles.isNotEmpty()) {
                        meshes.add(Mesh(currentObjName, *triangles.toTypedArray()))

                        triangles.clear()
                    }
                    currentObjName = line.split(line[1])[1]
                }
            }

            meshes.add(Mesh(currentObjName, *triangles.toTypedArray()))

            return meshes.toTypedArray()
        }
    }
}