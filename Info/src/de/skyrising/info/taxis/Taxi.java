package de.skyrising.info.taxis;

public class Taxi
{
    private String driver;
    private String licencePlate;
    
    public Taxi(String driver, String licence)
    {
	this.driver = driver;
	this.licencePlate = licence;
    }
    
    public String getDriver()
    {
	return driver;
    }
    
    public String getLicencePlate()
    {
	return licencePlate;
    }
    
    @Override
    public String toString()
    {
        return "Taxi[driver:" + driver + ",licencePlate:" + licencePlate + "]";
    }
}
