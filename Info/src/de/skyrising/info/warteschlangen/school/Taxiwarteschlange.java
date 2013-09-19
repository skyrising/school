
package de.skyrising.info.warteschlangen.school;

//page 10 fig. 1
public class Taxiwarteschlange 
{
    private int anzahlTaxis;
    private Taxi[] taxistand;
    
    public Taxiwarteschlange()
    {
        taxistand = new Taxi[10];
        anzahlTaxis = 0;
    }
    
    //page 11 fig. 1
    public void hintenAnstellen(Taxi t)
    {
        if(anzahlTaxis == taxistand.length)
        {
            Taxi[] old = taxistand;
            taxistand = new Taxi[anzahlTaxis*2];
            System.arraycopy(old, 0, taxistand, 0, anzahlTaxis);
        }
        taxistand[anzahlTaxis++] = t;
    }

    //page 11 fig. 2
    public Taxi vorneAbfahren()
    {
	//4 lines < 15 lines
        if(anzahlTaxis == 0)return null;
        Taxi ret = taxistand[0];
        System.arraycopy(taxistand, 1, taxistand, 0, --anzahlTaxis);
        return ret;
    }

    //page 11 blue box 1
    public int anzahlGeben()
    {
        return anzahlTaxis;
    }

    //page 11 fig. 3
    public void fahrerlisteAusgeben()
    {
        StringBuilder b = new StringBuilder();
        for(int i = 0; i < anzahlTaxis; i++)
        {
            if(b.length() != 0)
                b.append(" | ");
            b.append(taxistand[i].fahrernameGeben());
        }
        System.out.println(b);
    }

    //page 11 blue box 1
    public boolean istLeer()
    {
        return anzahlTaxis <= 0;
    }
}
