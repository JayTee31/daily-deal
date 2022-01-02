package jaytee31.daily.deal.restaurants;

import jaytee31.daily.deal.date.CurrentTime;
import jaytee31.daily.deal.menu.DailyMenu;

public interface Restaurant<T> {
    DailyMenu<T> getDailyMenu(CurrentTime currentTime);
}
