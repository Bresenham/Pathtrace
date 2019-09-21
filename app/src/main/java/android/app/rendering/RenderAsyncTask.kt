package android.app.rendering

import android.os.AsyncTask


class RenderAsyncTask(private val listener : AsyncTaskDoneListener) : AsyncTask<Int, Void, Pair<Pair<Int, Int>, Array<Array<Col>>>>() {

    var fromX : Int = 0
    var xLength: Int = 0

    var fromY: Int = 0
    var yLength: Int = 0

    override fun doInBackground(vararg p0: Int?): Pair<Pair<Int, Int>, Array<Array<Col>>> {
        fromX = p0[0]!!
        xLength = p0[1]!!

        fromY = p0[2]!!
        yLength = p0[3]!!

        val returnArray = Pair(Pair(fromX, fromY), Array(xLength) {Array(yLength) {Col(0, 0, 0)} })

        for(x in fromX until fromX + xLength) {
            for(y in fromY until fromY + yLength) {
                returnArray.second[x - fromX][y - fromY] = Col(0, 255, 0)
            }
        }

        return returnArray
    }

    override fun onPostExecute(result: Pair<Pair<Int, Int>, Array<Array<Col>>>) {
        listener.pushChanges(result)
    }
}