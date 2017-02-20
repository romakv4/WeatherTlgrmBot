package telegram.weatherBot;

import currentWeatherData.Clouds;

/**
 * Cloudiness interpretation.
 */
class Cloudiness {
    private String cloudiness = null;

    String getCloudiness(Clouds clouds) {

        int cloudPercentage = Integer.parseInt(String.valueOf(clouds));

        if (cloudPercentage == 0) {
            cloudiness = "Ясно";
        } else if (cloudPercentage > 0 && cloudPercentage < 30) {
            cloudiness = "Малооблачно";
        } else if (cloudPercentage > 30 && cloudPercentage < 70) {
            cloudiness = "Средняя облачность";
        } else if (cloudPercentage > 70 && cloudPercentage < 100) {
            cloudiness = "Сильная облачность";
        }
        return cloudiness;
    }
}
