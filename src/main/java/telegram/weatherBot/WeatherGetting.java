package telegram.weatherBot;

import com.google.gson.Gson;
import json.OpenWeatherAPI;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;

/**
 * Weather getting class for WeatherTelegramBot.
 */
class WeatherGetting {

    private static OkHttpClient client = new OkHttpClient();

    static String getJSON(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    static String getWeather(String city) {

        GettingJson gJson = new GettingJson();

        Gson gson = new Gson();

        OpenWeatherAPI opwei = gson.fromJson(gJson.gettingJson(city), OpenWeatherAPI.class);

        WindRose wr = new WindRose();

        Double Temp = Double.valueOf(opwei.getMain().getTemp()) - 273.15;


        String[] strings = {
                "Город: " + opwei.getName(),
                "Облачность: " + opwei.getClouds(),
                "Ветер: " + opwei.getWind().getSpeed() + "м/с" + wr.windRose(opwei.getWind().getDeg()),
                "Погода: " + Temp + "°C"
        };

        return Arrays.toString(strings).replaceAll(", ", "\n");
    }
}
