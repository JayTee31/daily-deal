package jaytee31.daily.deal;

import jaytee31.daily.deal.restaurants.melange.Melange;
import jaytee31.daily.deal.restaurants.Restaurant;

public class Application {
    public static void main(String[] args) {

        Restaurant paff = Melange.getInstance();
        paff.getDailyDeal();
    }
}
