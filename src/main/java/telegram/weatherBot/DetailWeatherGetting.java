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
 * Weather Getting class for my bot.
 */
class DetailWeatherGetting {

    static String getDetailWeather(String city) {

        GettingJson gJson = new GettingJson();

        Gson gson = new Gson();

        OpenWeatherAPI opwei = gson.fromJson(gJson.gettingJson(city), OpenWeatherAPI.class);

        WindRose wr = new WindRose();
        Cloudiness cloudiness = new Cloudiness();
        MillibarsToMmHg MTM = new MillibarsToMmHg();

        Double Temp = Double.valueOf(opwei.getMain().getTemp_max()) - 273.15;
        double pressure = Double.valueOf(opwei.getMain().getPressure());

        String[] strings = {
                format("Температура: %d°C", Math.round(Temp)),
                cloudiness.getCloudiness(opwei.getClouds()),
                format("Ветер: %s %sм/с", wr.windRose(Double.valueOf(opwei.getWind().getDeg())),
                        Math.round(Float.valueOf(opwei.getWind().getSpeed()))),
                format("Влажность: %s", opwei.getMain().getHumidity() + " %"),
                format("Давление: %s", Math.round(MTM.millibarsToMmHg(pressure))
                        + " мм рт.ст"),
        };
        return Arrays.toString(strings).replaceAll(", ", "\n");
    }
}
