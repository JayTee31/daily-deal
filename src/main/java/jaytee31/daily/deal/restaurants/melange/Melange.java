package jaytee31.daily.deal.restaurants.melange;

import jaytee31.daily.deal.date.CurrentTime;
import jaytee31.daily.deal.date.Day;
import jaytee31.daily.deal.menu.DailyMenu;
import jaytee31.daily.deal.menu.DailyMenuStatus;
import jaytee31.daily.deal.menu.specificmenus.MelangeDailyMenu;
import jaytee31.daily.deal.restaurants.Restaurant;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class Melange implements Restaurant<MelangeDailyMenu> {
    private final static String RESTAURANT_NAME = "Melange";
    private final static String URL = "https://melangekavehaz.hu/en/gastronomy-home/";
    private final static Logger LOGGER = LoggerFactory.getLogger(Melange.class);
    private final String allInformation;
    private DailyMenuStatus status;

    public Melange() {
        allInformation = extractInformationFromSite();
        status = DailyMenuStatus.AVAILABLE;
        LOGGER.info("New instance was created.");
    }

    @Override
    public DailyMenu<MelangeDailyMenu> getDailyMenu(final CurrentTime currentTime) {
        MelangeDailyMenu melangeMenu = new MelangeDailyMenu(extractSoup(currentTime), extractCourseA(currentTime), extractCourseB(currentTime), extractPrice());

        return new DailyMenu<>(currentTime, RESTAURANT_NAME, status, melangeMenu);
    }

    private String extractInformationFromSite() {
        final String selector = "#hetimenu";
        String information = "";

        try {
            information = Jsoup.connect(URL).get().select(selector).text();
            LOGGER.info("Extracting information from site.");
            LOGGER.debug("Extracting information from element: {}, and converting to text: {}.", selector, information);
        } catch (IOException e) {
            status = DailyMenuStatus.MISSING_FOR_UNKNOWN_REASON;
            LOGGER.warn("Could not download information from the site.");
            return "";
        }

        return information;
    }

    private String extractDay(final CurrentTime currentTime) {
        if (!status.equals(DailyMenuStatus.AVAILABLE)) {
            return "";
        }

        if (currentTime.getNumberOfDayOfWeek() > 5) {
            status = DailyMenuStatus.RESTAURANT_CLOSED;
            return "";
        }

        final String nextDay = Day.getHungarianName(currentTime.getNextDaysName());

        final int startIndex = allInformation.indexOf(currentTime.getHungarianDayName());
        int endIndex = 0;

        if (currentTime.getNameOfDay().equals(Day.FRIDAY.name())) {
            endIndex = allInformation.indexOf(extractPrice());
        } else {
            endIndex = allInformation.indexOf(nextDay);
        }

        return allInformation.substring(startIndex, endIndex);
    }

    private String extractSoup(final CurrentTime currentTime) {
        if (!status.equals(DailyMenuStatus.AVAILABLE)) {
            return "";
        }

        final String dailyInformation = extractDay(currentTime);
        final int startIndex = dailyInformation.indexOf(currentTime.getHungarianDayName()) + currentTime.getHungarianDayName().length();
        final int endIndex = dailyInformation.indexOf("„A\"");

        return dailyInformation.substring(startIndex, endIndex);
    }

    private String extractCourseA(final CurrentTime currentTime) {
        if (!status.equals(DailyMenuStatus.AVAILABLE)) {
            return "";
        }

        final String dailyInformation = extractDay(currentTime);
        final int startIndex = dailyInformation.indexOf("„A\"") + "„A\"".length() + 1;
        int endIndex = dailyInformation.indexOf("„B\"");

        if (!dailyInformation.contains("„A\"")) {
            return "A course is not exist.";
        }

        if (!dailyInformation.contains("„B\"")) {
            endIndex = dailyInformation.length();
        }

        return dailyInformation.substring(startIndex, endIndex);
    }

    private String extractCourseB(final CurrentTime currentTime) {
        if (!status.equals(DailyMenuStatus.AVAILABLE)) {
            return "";
        }

        final String dailyInformation = extractDay(currentTime);
        final int startIndex = dailyInformation.indexOf("„B\"") + "„B\"".length() + 1;
        int endIndex = dailyInformation.length();

        if (!dailyInformation.contains("„B\"")) {
            return "B course is not exist.";
        }

        return dailyInformation.substring(startIndex, endIndex);
    }


    private String extractPrice() {
        if (!status.equals(DailyMenuStatus.AVAILABLE)) {
            return "";
        }

        final String selector = "#hetimenu > div > div > div > div > div:nth-child(7) > div > div > div.wpb_text_column.wpb_content_element > div";
        String price = "";

        try {
            price = Jsoup.connect(URL).get().select(selector).text();
            LOGGER.info("Extracting price from site.");
            LOGGER.debug("Extracting information from element: {}, and converting to text: {}", selector, price);
        } catch (IOException e) {
            LOGGER.warn("Could not download the site.");
            status = DailyMenuStatus.MISSING_FOR_UNKNOWN_REASON;
        }

        return price;
    }
}
