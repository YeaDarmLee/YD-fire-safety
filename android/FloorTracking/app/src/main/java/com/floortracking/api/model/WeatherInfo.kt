package com.floortracking.api.model

data class WeatherInfo(
    val coord: Coord,
    val weather: Array<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
) {

    data class Coord(val lon: Float, val lat: Float)
    data class Weather(val id: Int, val main: String, val description: String, val icon: String)
    data class Main(
        val temp: Float, val feels_like: Float, val temp_min: Float, val temp_max: Float,
        val pressure: Int, val humidity: Int, val sea_level: Int, val grnd_level: Int
    )

    data class Wind(val speed: String, val deg: String, val gust: String)
    data class Clouds(val all: Int)
    data class Sys(
        val type: Int,
        val id: Int,
        val country: String,
        val sunrise: Long,
        val sunset: Long
    )

    fun sealevel(): Int {
        if (main.sea_level > 0) return main.sea_level
        return main.pressure
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeatherInfo

        if (coord != other.coord) return false
        if (!weather.contentEquals(other.weather)) return false
        if (base != other.base) return false
        if (main != other.main) return false
        if (visibility != other.visibility) return false
        if (wind != other.wind) return false
        if (clouds != other.clouds) return false
        if (sys != other.sys) return false
        if (timezone != other.timezone) return false
        if (id != other.id) return false
        if (name != other.name) return false
        if (cod != other.cod) return false

        return true
    }

    override fun hashCode(): Int {
        var result = coord.hashCode()
        result = 31 * result + weather.contentHashCode()
        result = 31 * result + base.hashCode()
        result = 31 * result + main.hashCode()
        result = 31 * result + visibility
        result = 31 * result + wind.hashCode()
        result = 31 * result + clouds.hashCode()
        result = 31 * result + sys.hashCode()
        result = 31 * result + timezone
        result = 31 * result + id
        result = 31 * result + name.hashCode()
        result = 31 * result + cod
        return result
    }

}
