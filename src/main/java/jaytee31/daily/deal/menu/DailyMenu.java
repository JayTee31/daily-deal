package jaytee31.daily.deal.menu;

import jaytee31.daily.deal.date.CurrentTime;

public class DailyMenu<T> {
    private final CurrentTime currentTime;
    private final String restaurantName;
    private final DailyMenuStatus status;
    private final T menu;

    public DailyMenu(final CurrentTime currentTime, final String restaurantName, final DailyMenuStatus dailyMenuStatus, final T menu) {
        this.currentTime = currentTime;
        this.restaurantName = restaurantName;
        this.menu = menu;
        status = DailyMenuStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        String result = "The daily menu in: " + restaurantName +
                " at " + currentTime + "\n";

        if (status.equals(DailyMenuStatus.AVAILABLE)) {
            return result + menu;
        } else if (status.equals(DailyMenuStatus.HOLIDAY)) {
            return result + " is not available on holiday.";
        } else if (status.equals(DailyMenuStatus.RESTAURANT_CLOSED)) {
            return result + " is not available since the restaurant is closed.";
        } else if (status.equals(DailyMenuStatus.MISSING_FOR_UNKNOWN_REASON)) {
            return result + " is not available for unknown reasons";
        } else {
            throw new IllegalStateException("DailyMenuStatus is missing");
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
}
