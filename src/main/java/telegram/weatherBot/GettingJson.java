package telegram.weatherBot;

import java.io.IOException;

import static telegram.weatherBot.WeatherGetting.getJSON;

/**
 * Class for getting JSON data.
 */
class GettingJson {

    String gettingJson(String city) {

        String json = null;

        try {
            json = getJSON("http://api.openweathermap.org/data/2.5/weather?q=" + city +
                    "&APPID=d4f9cdcc72088078ab2092ebe1841883");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
