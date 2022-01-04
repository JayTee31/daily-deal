package jaytee31.daily.deal.download;

import java.io.IOException;

public interface PageDownloader {
    String downloadPage(String url) throws IOException;
}
