package jaytee31.daily.deal.melange.menu;

public class MelangeDailyMenu {
    private final String soup;
    private final String courseA;
    private final String courseB;
    private final String price;

    public MelangeDailyMenu(final String soup, final String courseA, final String courseB, final String price) {
        this.soup = soup;
        this.courseA = courseA;
        this.courseB = courseB;
        this.price = price;
    }

    @Override
    public String toString() {
        return "The soup is:" + soup +
                "\nA course is: " + courseA +
                "\nB course is: " + courseB +
                "\nThe price is: " + price;
    }

    public String getSoup() {
        return soup;
    }

    public String getCourseA() {
        return courseA;
    }

    public String getCourseB() {
        return courseB;
    }

    public String getPrice() {
        return price;
    }
}
