package bose.ankush.weatherify.util

import bose.ankush.weatherify.model.model.AvgForecast
import bose.ankush.weatherify.model.model.WeatherForecast
import com.bosankus.utilities.DateTimeUtilsImpl
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

/**Created by
Author: Ankush Bose
Date: 06,May,2021
 **/

object Extension {

    fun Double.toCelsius(): String = (this - KELVIN_CONSTANT).roundToInt().toString()

    fun List<WeatherForecast.ForecastList>.getForecastListForNext4Days():
            List<AvgForecast> {
        return filter { list -> (list.dt?.isNotMatchingWithTodayAndWithinNext4Days() == true) }
            .parseEachDayFromList()
    }

    private fun List<WeatherForecast.ForecastList>.parseEachDayFromList(): List<AvgForecast> {
        val listOfAvgForecast = ArrayList<AvgForecast>()
        var avgTemp: Int
        var dayName: String
        for (i in 1..4 step 1) {
            var totalTemp = 0
            var counter = 0
            for (j in this.indices step 1) {
                val date = this[j].dt
                if (date?.let { DateTimeUtilsImpl.getDayWiseDifferenceFromToday(it) } == i) {
                    val forecastObj = this[j]
                    totalTemp += forecastObj.main?.temp?.toCelsius()?.toInt()!!
                    counter++
                    if ((counter % 7) == 0) {
                        avgTemp = totalTemp / counter
                        dayName = DateTimeUtilsImpl.getDayNameFromEpoch(date)
                        val avgForecast = AvgForecast(this.hashCode(), dayName, "$avgTemp C")
                        listOfAvgForecast.add(avgForecast)
                    }
                }
            }
        }
        return listOfAvgForecast
    }


    private fun Int.isNotMatchingWithTodayAndWithinNext4Days(): Boolean {
        val givenDate = Date(this.toLong() * 1000)
        val givenDateCalender = Calendar.getInstance()
        givenDateCalender.time = givenDate
        val givenYear = givenDateCalender.get(Calendar.YEAR)
        val currentYear = DateTimeUtilsImpl.getTodayDateInCalenderFormat().get(Calendar.YEAR)
        val givenDateNumber = givenDateCalender.get(Calendar.DAY_OF_MONTH + 1)
        val todayDateNumber =
            DateTimeUtilsImpl.getTodayDateInCalenderFormat().get(Calendar.DAY_OF_MONTH + 1)
        val differenceOfDate = DateTimeUtilsImpl.getDayWiseDifferenceFromToday(this)
        return (givenDateNumber > todayDateNumber && givenYear == currentYear && (differenceOfDate <= 4))
    }

}
