package jaytee31.daily.deal.melange.restaurant;

import jaytee31.daily.deal.date.CurrentTime;
import jaytee31.daily.deal.download.PageDownloader;
import jaytee31.daily.deal.melange.menu.MelangeDailyMenu;
import jaytee31.daily.deal.menu.DailyMenu;
import jaytee31.daily.deal.menu.DailyMenuStatus;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MelangeTest {
    @Test()
    public void getDailyMenuShouldReturnTodaysMenuIfItsAvailable() throws IOException {
        // Given
        final CurrentTime currentTime = new CurrentTime(LocalDateTime.of(2022, 1, 4, 6, 9));
        final DailyMenu<MelangeDailyMenu> expected = new DailyMenu<>(
            currentTime,
            Melange.RESTAURANT_NAME,
            DailyMenuStatus.AVAILABLE,
            new MelangeDailyMenu(
                    " Májgaluska leves ",
                    "Csirkés burrito, vitamin saláta ",
                    "Mustáros flekken kapros-juhtúrós galuskával, marinált hagyma ",
                    "Helyben fogyasztva: 1690.-Ft/adag Elvitelre : 1800.-Ft/adag Házhoz szállítva : 1900.-Ft/adag ")
        );
        final Melange systemUnderTest = new Melange(createPageDownloaderOfResource("melange/melange-available.html"));

        // When
        final DailyMenu<MelangeDailyMenu> actual = systemUnderTest.getDailyMenu(currentTime);

        // Then
        assertEquals(expected, actual);
    }

    private PageDownloader createPageDownloaderOfResource(String resourceName) throws IOException {
        final String pageSource = loadPageSourceFromResource(resourceName);

        final PageDownloader pageDownloader = mock(PageDownloader.class);
        when(pageDownloader.downloadPage(any())).thenReturn(pageSource);
        return pageDownloader;
    }

    private String loadPageSourceFromResource(String resourceName) throws IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource(resourceName).getFile());
        return String.join("", Files.readAllLines(file.toPath()));
    }
}