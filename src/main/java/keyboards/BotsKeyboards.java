package keyboards;

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Keyboards for my bot.
 */
public class BotsKeyboards {
    public InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

    /**
     * Condition of keyboard for SimplyTemperatureGetting.
     */
    public void keyboardForSimple() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> keys = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        keys.add(button.setText("Подробно").setCallbackData("Подробно"));
        keyboard.add(keys);
        markup.setKeyboard(keyboard);
    }

    /**
     * Condition of keyboard for DetailWeatherData.
     */
    public void keyboardForDetail() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> keys = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        keys.add(button.setText("Кратко").setCallbackData("Кратко"));
        keyboard.add(keys);
        markup.setKeyboard(keyboard);
    }
}
