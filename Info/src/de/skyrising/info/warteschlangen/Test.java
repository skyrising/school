package de.skyrising.info.warteschlangen;

import java.io.IOException;
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
	try
	{
	    while(System.in.read() >= 0)
	    {
		System.out.println(createRandomTaxi());
	    }
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    private static Taxi createRandomTaxi()
    {
	return new Taxi(getRandomName(), getRandomLicencePlate());
    }
    
    private static String getRandomName()
    {

	StringBuilder name = new StringBuilder();
        boolean b = random.nextBoolean();
        char last = 0;
	for(int i = 0; i < 5 - nextIntGau(5, 2) + 3 || last == 'q'; i++)
        {
            char c = getRandomChar();
            while(!((c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')^(last == 'a' || last == 'e' || last == 'i' || last == 'o' || last == 'u')))
        	c = getRandomChar();
            if(last == 'q')
        	c = 'u';
            last = c;
            if(i == 0)
                c += 'A' - 'a';
            name.append(c);
            b = !b;
        }
	return name.toString();
    }
    
    private static String getRandomLicencePlate()
    {

	StringBuilder licence = new StringBuilder();
        char[] vocals = new char[]{'e','a','i','o','u'};
        char[] consons = new char[]{'n','b','d','f','g','h','w','k','l','m','c','p','j','r','s','t','v','q','x','y','z'};
        for(int i = 0; i < 3; i++)
        {
            char c = (char)('A' - 'a');
            if(random.nextBoolean())
        	licence.append((char)(c + vocals[random.nextInt(vocals.length)]));
            else
        	licence.append((char)(c + consons[random.nextInt(vocals.length)]));
        }
        licence.append('-');
        for(int i = 0; i < 2; i++)
        {
            char c = (char)('A' - 'a');
            if(random.nextBoolean())
        	licence.append((char)(c + vocals[random.nextInt(vocals.length)]));
            else
        	licence.append((char)(c + consons[random.nextInt(vocals.length)]));
        }
        licence.append('-');
        for(int i = 0; i < 4; i++)
        {
            if(random.nextInt(i + 1) == 0 || i == 0)
        	licence.append(random.nextInt(10));
            else break;
        }
        return licence.toString();
    }
    
    private static char getRandomChar()
    {
	double d = random.nextDouble()*100;
	if(d < 17.4)return 'e';
	if((d -= 17.4) < 9.78)return 'n';
	if((d -= 9.78) < 7.55)return 'i';
	if((d -= 7.55) < 7.27)return 's';
	if((d -= 7.27) < 7.00)return 'r';
	if((d -= 7.00) < 6.51)return 'a';
	if((d -= 6.51) < 6.15)return 't';
	if((d -= 6.15) < 5.08)return 'd';
	if((d -= 5.08) < 4.76)return 'h';
	if((d -= 4.76) < 4.35)return 'u';
	if((d -= 4.35) < 3.44)return 'l';
	if((d -= 3.44) < 3.06)return 'c';
	if((d -= 3.06) < 3.01)return 'g';
	if((d -= 3.01) < 2.53)return 'm';
	if((d -= 2.53) < 2.51)return 'o';
	if((d -= 2.51) < 1.89)return 'b';
	if((d -= 1.89) < 1.89)return 'w';
	if((d -= 1.89) < 1.66)return 'f';
	if((d -= 1.66) < 1.21)return 'k';
	if((d -= 1.21) < 1.13)return 'z';
	if((d -= 1.13) < 0.79)return 'p';
	if((d -= 0.79) < 0.67)return 'v';
	if((d -= 0.67) < 0.31)return '§';
	if((d -= 0.31) < 0.27)return 'j';
	if((d -= 0.27) < 0.04)return 'y';
	if((d -= 0.04) < 0.03)return 'x';
	if((d -= 0.03) < 0.02)return 'q';
	
	return 'Ž';
    }
    
    private static int nextIntGau(int max, int r)
    {
	int i = 1;
	while(random.nextInt(r) != 0 && ++i < max);
	return i - 1;
    }
}
