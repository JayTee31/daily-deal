package jaytee31.daily.deal.melange.restaurant;

import jaytee31.daily.deal.date.CurrentTime;
import jaytee31.daily.deal.date.Day;
import jaytee31.daily.deal.download.PageDownloader;
import jaytee31.daily.deal.melange.menu.MelangeDailyMenu;
import jaytee31.daily.deal.menu.DailyMenu;
import jaytee31.daily.deal.menu.DailyMenuStatus;
import jaytee31.daily.deal.restaurant.Restaurant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Objects;

public class Melange implements Restaurant<MelangeDailyMenu> {
    public final static String RESTAURANT_NAME = "Melange";
    private final static String URL = "https://melangekavehaz.hu/en/gastronomy-home/";
    private final static Logger LOGGER = LoggerFactory.getLogger(Melange.class);

    private final PageDownloader pageDownloader;

    public Melange(PageDownloader pageDownloader) {
        this.pageDownloader = pageDownloader;
        LOGGER.info("New instance was created.");
    }

    @Override
    public DailyMenu<MelangeDailyMenu> getDailyMenu(final CurrentTime currentTime) {
        final String allInformation = extractInformationFromSite();
        final DailyMenuStatus status = extractStatusFromInformation(allInformation, currentTime);

        if (!status.equals(DailyMenuStatus.AVAILABLE)) {
            return new DailyMenu<>(currentTime, RESTAURANT_NAME, status, null);
        }

        MelangeDailyMenu melangeMenu = new MelangeDailyMenu(
                extractSoup(allInformation, currentTime),
                extractCourseA(allInformation, currentTime),
                extractCourseB(allInformation, currentTime),
                extractPrice(allInformation));

        return new DailyMenu<>(currentTime, RESTAURANT_NAME, status, melangeMenu);
    }

    private String extractInformationFromSite() {
        final String selector = "#hetimenu";
        String information = "";
        try {
            final String pageSource = pageDownloader.downloadPage(URL);
            final Document document = Jsoup.parse(pageSource);
            information = document.select(selector).text();
            LOGGER.info("Extracting information from site.");
            LOGGER.debug("Extracting information from element: {}, and converting to text: {}.", selector, information);
        } catch (IOException e) {
            LOGGER.warn("Could not download information from the site.");
        }

        return information;
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

    private String extractDay(final String allInformation, final CurrentTime currentTime) {
        final String nextDay = Day.getHungarianName(currentTime.getNextDaysName());

        final int startIndex = allInformation.indexOf(currentTime.getHungarianDayName());
        int endIndex = 0;

        if (currentTime.getNameOfDay().equals(Day.FRIDAY.name())) {
            endIndex = allInformation.indexOf(extractPrice(allInformation));
        } else {
            endIndex = allInformation.indexOf(nextDay);
        }

        return allInformation.substring(startIndex, endIndex);
    }

    private String extractSoup(final String allInformation, final CurrentTime currentTime) {
        final String dailyInformation = extractDay(allInformation, currentTime);
        final int startIndex = dailyInformation.indexOf(currentTime.getHungarianDayName()) + currentTime.getHungarianDayName().length();
        final int endIndex = dailyInformation.indexOf("„A\"");

        return dailyInformation.substring(startIndex, endIndex);
    }

    private String extractCourseA(final String allInformation, final CurrentTime currentTime) {
        final String dailyInformation = extractDay(allInformation, currentTime);
        final int startIndex = dailyInformation.indexOf("„A\"") + "„A\"".length() + 1;
        int endIndex = dailyInformation.indexOf("„B\"");

        if (!dailyInformation.contains("„A\"")) {
            return "A course does not exist.";
        }

        if (!dailyInformation.contains("„B\"")) {
            endIndex = dailyInformation.length();
        }

        return dailyInformation.substring(startIndex, endIndex);
    }

    private String extractCourseB(final String allInformation, final CurrentTime currentTime) {
        final String dailyInformation = extractDay(allInformation, currentTime);
        final int startIndex = dailyInformation.indexOf("„B\"") + "„B\"".length() + 1;
        int endIndex = dailyInformation.length();

        if (!dailyInformation.contains("„B\"")) {
            return "B course does not exist.";
        }

        return dailyInformation.substring(startIndex, endIndex);
    }

    private String extractPrice(final String allInformation) {
        final int startIndex = allInformation.indexOf("Helyben fogyasztva");
        final int endIndex = allInformation.indexOf("Megrendelés");

        return allInformation.substring(startIndex, endIndex);
    }
}
