package telegram.weatherBot;

/**
 * Its windRose for 8 direction.
 */
class WindRose {
    private String direction = null;

    String windRose(Double degree) {
        if (degree == 0) {
            direction = "→";
        } else if (degree > 0 && degree < 90) {
            direction = "↗";
        } else if (degree == 90) {
            direction = "↑";
        } else if (degree > 90 && degree < 180) {
            direction = "↖";
        } else if (degree == 180) {
            direction = "←";
        } else if (degree > 180 && degree < 270) {
            direction = "↙";
        } else if (degree == 270) {
            direction = "↓";
        } else if (degree > 270 && degree <= 360) {
            direction = "↘";
        }
        return direction;
    }
}
