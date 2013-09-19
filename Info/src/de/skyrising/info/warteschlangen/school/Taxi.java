package de.skyrising.info.warteschlangen.school;

//page 11 blue box 2
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