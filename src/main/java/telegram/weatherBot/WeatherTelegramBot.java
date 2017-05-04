package telegram.weatherBot;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.*;

import static org.telegram.telegrambots.ApiContextInitializer.init;
import static telegram.weatherBot.SimplyTemperature.getSimplyTemperature;
import static telegram.weatherBot.WeatherGetting.getWeather;

/**
 * WeatherTelegramBot!
 */
public class WeatherTelegramBot extends TelegramLongPollingBot {

    private String city = null;
    private boolean isDetail = false;

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
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                if (message.getText().equals("/start")) {
                    sendMessage(message, "Теперь введите название своего города", true);
                } else if (message.getText().equals("/help")) {
                    sendMessage(message, "Введите название города, чтобы узнать текущую погоду в нем", true);
                } else if (message.getText() != null && !message.getText().equals("/help") &&
                        !message.getText().equals("/start") && !message.getText().equals("Подробнее")) {
                    sendMessage(message, getSimplyTemperature(message.getText()), true);
                    city = message.getText();
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            AnswerCallbackQuery answer = new AnswerCallbackQuery();
            answer.setCallbackQueryId(query.getId());
            if (query.getData().equals("Подробно")) {
                isDetail = true;
                sendMessage(query.getMessage(), "For " + city + "\n" + getWeather(city), false);
            } else if (query.getData().equals("Кратко")) {
                isDetail = false;
                sendMessage(query.getMessage(), "For " + city + "\n" + getSimplyTemperature(city), false);
            }
            try {
                answerCallbackQuery(answer);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(Message message, String text, boolean isReplay) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        if (isReplay) {
            sendMessage.setReplyToMessageId(message.getMessageId());
        }
        if (!isReplay) {
            sendMessage.setReplyToMessageId(message.getMessageId());
        }
        sendMessage.setText(text);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> keys = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        if (isDetail == false) {
            button.setText("Подробно");
            button.setCallbackData("Подробно");
        } else if (isDetail == true) {
            button.setText("Кратко");
            button.setCallbackData("Кратко");
        }
        keys.add(button);
        keyboard.add(keys);
        markup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(markup);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}