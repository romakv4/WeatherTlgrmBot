package telegram.weatherBot;

import keyboards.BotsKeyboards;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.telegram.telegrambots.ApiContextInitializer.init;
import static weatherGetting.DetailWeatherGetting.getDetailWeather;
import static weatherGetting.SimplyTemperatureGetting.getSimplyTemperature;

/**
 * WeatherTelegramBot!
 */
public class WeatherTelegramBot extends TelegramLongPollingBot {

    private String city = null;

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
                    sendSimpleMsg(message, "Теперь введите название своего города");
                } else if (message.getText().equals("/help")) {
                    sendSimpleMsg(message, "Введите название города, чтобы узнать текущую погоду в нем");
                } else if (!message.getText().equals("/start") && !message.getText().equals("/help")) {
                    city = message.getText();
                    sendMsg(message, getSimplyTemperature(city));
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
            answerCallbackQuery.setCallbackQueryId(query.getId());
            if (query.getData().equals("Подробно")) {
                BotsKeyboards botsKeyboards = new BotsKeyboards();
                botsKeyboards.keyboardForDetail();
                String answer = getDetailWeather(city);
                EditMessageText new_message = new EditMessageText()
                        .setChatId(update.getCallbackQuery().getMessage().getChatId())
                        .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                        .enableMarkdown(true)
                        .setReplyMarkup(botsKeyboards.markup)
                        .setText(answer.replaceAll("[|]", ""));
                try {
                    editMessageText(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (query.getData().equals("Кратко")) {
                String answer = getSimplyTemperature(city);
                BotsKeyboards botsKeyboards = new BotsKeyboards();
                botsKeyboards.keyboardForSimple();
                EditMessageText new_message = new EditMessageText()
                        .setChatId(update.getCallbackQuery().getMessage().getChatId())
                        .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                        .enableMarkdown(true)
                        .setReplyMarkup(botsKeyboards.markup)
                        .setText(answer.replaceAll("[|]", ""));
                try {
                    editMessageText(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void sendMsg(Message message, String text) {
        BotsKeyboards botsKeyboards = new BotsKeyboards();
        botsKeyboards.keyboardForSimple();
        SendMessage sendMessage = new SendMessage()
                .enableMarkdown(true)
                .setChatId(message.getChatId().toString())
                .setReplyMarkup(botsKeyboards.markup)
                .setText(text)
                .setReplyToMessageId(message.getMessageId());
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendSimpleMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage()
                .enableMarkdown(true)
                .setChatId(message.getChatId().toString())
                .setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}