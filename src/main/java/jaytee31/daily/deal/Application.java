package jaytee31.daily.deal;

import jaytee31.daily.deal.blahane.restaurant.Blahane;
import jaytee31.daily.deal.date.CurrentTime;
import jaytee31.daily.deal.melange.restaurant.Melange;

public class Application {
    public static void main(String[] args) {
        Melange melange = new Melange();
        Blahane blahane = new Blahane();

        System.out.println(melange.getDailyMenu(new CurrentTime()));
        System.out.println(blahane.getDailyMenu(new CurrentTime()));
    }
}
