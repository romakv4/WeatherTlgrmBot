package weatherGetting;

import com.google.gson.Gson;
import currentWeatherData.OpenWeatherAPI;
import workWithJson.GettingJson;

import java.util.Arrays;

import static java.lang.String.format;

/**
 * This method send to user temperature in his city.
 */
public class SimplyTemperatureGetting {
    public static String getSimplyTemperature(String city) {

        GettingJson gJson = new GettingJson();

        Gson gson = new Gson();

        OpenWeatherAPI openWeatherAPI = gson.fromJson(gJson.gettingJson(city), OpenWeatherAPI.class);

        double Temp = Double.valueOf(openWeatherAPI.getMain().getTemp_max()) - 273.15;

        String[] strings = {
                format("Температура: %d°C", Math.round(Temp)),
        };
        return Arrays.toString(strings).replaceAll(", ", "\n");
    }
}
