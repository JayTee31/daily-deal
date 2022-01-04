package jaytee31.daily.deal.download;

import org.jsoup.Jsoup;

import java.io.IOException;

public class JsoupPageDownloader implements PageDownloader {
    @Override
    public String downloadPage(final String url) throws IOException {
        return Jsoup.connect(url).execute().body();
    }
}
