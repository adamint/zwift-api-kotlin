package de.saring.exerciseviewer.data

/**
 * This class stores the altitude summary of a recorded exercise.
 *
 * @property altitudeMin Minimum altitude of exercise.
 * @property altitudeAvg Average altitude of exercise.
 * @property altitudeMax Maximum altitude of exercise.
 * @property ascent Ascent of exercise (climbed height meters).
 * @property descent Descent data of exercise (height meters).
 *
 * @author Stefan Saring
 */
data class ExerciseAltitude(

    var altitudeMin: Short,
    var altitudeAvg: Short,
    var altitudeMax: Short,
    var ascent: Int,
    var descent: Int)

/**
 * This class stores the cadence summary of a recorded exercise.
 *
 * @property cadenceAvg Average cadence of exercise (rpm)
 * @property cadenceMax Maximum cadence of exercise (rpm)
 * @property cadenceTotal Total cycles of exercise recorded (optional).
 *
 * @author Stefan Saring
 */
data class ExerciseCadence(

    var cadenceAvg: Short,
    var cadenceMax: Short,
    var cyclesTotal: Long? = null)

/**
 * This data class contains all the information recorded at each interval. All the attributes are optional,
 * it depends on the file type (or heartrate monitor model) whether the data is available or not.
 *
 * @property timestamp Timestamp since exercise start of this sample (in 1/1000 sec).
 * @property heartRate Heartrate at record moment.
 * @property altitude Altitude at record moment.
 * @property speed Speed at record moment (in km/h).
 * @property cadence Cadence at record moment (in rpm).
 * @property distance Distance at record moment (in meters).
 * @property temperature Temperature at record moment (in degrees celcius, optional).
 * @property position The geographical location of this sample in the exercise track (optional).
 *
 * @author Stefan Saring
 */
data class ExerciseSample(

    var timestamp: Long? = null,
    var heartRate: Short? = null,
    var altitude: Short? = null,
    var speed: Float? = null,
    var cadence: Short? = null,
    var distance: Int? = null,
    var temperature: Short? = null,
    var position: Position? = null)

/**
 * This class stores the speed summary of a recorded exercise.
 *
 * @property speedAvg Average speed of exercise (in km/h).
 * @property speedMax Maximum speed of exercise (in km/h).
 * @property distance Distance of exercise (in meters).
 *
 * @author Stefan Saring
 */
data class ExerciseSpeed(

    var speedAvg: Float,
    var speedMax: Float,
    var distance: Int)

/**
 * This class stores the temperature summary of a recorded exercise.
 *
 * @property temperatureMin Minimum temperature of an exercise (in celsius degrees).
 * @property temperatureAvg Average temperature of an exercise (in celsius degrees).
 * @property temperatureMax Maximum temperature of an exercise (in celsius degrees).
 *
 * @author Stefan Saring
 */
data class ExerciseTemperature(

    var temperatureMin: Short,
    var temperatureAvg: Short,
    var temperatureMax: Short)

/**
 * This class contains the heartrate limit data of a recorded exercise. It consists of the limit range
 * and the times below, within and above.
 *
 * @property lowerHeartRate Lower heartrate limit.
 * @property upperHeartRate Upper heartrate limit.
 * @property timeBelow Time in seconds below the limit (can be missing).
 * @property timeWithin Time in seconds within the limit.
 * @property timeAbove Time in seconds above the limit (can be missing).
 * @property isAbsoluteRange Flag is true when the range is set by absolute values (default), false for percentual values (e.g. 60-80%).
 *
 * @author Stefan Saring
 */
data class HeartRateLimit(

    var lowerHeartRate: Short,
    var upperHeartRate: Short,
    var timeBelow: Int?,
    var timeWithin: Int,
    var timeAbove: Int?,
    var isAbsoluteRange: Boolean = true)

/**
 * This class contains all data of a lap in an exercise.
 *
 * @property timeSplit Lap split time (in 1/10 seconds).
 * @property heartRateSplit Heartrate at lap split time (if recorded).
 * @property heartRateAVG Average heartrate at lap (if recorded).
 * @property heartRateMax Maximum heartrate at lap (if recorded).
 * @property speed Lap speed data (if recorded).
 * @property altitude Lap altitude data (if recorded).
 * @property temperature Lap temperature (if recorded).
 * @property positionSplit The geographical location at lap split time (if recorded).
 *
 * @author Stefan Saring
 */
data class Lap(

    var timeSplit: Int = 0,
    var heartRateSplit: Short? = null,
    var heartRateAVG: Short? = null,
    var heartRateMax: Short? = null,
    var speed: LapSpeed? = null,
    var altitude: LapAltitude? = null,
    var temperature: LapTemperature? = null,
    var positionSplit: Position? = null)

/**
 * This class contains all altitude data of a lap of an exercise.
 *
 * @property altitude Altitude at lap end.
 * @property ascent Ascent (climbed height meters) of lap.
 * @property descent Descent (height meters) of lap.
 *
 * @author Stefan Saring
 */
data class LapAltitude(

    var altitude: Short,
    var ascent: Int,
    var descent: Int)

/**
 * This class contains all speed data of a lap of an exercise.
 *
 * @property speedEnd Speed at end of the lap (km/h).
 * @property speedAVG Average speed of the lap (km/h).
 * @property distance Distance of the lap (meters) from the beginning of the exercise, not from the beginning of the lap!
 * @property cadence Cadence at the end of the lap (rpm).
 *
 * @author Stefan Saring
 */
data class LapSpeed(

    var speedEnd: Float,
    var speedAVG: Float,
    var distance: Int,
    var cadence: Short? = null)

/**
 * This class contains all temperature data of a lap of an exercise. It's a separate class because it's recorded
 * optionally.
 *
 * @property temperature Temperature at lap (in celsius degrees).
 *
 * @author Stefan Saring
 */
data class LapTemperature(

    var temperature: Short)

/**
 * The Position class defines the geographical location of one specific point of the exercise track
 * (also known as track point).
 *
 * @property latitude Latitude of this trackpoint in degrees.
 * @property longitude Longitude of this trackpoint in degrees.
 *
 * @author Stefan Saring
 */
data class Position(

    val latitude: Double,
    val longitude: Double)

/**
 * This class contains the information about what has been recorded in an exercise.
 *
 * @property isHeartRate Has heart rate been recorded?
 * @property isSpeed Has speed been recorded?
 * @property isAltitude Has altitude been recorded?
 * @property isCadence Has cadence been recorded?
 * @property isPower Has bicycling power been recorded?
 * @property isTemperature Has the temperature been recorded ? (Only in HAC4 devices).
 * @property isLocation Has the location of the trackpoints been recorded? (GPS data)
 * @property isIntervalExercise Is the exercise an interval training (S510 only?)
 * @property bikeNumber Number of bike, when speed has been recorded (Polar S710 supports 2).
 *
 * @author Stefan Saring
 */
data class RecordingMode(

    var isHeartRate: Boolean = false,
    var isSpeed: Boolean = false,
    var isAltitude: Boolean = false,
    var isCadence: Boolean = false,
    var isPower: Boolean = false,
    var isTemperature: Boolean = false,
    var isLocation: Boolean = false,
    var isIntervalExercise: Boolean = false,
    var bikeNumber: Byte? = null)
