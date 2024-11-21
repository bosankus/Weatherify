package bose.ankush.weatherify.data.room.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import bose.ankush.weatherify.base.common.AQ_DATABASE_NAME
import bose.ankush.weatherify.base.common.WEATHER_DATABASE_NAME
import bose.ankush.weatherify.domain.model.AirQuality
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Transaction
    fun refreshWeather(weather: WeatherEntity, airQuality: AirQuality) {
        deleteAllWeatherDetails()
        deleteAllAirQualityDetails()
        insertWeather(weather)
        insertAirQuality(airQuality)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAirQuality(airQuality: AirQuality)

    @Query("SELECT * from $WEATHER_DATABASE_NAME")
    fun getWeather(): Flow<WeatherEntity>

    @Query("SELECT * from $AQ_DATABASE_NAME")
    fun getAirQuality(): Flow<AirQuality>

    @Query("DELETE from $WEATHER_DATABASE_NAME")
    fun deleteAllWeatherDetails()

    @Query("DELETE from $AQ_DATABASE_NAME")
    fun deleteAllAirQualityDetails()
}