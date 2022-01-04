package jaytee31.daily.deal.melange.menu;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MelangeDailyMenu that = (MelangeDailyMenu) o;
        return Objects.equals(soup, that.soup) && Objects.equals(courseA, that.courseA) && Objects.equals(courseB, that.courseB) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(soup, courseA, courseB, price);
    }
}
