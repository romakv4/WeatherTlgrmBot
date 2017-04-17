package workWithJson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;



/**
 * Weather getting class for WeatherTelegramBot.
 */
class JSONRequestBuilder {

    private static OkHttpClient client = new OkHttpClient();

    static String getJSON(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
