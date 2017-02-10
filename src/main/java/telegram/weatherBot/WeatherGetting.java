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

    static String getWeather() {

        String direction = null;

        GettingJson gJson = new GettingJson();

        Gson gson = new Gson();

        OpenWeatherAPI opwei = gson.fromJson(gJson.gettingJson("Moscow,rf"), OpenWeatherAPI.class);

        int degree = Integer.parseInt(opwei.getWind().getDeg());

        if (degree == 0) {
            direction = "западный";
        } else if (degree > 0 && degree <90) {
            direction = "юго-западный";
        } else if (degree == 90) {
            direction = "южный";
        } else if (degree > 90 && degree < 180) {
            direction = "юго-восточный";
        }else if (degree == 180) {
            direction = "восточный";
        }else if (degree > 180 && degree <270) {
            direction = "северо-восточный";
        }else if (degree == 270) {
            direction = "северный";
        }else if (degree > 270 && degree <= 360) {
            direction = "северо-западный";
        }

        String[] strings = {
                "Город: " + opwei.getName(),
                "Видимость: " + opwei.getVisibility(),
                "Скорость ветра: " + opwei.getWind().getSpeed() + "м/с",
                "Направление ветра: " + direction
        };

        return Arrays.toString(strings).replaceAll(", ", "\n");
    }
}
