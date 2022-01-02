package jaytee31.daily.deal;

import jaytee31.daily.deal.date.CurrentTime;
import jaytee31.daily.deal.restaurants.Restaurant;
import jaytee31.daily.deal.restaurants.melange.Melange;

public class Application {
    public static void main(String[] args) {

        Melange piff = new Melange();

        //System.out.println(piff.extractInformationFromSite());

        System.out.println(piff.getDailyMenu(new CurrentTime()));

    }
}
