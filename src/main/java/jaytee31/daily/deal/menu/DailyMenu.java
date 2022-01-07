package jaytee31.daily.deal.menu;

import jaytee31.daily.deal.date.CurrentTime;

import java.util.Objects;

public class DailyMenu<T> {
    private final CurrentTime currentTime;
    private final String restaurantName;
    private final DailyMenuStatus status;
    private final T menu;

    public DailyMenu(final CurrentTime currentTime, final String restaurantName, final DailyMenuStatus dailyMenuStatus, final T menu) {
        this.currentTime = currentTime;
        this.restaurantName = restaurantName;
        this.menu = menu;
        this.status = dailyMenuStatus;
    }

    @Override
    public String toString() {
        String result = "The daily menu in: " + restaurantName +
                " at " + currentTime + "\n";

        switch (status) {
            case AVAILABLE:
                return result + menu;
            case HOLIDAY:
                return result + " is not available on holiday.";
            case RESTAURANT_CLOSED:
                return result + " is not available since the restaurant is closed.";
            case MISSING_FOR_UNKNOWN_REASON:
                return result + " is not available for unknown reasons";
            default:
                return "DailyMenuStatus is missing";
        }
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public DailyMenuStatus getStatus() {
        return status;
    }

    public T getMenu() {
        return menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyMenu<?> dailyMenu = (DailyMenu<?>) o;
        return Objects.equals(currentTime, dailyMenu.currentTime) && Objects.equals(restaurantName, dailyMenu.restaurantName) && status == dailyMenu.status && Objects.equals(menu, dailyMenu.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentTime, restaurantName, status, menu);
    }
}
