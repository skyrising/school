package de.skyrising.info.taxis.school;

public class Taxi
{
    private String kennzeichen;
    private String fahrername;

    public Taxi(String k, String f)
    {
	kennzeichen = k;
	fahrername = f;
    }

    public String kennzeichenGeben()
    {
	return kennzeichen;
    }

    public String fahrernameGeben()
    {
	return fahrername;
    }
}