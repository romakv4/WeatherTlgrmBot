package telegram.weatherBot;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.telegram.telegrambots.ApiContextInitializer.init;
import static telegram.weatherBot.SimplyTemperature.getSimplyTemperature;
import static telegram.weatherBot.WeatherGetting.getWeather;

/**
 * WeatherTelegramBot!
 */
public class WeatherTelegramBot extends TelegramLongPollingBot {

    private String city = null;
    private Boolean cityEntered = false;

    public static void main(String[] args) {
        init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new WeatherTelegramBot());
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
            if (message.getText().equals("/start")){
                sendMessage(message, "Теперь введите название своего города");
            } else if (message.getText().equals("/help")) {
                sendMessage(message, "Введите название города, чтобы узнать текущую погоду в нем");
            } else if (message.getText() != null && !message.getText().equals("/help") &&
                    !message.getText().equals("/start") && !message.getText().equals("Подробнее")) {
                sendMessage(message, getSimplyTemperature(message.getText()));
                city = message.getText();
            } else if(message.getText().equals("Подробнее")) {
                sendMessage(message, "For " + city + "\n" + getWeather(city));
            }
        }
    }

    private String editMessageText(String weather) {
        return weather;
    }

    private void sendMessage(Message message, String text) {
        SendMessage sendMessage
                = new SendMessage();
        sendMessage.enableMarkdown(true);
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Подробнее");
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