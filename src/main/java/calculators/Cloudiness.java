package calculators;

import currentWeatherData.Clouds;

/**
 * Cloudiness interpretation.
 */
public class Cloudiness {
    private String cloudiness = null;

    public String getCloudiness(Clouds clouds) {

        int cloudPercentage = Integer.parseInt(String.valueOf(clouds));

        if (cloudPercentage == 0) {
            cloudiness = "Ясно ☀";
        } else if (cloudPercentage > 0 && cloudPercentage < 30) {
            cloudiness = "Малооблачно \uD83C\uDF24";
        } else if (cloudPercentage > 30 && cloudPercentage < 70) {
            cloudiness = "Средняя облачность ⛅";
        } else {
            cloudiness = "Сильная облачность \uD83C\uDF25";
        }
        return cloudiness;
    }
}
