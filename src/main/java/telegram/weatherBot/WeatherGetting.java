package telegram.weatherBot;

import com.google.gson.Gson;
import currentWeatherData.OpenWeatherAPI;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;

import static java.lang.String.format;

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
        Cloudiness cloudiness = new Cloudiness();

        Double Temp = Double.valueOf(opwei.getMain().getTemp_max()) - 273.15;

        String[] strings = {
                format("Температура: %d°C", Math.round(Temp)),
                cloudiness.getCloudiness(opwei.getClouds()),
                format("Ветер: %s %sм/с", wr.windRose(opwei.getWind().getDeg()), Math.round(opwei.getWind().getSpeed())),
                format("Влажность: %s", opwei.getMain().getHumidity() + " %"),
//                format("Восход: %s", opwei.getSys().getSunrise()),
//                format("Закат: %s", opwei.getSys().getSunset()),
        };

        return Arrays.toString(strings).replaceAll(", ", "\n");
    }
}
