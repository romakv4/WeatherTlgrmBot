package json;

/**
 * Main information.
 */
public class Main
{
    private Double tempmax;

    private Double tempmin;

    private String humidity;

    private String pressure;

    private String temp;

    public Double getTempmax ()
    {
        return tempmax;
    }

    public void setTempmax (Double tempmax)
    {
        this.tempmax = tempmax;
    }

    public Double getTempmin ()
    {
        return tempmin;
    }

    public void setTempmin (Double tempmin)
    {
        this.tempmin = tempmin;
    }

    public String getHumidity ()
    {
        return humidity;
    }

    public void setHumidity (String humidity)
    {
        this.humidity = humidity;
    }

    public String getPressure ()
    {
        return pressure;
    }

    public void setPressure (String pressure)
    {
        this.pressure = pressure;
    }

    public String getTemp ()
    {
        return temp;
    }

    public void setTemp (String temp)
    {
        this.temp = temp;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [tempmax = "+tempmax+", tempmin = "+tempmin+", humidity = "+humidity+", pressure = "+pressure+", temp = "+temp+"]";
    }
}
