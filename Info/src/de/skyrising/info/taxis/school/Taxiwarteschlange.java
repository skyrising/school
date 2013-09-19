
package de.skyrising.info.taxis.school;

public class Taxiwarteschlange 
{
    private int anzahlTaxis;
    private Taxi[] taxistand;
    
    public Taxiwarteschlange()
    {
        taxistand = new Taxi[10];
        anzahlTaxis = 0;
    }
    
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
    
    public Taxi vorneAbfahren()
    {
        if(anzahlTaxis == 0)return null;
        Taxi ret = taxistand[0];
        System.arraycopy(taxistand, 1, taxistand, 0, --anzahlTaxis);
        return ret;
    }
    
    public int anzahlGeben()
    {
        return anzahlTaxis;
    }
    
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
    
    public boolean istLeer()
    {
        return anzahlTaxis <= 0;
    }
}
