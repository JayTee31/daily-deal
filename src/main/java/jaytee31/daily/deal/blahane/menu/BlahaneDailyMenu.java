package jaytee31.daily.deal.blahane.menu;

public class BlahaneDailyMenu {
    private final String allSoup;
    private final String courseA;
    private final String courseB;
    private final String courseC;
    private final String courseD;
    private final String courseF;
    private final String price;

    public BlahaneDailyMenu(final String allSoup, final String courseA, final String courseB, final String courseC, final String courseD, final String courseF, final String price) {
        this.allSoup = allSoup;
        this.courseA = courseA;
        this.courseB = courseB;
        this.courseC = courseC;
        this.courseD = courseD;
        this.courseF = courseF;
        this.price = price;
    }

    @Override
    public String toString() {
        return "The soups are: " + allSoup +
                "\nA course is:" + courseA +
                "\nB course is:" + courseB +
                "\nC course is:" + courseC +
                "\nD course is:" + courseD +
                "\nF course is:" + courseF +
                "\nThe price is: " + price;
    }

    public String getAllSoup() {
        return allSoup;
    }

    public String getCourseA() {
        return courseA;
    }

    public String getCourseB() {
        return courseB;
    }

    public String getCourseC() {
        return courseC;
    }

    public String getCourseD() {
        return courseD;
    }

    public String getCourseF() {
        return courseF;
    }
}
