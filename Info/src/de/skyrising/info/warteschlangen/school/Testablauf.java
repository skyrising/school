package de.skyrising.info.warteschlangen.school;

//page 12/1
public class Testablauf 
{
    public static void main(String[] args)
    {
        new Testablauf().ablaufen();
    }

    private Taxi taxiA;
    private Taxi taxiB;
    private Taxi taxiC;
    private Taxi taxiD;
    private Taxi taxiE;
    private Taxi taxiF;
    private Taxi taxiG;
    private Taxi taxiH;
    private Taxi taxiI;
    private Taxi taxiJ;
    private Taxiwarteschlange taxiwarteschlange1;
    
    private void taxisErzeugen()
    {
        taxiA = new Taxi("DON-R-1313", "Jojo");
        taxiB = new Taxi("DON-T-100", "Taxifahrer John");
        taxiC = new Taxi("DON-X-666", "Fränk");
        taxiD = new Taxi("DON-B-007", "James");
        taxiE = new Taxi("DON-A-380", "Hans");
        taxiF = new Taxi("DON-B-747", "Isabel");
        taxiG = new Taxi("DON-EC-135", "Simon");
        taxiH = new Taxi("DON-HI-1234", "Tom");
        taxiI = new Taxi("DON-UT-42", "Homer");
        taxiJ = new Taxi("DON-AK-47", "Vladimir");
        taxiwarteschlange1 = new Taxiwarteschlange();
    }
    
    public void ablaufen() {
       taxisErzeugen();
       taxiwarteschlange1.hintenAnstellen(taxiA);
       taxiwarteschlange1.hintenAnstellen(taxiB);
       taxiwarteschlange1.fahrerlisteAusgeben();
       for(int i = 0; i < 5; i++)
           taxiwarteschlange1.vorneAbfahren();
       taxiwarteschlange1.hintenAnstellen(taxiC);
       taxiwarteschlange1.hintenAnstellen(taxiD);
       taxiwarteschlange1.hintenAnstellen(taxiE);
       taxiwarteschlange1.hintenAnstellen(taxiF);
       taxiwarteschlange1.fahrerlisteAusgeben();
       taxiwarteschlange1.vorneAbfahren();
       taxiwarteschlange1.fahrerlisteAusgeben();
       taxiwarteschlange1.hintenAnstellen(taxiG);
       taxiwarteschlange1.hintenAnstellen(taxiH);
       taxiwarteschlange1.hintenAnstellen(taxiI);
       taxiwarteschlange1.hintenAnstellen(taxiJ);
       taxiwarteschlange1.fahrerlisteAusgeben();
    }
}
