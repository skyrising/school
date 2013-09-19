package de.skyrising.info.warteschlangen.school;

import java.awt.Color;
import java.util.*;

//page 13 fig. 2
public class Polizeikontrolle
{
    private Autowarteschlange wartend = new Autowarteschlange();
    private List<Auto> kontrollliste = new ArrayList<Auto>();
    private static Random random = new Random();
    
    public void autoHereinwinken(Auto a)
    {
	wartend.hintenAnstellen(a);
    }
    
    public void autoKotrollieren()
    {
	Auto a = wartend.vorneAbfahren();
	a.inOrdnungSetzen(random.nextInt(20) != 0);
	kontrollliste.add(a);
    }
    
    public String kontrolllisteGeben()
    {
	return kontrollliste.toString();
    }
    
    public static void main(String[] args)
    {
	Polizeikontrolle k = new Polizeikontrolle();
	for(int i = 0; i < 20; i++)
	    k.autoHereinwinken(new Auto("m" + (random.nextInt(50) + 10), random.nextInt(0x1000000)));
	while(!k.wartend.istLeer())
	    k.autoKotrollieren();
	System.out.println(k.kontrolllisteGeben().replace(", ", ",\n"));
    }
}
