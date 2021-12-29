package jaytee31.daily.deal.restaurants;

public enum Day {
    HÉTFŐ(1),
    KEDD(2),
    SZERDA(3),
    CSÜTÖRTÖK(4),
    PÉNTEK(5),
    SZOMBAT(6),
    VASÁRNAP(7);

    private final int numberOfDay;

    Day(final int numberOfDay) {
        this.numberOfDay = numberOfDay;
    }

    public static String getDayByNumber(int number) {
        for (Day day : Day.values()) {
            if (day.numberOfDay == number) {
                return day.name();
            }
        }

        return null;
    }
}
