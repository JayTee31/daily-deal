package jaytee31.daily.deal.date;

public enum Day {
    MONDAY(1, "Hétfő"),
    TUESDAY(2, "Kedd"),
    WEDNESDAY(3, "Szerda"),
    THURSDAY(4, "Csütörtök"),
    FRIDAY(5, "Péntek"),
    SATURDAY(6, "Szombat"),
    SUNDAY(7, "Vasárnap");

    private final int numberOfDay;
    private final String hungarianName;

    Day(final int numberOfDay, final String hungarianName) {
        this.numberOfDay = numberOfDay;
        this.hungarianName = hungarianName;
    }

    public String getHungarianName() {
        return hungarianName;
    }

    public int getNumberOfDay() {
        return numberOfDay;
    }
}
