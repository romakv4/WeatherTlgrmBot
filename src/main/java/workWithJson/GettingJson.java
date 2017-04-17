package workWithJson;

import java.io.IOException;

/**
 * Class for getting JSON data.
 */
public class GettingJson {

    public String gettingJson(String city) {

        String json = null;

        try {
            json = JSONRequestBuilder.getJSON("http://api.openweathermap.org/data/2.5/weather?q=" + city +
                    "&APPID=d4f9cdcc72088078ab2092ebe1841883");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
