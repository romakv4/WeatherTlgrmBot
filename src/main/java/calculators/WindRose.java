package calculators;

/**
 * Its windRose for 8 direction.
 */
public class WindRose {

    public String windRose(Double degree) {
        String direction = null;
        if (degree >= 337.5 || degree <= 22.5) {
            direction = "⬇"; // северный
        } else if (degree > 22.5 && degree < 67.5) {
            direction = "↙"; // северо-восточный
        } else if (degree >= 67.5 && degree <= 112.5) {
            direction = "⬅"; // восточный
        } else if (degree > 112.5 && degree < 157.5) {
            direction = "↖"; // юго-восточный
        } else if (degree >= 157.5 && degree <= 202.5) {
            direction = "⬆"; // южный
        } else if (degree > 202.5 && degree < 247.5) {
            direction = "↗"; // юго-западный
        } else if (degree >= 247.5 && degree <= 292.5) {
            direction = "➡"; //западный
        } else if (degree > 292.5 && degree < 337.5){
            direction = "↘"; // северо-западный
        }
        return direction;
    }
}
