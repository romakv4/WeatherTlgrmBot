package telegram.weatherBot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static telegram.weatherBot.WeatherGetting.getWeather;

/**
 * WeatherTelegramBot!
 */
public class WeatherTlgrmBot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new WeatherTlgrmBot());
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
            if (message.getText().equals("/help")) {
                sendMsg(message, "");
            } else if (message.getText().equals("/weather")) {
                sendMsg(message, (getWeather()));
            }
        }
    }

    private void sendMsg(Message message, String text) {
        org.telegram.telegrambots.api.methods.send.SendMessage sendMessage = new org.telegram.telegrambots.api.methods.send.SendMessage();
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