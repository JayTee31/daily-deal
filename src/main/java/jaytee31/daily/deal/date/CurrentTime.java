package jaytee31.daily.deal.date;

import java.time.LocalDateTime;

public class CurrentTime {
    private final int year;
    private final int month;
    private final int dayOfMonth;
    private final String nameOfDay;

    public CurrentTime() {
        this(LocalDateTime.now());
    }

    public CurrentTime(final LocalDateTime localDateTime) {
        year = localDateTime.getYear();
        month = localDateTime.getMonthValue();
        dayOfMonth = localDateTime.getDayOfMonth();
        nameOfDay = localDateTime.getDayOfWeek().name();
    }

    public int getNumberOfDayOfWeek() {
        for (Day day : Day.values()) {
            if (this.nameOfDay.equals(day.name())) {
                return day.getNumberOfDay();
            }
        }

        throw new IllegalArgumentException();
    }

    public String getHungarianDayName() {
        for (Day day : Day.values()) {
            if (this.nameOfDay.equals(day.name())) {
                return day.getHungarianName();
            }
        }

        throw new IllegalArgumentException();
    }

    public String getNextDaysName() {
        return LocalDateTime.now().withDayOfMonth(dayOfMonth + 1).getDayOfWeek().name();
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public String getNameOfDay() {
        return nameOfDay;
    }

    @Override
    public String toString() {
        return year + "/" +
                month + "/" +
                dayOfMonth;
    }
}
