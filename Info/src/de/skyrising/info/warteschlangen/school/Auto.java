package de.skyrising.info.warteschlangen.school;

import java.awt.Color;

//page 13 fig. 1.1
public class Auto
{
    
    private String modell;
    private int farbe;
    private boolean inOrdnung = false;
    private boolean kontrolliert = false;
    
    public Auto(String modell, int farbe)
    {
	this.modell = modell;
	this.farbe = farbe;
    }
    
    public String datenwerteGeben()
    {
	return toString();
    }
    
    public void inOrdnungSetzen(boolean io)
    {
	inOrdnung = io;
	kontrolliert = true;
    }
    
    @Override
    public String toString()
    {
	return String.format("Auto[modell:%s,farbe:#%06X,inOrdnung:%s]", modell, farbe, (kontrolliert ? inOrdnung : "not set"));
    }
}
