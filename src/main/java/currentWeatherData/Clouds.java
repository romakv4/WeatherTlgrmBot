package currentWeatherData;

/**
 * Json-structure for information about clouds.
 */
public class Clouds {
    private String all;

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return all;
    }
}
