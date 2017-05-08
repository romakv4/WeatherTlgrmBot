package telegram.weatherBot;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
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
    private boolean firstMessageIsSend = false;

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
                    sendMsg(message, "Теперь введите название своего города", true);
                } else if (message.getText().equals("/help")) {
                    sendMsg(message, "Введите название города, чтобы узнать текущую погоду в нем", true);
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
            answerCallbackQuery.setCallbackQueryId(query.getId());
            if (query.getData().equals("Подробно")) {
                BotsKeyboards botsKeyboards = new BotsKeyboards();
                botsKeyboards.keyboardForDetail();
                String answer = getWeather(city);
                EditMessageText new_message = new EditMessageText()
                        .setChatId(update.getCallbackQuery().getMessage().getChatId())
                        .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                        .enableMarkdown(true)
                        .setReplyMarkup(botsKeyboards.markup)
                        .setText(answer.replaceAll("\\[|]", ""));
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
                        .setText(answer.replaceAll("\\[|]", ""));
                try {
                    editMessageText(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendMsg(Message message, String text, boolean isReplay) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        if (isReplay) {
            sendMessage.setReplyToMessageId(message.getMessageId());
        }
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> keys = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        keys.add(button.setText("Подробно").setCallbackData("Подробно"));
        keyboard.add(keys);
        markup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(markup);
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}