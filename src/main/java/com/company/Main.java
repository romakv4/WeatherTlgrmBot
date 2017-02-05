package com.company;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class Main extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Main());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "WeatherTlgrm_bot";
    }

    @Override
    public String getBotToken() {
        return "305385180:AAGx5qFwRSXTHV_t-IK-TCl801V3AUnV4ps";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            if (message.getText().equals("/help"))
                sendMsg(message, "Привет, я робот, чтобы узнать погоду введите команду /weather");

            else if (message.getText().equals("/weather")) {

                String query = "http://api.openweathermap.org/data/2.5/weather?q=" +
                        "Moscow,rf" +
                        "&APPID=d4f9cdcc72088078ab2092ebe1841883";

                HttpURLConnection connection = null;
                
                try {
                    connection = (HttpURLConnection) new URL(query).openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    assert connection != null;
                    connection.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                connection.setUseCaches(false);
                connection.setConnectTimeout(200);
                connection.setReadTimeout(200);

                try {
                    connection.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                StringBuilder sb = new StringBuilder();
                try {
                    if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        String line;
                        while ((line = in.readLine()) != null) {
                            sb.append(line);
                            sb.append("\n");
                        }

                        sendMsg(message, sb.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    connection.disconnect();
                }
            }
        }
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}