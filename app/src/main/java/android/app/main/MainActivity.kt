package android.app.main

import android.app.obj.ObjReader
import android.app.pathtracer.*
import android.app.rendering.AsyncTaskDoneListener
import android.app.rendering.RenderAsyncTask
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.pathtrace.R

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor

class MainActivity : AppCompatActivity(), AsyncTaskDoneListener {

    private var passes = 1
    private var finishedThreadsCounter = 0
    private val threads = 8
    private val completeArray = mutableListOf<RenderFragment>()
    private lateinit var triangles : Array<RenderObject>
    private val objTest = "v 0.745072 1.349045 -0.790534\n" +
            "v 0.373371 -0.424354 -1.637229\n" +
            "v 1.457932 0.424354 0.833282\n" +
            "v 1.086231 -1.349045 -0.013413\n" +
            "v -1.086231 1.349045 0.013413\n" +
            "v -1.457932 -0.424354 -0.833282\n" +
            "v -0.373371 0.424354 1.637229\n" +
            "v -0.745072 -1.349045 0.790534\n" +
            "v 1.913353 0.795666 -1.113307\n" +
            "v 1.694124 -0.250284 -1.612687\n" +
            "v 2.333797 0.250284 -0.155582\n" +
            "v 2.114568 -0.795666 -0.654962\n" +
            "v -1.913796 0.918612 0.524586\n" +
            "v -2.166901 -0.288958 -0.051958\n" +
            "v -1.428385 0.288958 1.630299\n" +
            "v -1.681490 -0.918612 1.053755\n" +
            "v -0.542280 -0.424354 -1.235255\n" +
            "v 0.709595 1.222619 1.616380\n" +
            "v 0.170579 -1.349045 0.388561\n" +
            "v -0.003264 2.147310 -0.007436\n" +
            "v 0.466262 0.582928 -1.425632\n" +
            "v -0.466262 -0.582928 1.425632\n" +
            "v 1.365040 -0.582928 0.621685\n" +
            "v -1.365040 0.582928 -0.621685\n" +
            "v 1.748912 0.343810 -1.487887\n" +
            "v 2.279010 -0.343810 -0.280382\n" +
            "v -1.491638 -0.396936 1.486215\n" +
            "v -2.103648 0.396936 0.092125\n" +
            "v -0.365732 0.982061 -0.833096\n" +
            "v 0.533046 -0.183796 1.214221\n" +
            "vn -0.4745 0.6590 0.5836\n" +
            "vn 0.2859 -0.2222 0.9321\n" +
            "vn -0.4643 -0.8417 -0.2755\n" +
            "vn -0.1859 -0.8867 -0.4233\n" +
            "vn 0.6276 -0.6194 0.4717\n" +
            "vn -0.1927 0.6669 -0.7198\n" +
            "vn 0.9157 0.0000 -0.4020\n" +
            "vn 0.0776 0.6194 -0.7812\n" +
            "vn 0.1204 -0.8390 -0.5306\n" +
            "vn 0.4721 0.8390 0.2705\n" +
            "vn -0.9157 0.0000 0.4020\n" +
            "vn -0.1115 0.8417 0.5283\n" +
            "vn -0.7063 0.2073 -0.6768\n" +
            "vn 0.0202 -0.2073 0.9781\n" +
            "vn -0.3994 0.6669 -0.6291\n" +
            "vn 0.4926 -0.2222 0.8414\n" +
            "vn 0.7508 0.6590 0.0457\n" +
            "vn 0.2980 -0.6712 0.6788\n" +
            "vn -0.3918 0.2236 -0.8925\n" +
            "vn -0.0668 -0.6223 0.7800\n" +
            "vn -0.6194 0.6223 -0.4787\n" +
            "vn -0.0090 0.2064 -0.9784\n" +
            "vn 0.7142 -0.2064 0.6689\n" +
            "vn 0.2348 -0.2944 0.9264\n" +
            "vn -0.1698 0.6044 -0.7784\n" +
            "vn -0.4580 0.6044 -0.6519\n" +
            "vn 0.5230 -0.2944 0.7999\n" +
            "vn 0.2442 -0.6101 0.7538\n" +
            "vn -0.3098 0.2971 -0.9032\n" +
            "vn -0.4552 0.2971 -0.8393\n" +
            "vn 0.3896 -0.6101 0.6899\n" +
            "f 5/1/1 18/2/1 20/3/1\n" +
            "f 30/4/2 7/5/2 22/6/2\n" +
            "f 8/7/3 14/8/3 6/9/3\n" +
            "f 2/10/4 19/11/4 17/12/4\n" +
            "f 23/13/5 12/14/5 26/15/5\n" +
            "f 29/16/6 1/17/6 21/18/6\n" +
            "f 9/19/7 26/20/7 25/21/7\n" +
            "f 21/18/8 9/22/8 25/23/8\n" +
            "f 2/10/9 12/24/9 4/25/9\n" +
            "f 3/26/10 9/27/10 1/28/10\n" +
            "f 15/29/11 28/30/11 27/31/11\n" +
            "f 5/1/12 15/32/12 7/33/12\n" +
            "f 24/34/13 14/35/13 28/36/13\n" +
            "f 22/6/14 15/29/14 27/31/14\n" +
            "f 5/37/15 29/16/15 24/34/15\n" +
            "f 17/12/4 8/7/4 6/9/4\n" +
            "f 3/38/16 30/4/16 23/13/16\n" +
            "f 20/3/17 3/26/17 1/28/17\n" +
            "f 23/13/18 19/39/18 4/40/18\n" +
            "f 24/34/19 17/41/19 6/42/19\n" +
            "f 22/6/20 16/43/20 8/44/20\n" +
            "f 24/34/21 13/45/21 5/37/21\n" +
            "f 27/31/11 14/8/11 16/43/11\n" +
            "f 21/18/22 10/46/22 2/10/22\n" +
            "f 25/21/7 12/47/7 10/48/7\n" +
            "f 17/41/19 21/18/19 2/10/19\n" +
            "f 23/13/23 11/49/23 3/38/23\n" +
            "f 19/39/18 22/6/18 8/44/18\n" +
            "f 5/1/1 7/33/1 18/2/1\n" +
            "f 30/4/24 18/50/24 7/5/24\n" +
            "f 8/7/3 16/51/3 14/8/3\n" +
            "f 2/10/4 4/25/4 19/11/4\n" +
            "f 23/13/5 4/40/5 12/14/5\n" +
            "f 29/16/25 20/52/25 1/17/25\n" +
            "f 9/19/7 11/53/7 26/20/7\n" +
            "f 21/18/8 1/17/8 9/22/8\n" +
            "f 2/10/9 10/46/9 12/24/9\n" +
            "f 3/26/10 11/54/10 9/27/10\n" +
            "f 15/29/11 13/55/11 28/30/11\n" +
            "f 5/1/12 13/56/12 15/32/12\n" +
            "f 24/34/13 6/42/13 14/35/13\n" +
            "f 22/6/14 7/5/14 15/29/14\n" +
            "f 5/37/26 20/52/26 29/16/26\n" +
            "f 17/12/4 19/11/4 8/7/4\n" +
            "f 3/38/27 18/50/27 30/4/27\n" +
            "f 20/3/17 18/2/17 3/26/17\n" +
            "f 23/13/28 30/4/28 19/39/28\n" +
            "f 24/34/29 29/16/29 17/41/29\n" +
            "f 22/6/20 27/31/20 16/43/20\n" +
            "f 24/34/21 28/36/21 13/45/21\n" +
            "f 27/31/11 28/30/11 14/8/11\n" +
            "f 21/18/22 25/23/22 10/46/22\n" +
            "f 25/21/7 26/20/7 12/47/7\n" +
            "f 17/41/30 29/16/30 21/18/30\n" +
            "f 23/13/23 26/15/23 11/49/23\n" +
            "f 19/39/31 30/4/31 22/6/31"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        triangles = ObjReader.parseObj(objTest)
    }

    override fun notifyFinish() {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val currentDate = sdf.format(Date())
        Log.d("Task", "Task finished @ $currentDate")
        finishedThreadsCounter += 1
        if(finishedThreadsCounter == threads) {
            finishedThreadsCounter = 0
            renderScreen.pushChanges(completeArray)
            passes += 1
            render()
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
            render()
        }
    }

    private fun render() {
        val heightSize = renderScreen.height / threads

        for(i in 0 until threads) {
            lateinit var frag : RenderFragment
            if(completeArray.size != threads) {
                frag = RenderFragment(0, renderScreen.width, heightSize * i, heightSize)
                completeArray.add(frag)
            } else {
                frag = completeArray[i]
            }

            val renderer = Renderer(Scene(*triangles, Sphere(Point3D(6.0, 1.0, 1.0), 3.0, Col(255,255,255), true)), renderScreen.measuredWidth, renderScreen.height, 4)
            val task = RenderAsyncTask(this, renderer)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, frag)
            Log.d("Task", "Started thread $i")
        }
    }
}
