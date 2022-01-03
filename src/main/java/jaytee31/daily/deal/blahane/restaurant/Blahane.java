package jaytee31.daily.deal.blahane.restaurant;

import jaytee31.daily.deal.blahane.menu.BlahaneDailyMenu;
import jaytee31.daily.deal.date.CurrentTime;
import jaytee31.daily.deal.menu.DailyMenu;
import jaytee31.daily.deal.menu.DailyMenuStatus;
import jaytee31.daily.deal.restaurant.Restaurant;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Objects;

public class Blahane implements Restaurant<BlahaneDailyMenu> {
    private final static String RESTAURANT_NAME = "Blahane";
    private final static String URL = "http://www.menurendeles.hu/";
    private final static Logger LOGGER = LoggerFactory.getLogger(Blahane.class);

    public Blahane() {
        LOGGER.info("New instance was created.");
    }

    @Override
    public DailyMenu<BlahaneDailyMenu> getDailyMenu(CurrentTime currentTime) {
        final String allInformation = extractInformationFromSite();
        final DailyMenuStatus status = extractStatusFromInformation(allInformation, currentTime);

        if (!status.equals(DailyMenuStatus.AVAILABLE)) {
            return new DailyMenu<>(currentTime, RESTAURANT_NAME, status, null);
        }

        BlahaneDailyMenu blahaneMenu = new BlahaneDailyMenu(
                extractSoupsFromSite(),
                extractCoursesFromInformation(allInformation, "A.", "B."),
                extractCoursesFromInformation(allInformation, "B.", "C."),
                extractCoursesFromInformation(allInformation, "C.", "D."),
                extractCoursesFromInformation(allInformation, "D.", "F."),
                extractCoursesFromInformation(allInformation, "F.", null),
                extractPrice());

        return new DailyMenu<>(currentTime, RESTAURANT_NAME, status, blahaneMenu);
    }

    private String extractInformationFromSite() {
        final String selector = "body > div.main > div.right > div:nth-child(1)";
        String information = "";

        try {
            information = Jsoup.connect(URL).get().select(selector).text();
            LOGGER.info("Extracting information from site.");
            LOGGER.debug("Extracting information from element: {}, and converting to text: {}.", selector, information);
        } catch (IOException e) {
            LOGGER.warn("Could not download information from the site.");
        }

        return information;
    }

    private String extractSoupsFromSite() {
        final String selector = "body > div.main > div.right > div:nth-child(1) > ul";
        String soups = "";

        try {
            soups = Jsoup.connect(URL).get().select(selector).text();
            LOGGER.info("Extracting soups from site.");
            LOGGER.debug("Extracting soups from element: {}, and converting to text: {}.", selector, soups);
        } catch (IOException e) {
            soups = "Soups not found";
            LOGGER.warn("Could not download information from the site.");
        }

        return soups;
    }

    private DailyMenuStatus extractStatusFromInformation(final String information, final CurrentTime currentTime) {
        if (Objects.isNull(information)) {
            return DailyMenuStatus.MISSING_FOR_UNKNOWN_REASON;
        } else if (information.isEmpty()) {
            return DailyMenuStatus.HOLIDAY;
        } else if (currentTime.getNumberOfDayOfWeek() > 5) {
            return DailyMenuStatus.RESTAURANT_CLOSED;
        }

        return DailyMenuStatus.AVAILABLE;
    }

    private String extractCoursesFromInformation(final String allInformation, final String startSubstring, final String endSubstring) {
        int startIndex = 0;
        int endIndex = 0;

        if (Objects.isNull(startSubstring) || !allInformation.contains(startSubstring)) {
            return "The course does not exist.";
        }

        if (Objects.isNull(endSubstring) || !allInformation.contains(endSubstring)) {
            endIndex = allInformation.length();
        } else {
            endIndex = allInformation.indexOf(endSubstring);
        }

        startIndex = allInformation.indexOf(startSubstring) + startSubstring.length();

        return allInformation.substring(startIndex, endIndex);
    }

    private String extractPrice() {
        final String selector = "body > div.main > div.left > div.content > div.kiemelt > p:nth-child(4) > strong > span > span > span > span > span > span > span > span > span > span > span > span > span > span > span > span > span > span > span > span > span > span > span > span > span";
        String price = "";

        try {
            price = Jsoup.connect(URL).get().select(selector).text();
            LOGGER.info("Extracting price from site.");
            LOGGER.debug("Extracting price from element: {}, and converting to text: {}.", selector, price);
        } catch (IOException e) {
            price = "Price not found.";
            LOGGER.warn("Could not download information from the site.");
        }

        return price;
    }
}
