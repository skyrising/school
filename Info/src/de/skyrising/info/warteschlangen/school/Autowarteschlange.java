
package de.skyrising.info.warteschlangen.school;

//page 13 fig. 1.2
public class Autowarteschlange 
{
    private int anzahlAutos;
    private Auto[] autoliste;
    
    public Autowarteschlange()
    {
        autoliste = new Auto[10];
        anzahlAutos = 0;
    }

    public void hintenAnstellen(Auto a)
    {
        if(anzahlAutos == autoliste.length)
        {
            Auto[] old = autoliste;
            autoliste = new Auto[anzahlAutos*2];
            System.arraycopy(old, 0, autoliste, 0, anzahlAutos);
        }
        autoliste[anzahlAutos++] = a;
    }

    public Auto vorneAbfahren()
    {
        if(anzahlAutos == 0)return null;
        Auto ret = autoliste[0];
        System.arraycopy(autoliste, 1, autoliste, 0, --anzahlAutos);
        return ret;
    }

    public boolean istLeer()
    {
        return anzahlAutos <= 0;
    }
    
    public boolean istVoll()
    {
	return false;
    }
}
