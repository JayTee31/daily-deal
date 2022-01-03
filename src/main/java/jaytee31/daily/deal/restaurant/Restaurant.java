package jaytee31.daily.deal.restaurant;

import jaytee31.daily.deal.date.CurrentTime;
import jaytee31.daily.deal.menu.DailyMenu;

public interface Restaurant<T> {
    DailyMenu<T> getDailyMenu(CurrentTime currentTime);
}
