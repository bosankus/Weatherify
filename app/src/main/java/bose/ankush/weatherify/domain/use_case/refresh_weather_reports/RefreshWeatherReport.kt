package bose.ankush.weatherify.domain.use_case.refresh_weather_reports

import bose.ankush.weatherify.domain.repository.WeatherRepository
import javax.inject.Inject

class RefreshWeatherReport @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(coordinates: Pair<Double, Double>) {
        repository.refreshWeatherData(coordinates)
    }
}