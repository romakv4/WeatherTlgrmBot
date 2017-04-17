package telegram.weatherBot;

import calculators.Cloudiness;
import calculators.MillibarsToMmHg;
import calculators.WindRose;
import com.google.gson.Gson;
import currentWeatherData.OpenWeatherAPI;
import workWithJson.GettingJson;

import java.util.Arrays;

import static java.lang.String.format;

/**
 * Weather Getting class fo my bot.
 */
public class WeatherGetting {

    static String getWeather(String city) {

        GettingJson gJson = new GettingJson();

        Gson gson = new Gson();

        OpenWeatherAPI opwei = gson.fromJson(gJson.gettingJson(city), OpenWeatherAPI.class);

        WindRose wr = new WindRose();
        Cloudiness cloudiness = new Cloudiness();
        MillibarsToMmHg MTM = new MillibarsToMmHg();

        Double Temp = Double.valueOf(opwei.getMain().getTemp_max()) - 273.15;

        String[] strings = {
                format("Температура: %d°C", Math.round(Temp)),
                cloudiness.getCloudiness(opwei.getClouds()),
                format("Ветер: %s %sм/с", wr.windRose(opwei.getWind().getDeg()), Math.round(opwei.getWind().getSpeed())),
                format("Влажность: %s", opwei.getMain().getHumidity() + " %"),
                format("Давление: %s", MTM.millibarsToMmHg(opwei.getMain().getPressure()) + " мм рт.ст"),
        };
        return Arrays.toString(strings).replaceAll(", ", "\n");
    }
}
