package de.skyrising.info.taxis;

import java.util.Random;

public class Test
{
    private static Random random = new Random();
    
    public static void main(String[] args)
    {
	Taxistand stand = new Taxistand();
	for(int i = 0; i < 3; i++)
	    stand.offer(createRandomTaxi());
	System.out.println(stand);
	stand.poll();
	System.out.println(stand);
	for(int i = 0; i < 5; i++)
	    stand.offer(createRandomTaxi());
	System.out.println(stand);
    }
    
    private static Taxi createRandomTaxi()
    {
	StringBuilder d = new StringBuilder();
	for(int i = 0; i < 10; i++)
	    d.append((char)(random.nextInt(26) + 'a'));
	return new Taxi(d.toString(), d.reverse().toString());
    }
}
