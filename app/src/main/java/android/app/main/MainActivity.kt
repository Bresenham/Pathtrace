package android.app.main

import android.app.pathtracer.*
import android.app.rendering.AsyncTaskDoneListener
import android.app.rendering.RenderAsyncTask
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.pathtrace.R

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.Stack

class MainActivity : AppCompatActivity(), AsyncTaskDoneListener {

    private var completeArray = Stack<RenderFragment>()
    private val threads = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun pushChanges(data: RenderFragment) {
        completeArray.push(data)
        if(completeArray.size == threads) {
            renderScreen.pushChanges(completeArray)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        renderScreen.viewTreeObserver.addOnGlobalLayoutListener {
            val heightSize = renderScreen.height / threads

            val renderer = Renderer(
                Scene(
                    Sphere(
                        Point3D(-9.0, 1.0, 1.0),
                        3.0,
                        Col(255,0,0),
                        false
                    ),
                    Sphere(
                        Point3D(3.0, 1.0, 1.0),
                        3.0,
                        Col(0,0,255),
                        false
                    ),
                    Triangle(
                        Point3D(-2.0, 7.0, 0.0),
                        Point3D(3.0, 5.0, 1.0),
                        Point3D(0.0, 3.0, -1.0),
                        Col(0, 255, 0),
                        false
                    ),
                    Sphere(
                        Point3D(-3.0, 1.0, 1.0),
                        3.0,
                        Col(255,255,255),
                        true
                    )
                ), renderScreen.measuredWidth, renderScreen.height, 4)

            for(i in 0 until threads) {
                RenderAsyncTask(this, renderer).execute(0, renderScreen.width, heightSize * i, heightSize)
            }
        }
    }
}
