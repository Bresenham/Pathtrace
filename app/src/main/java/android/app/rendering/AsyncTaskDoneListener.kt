package android.app.rendering

interface AsyncTaskDoneListener {
    fun pushChanges(data : Pair<Pair<Int, Int>, Array<Array<Col>>>)
}