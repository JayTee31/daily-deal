package jaytee31.daily.deal.date;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

public class CurrentTime {
    private String year;
    private String month;
    private String day;

    public CurrentTime(final String year, final String month, final String day) {
        this.year = year;
        this.month = month.toUpperCase();
        this.day = day.toUpperCase();
    }

    public CurrentTime(final int day) {
        this(String.valueOf(LocalDateTime.now().getYear()), LocalDateTime.now().getMonth().name(), DayOfWeek.of(day).name());
    }

    public CurrentTime() {
        this(String.valueOf(LocalDateTime.now().getYear()), LocalDateTime.now().getMonth().name(), LocalDateTime.now().getDayOfWeek().name());
    }

    public int getNumberOfDay() {
        for (Day day : Day.values()) {
            if (this.day.equals(day.name())) {
                return day.getNumberOfDay();
            }
        }

        throw new IllegalArgumentException();
    }

    public String getHungarianDayName() {
        for (Day day : Day.values()) {
            if (this.day.equals(day.name())) {
                return day.getHungarianName();
            }
        }

        throw new IllegalArgumentException();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return year + "/" +
                month + "/" +
                day;
    }
}
