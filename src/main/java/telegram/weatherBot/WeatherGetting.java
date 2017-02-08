package telegram.weatherBot;

import com.google.gson.Gson;
import json.OpenWeatherAPI;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Weather getting class for WeatherTelegramBot.
 */
public class WeatherGetting {

    private static OkHttpClient client = new OkHttpClient();

    public static String getJSON(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String[] getWeather(String city) {
        String json = null;

        try {
            json = getJSON("http://api.openweathermap.org/data/2.5/weather?q=" + city +
                    "&APPID=d4f9cdcc72088078ab2092ebe1841883");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();

        OpenWeatherAPI opwei = gson.fromJson(json, OpenWeatherAPI.class);

        return new String[]{
                "City: " + opwei.getName(),
                "Clouds: " + opwei.getClouds()
        };
    }

}
