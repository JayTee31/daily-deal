package jaytee31.daily.deal.restaurants.melange;

import jaytee31.daily.deal.restaurants.Day;
import jaytee31.daily.deal.restaurants.Restaurant;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

public class Melange implements Restaurant {
    private static Melange instance = null;
    private final static String url = "https://melangekavehaz.hu/en/gastronomy-home/";
    private final static Logger LOGGER = LoggerFactory.getLogger(Melange.class);
    private final String allInformation;
    private final LocalDateTime date;
    private String day;


    private Melange() {
        allInformation = extractInformationFromSite();
        date = LocalDateTime.now();
        setDay();
        LOGGER.info("New instance was created.");
    }

    @Override
    public void getDailyDeal() {
        if (extractNumberOfDay() > 5) {
            LOGGER.warn("No available menu on: {}", date.getDayOfWeek().name());
            System.out.println("There is no available menu on that day.");
            return;
        }

        int startIndex = allInformation.indexOf(day) + day.length() + 1;
        int endIndex = 0;
        final String dailyDeal;

        if (extractNumberOfDay() == 5) {
            endIndex = allInformation.indexOf(extractPrice());
        } else {
            endIndex = allInformation.indexOf(convertToCapitalForm(Day.getDayByNumber(extractNumberOfDay() + 1)));
        }

        LOGGER.debug("Get startIndex which is: {}, endIndex which is: {}", startIndex, endIndex);

        dailyDeal = allInformation.substring(startIndex, endIndex);
        LOGGER.info("Get needed information from all information.");

        System.out.println(dailyDeal + "\n" + extractPrice());
        LOGGER.info("Printing daily deal and price.");
    }

    public static Melange getInstance() {
        if (instance == null) {
            instance = new Melange();
            return instance;
        } else {
            throw new ExceptionInInitializerError();
        }
    }

    public String extractInformationFromSite() {
        final String selector = "#hetimenu";
        String information = "";

        try {
            information = Jsoup.connect(url).get().select(selector).text();
            LOGGER.info("Extracting information from site.");
            LOGGER.debug("Extracting information from element: {}, and converting to text: {}.", selector, information);
        } catch (IOException e) {
            System.out.printf("Information not found on the %s site.", url);
            LOGGER.warn("Could not download information from the site.");
        }

        return information;
    }

    public String getDay() {
        return day;
    }

    private void setDay() {
        String simpleDay = Day.getDayByNumber(extractNumberOfDay());
        LOGGER.info("Convert the name of the day from english to hungarian.");
        LOGGER.debug("Converting the day to hungarian name, the day is: {}", simpleDay);
        day = convertToCapitalForm(simpleDay);
    }

    private int extractNumberOfDay() {
        final int numberOfDay = date.getDayOfWeek().getValue();
        LOGGER.info("Convert day of the week to int format.");
        LOGGER.debug("Convert day to int, the day is: {}, the int is: {}", date.getDayOfWeek(), numberOfDay);

        return numberOfDay;
    }

    private String convertToCapitalForm(String string) {
        final String finalForm;

        if (Objects.isNull(string) || string.length() == 0) {
            LOGGER.warn("Illegal argument received.");
            throw new IllegalArgumentException();
        }

        finalForm = string.charAt(0) + string.substring(1).toLowerCase();
        LOGGER.info("Converting the name of the day to the proper format.");
        LOGGER.debug("Converting to proper format, the final form is: {}", finalForm);

        return finalForm;
    }

    private String extractPrice() {
        final String selector = "#hetimenu > div > div > div > div > div:nth-child(7) > div > div > div.wpb_text_column.wpb_content_element > div";
        String price = "";

        try {
            price = Jsoup.connect(url).get().select(selector).text();
            LOGGER.info("Extracting price from site.");
            LOGGER.debug("Extracting information from element: {}, and converting to text: {}", selector, price);
        } catch (IOException e) {
            LOGGER.warn("Could not download the site.");
            System.out.printf("Information not found on the %s site", url);
        }

        return price;
    }
}
