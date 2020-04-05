package fitparser

import com.garmin.fit.Decode
import com.garmin.fit.MesgListener
import de.saring.exerciseviewer.core.EVException
import de.saring.exerciseviewer.data.EVExercise
import de.saring.exerciseviewer.parser.impl.garminfit.FitMessageListener
import java.io.IOException
import java.io.InputStream

fun parseExercise(fitFileInputStream: InputStream): EVExercise {
    val mesgListener = FitMessageListener()
    readFitFile(fitFileInputStream, mesgListener)
    return mesgListener.getExercise()
}

private fun readFitFile(fitFileInputStream: InputStream, mesgListener: MesgListener) {

    try {
        fitFileInputStream.use { fis -> Decode().read(fis, mesgListener) }
    } catch (ioe: IOException) {
        throw EVException("Failed to read FIT file...", ioe)
    }
}
