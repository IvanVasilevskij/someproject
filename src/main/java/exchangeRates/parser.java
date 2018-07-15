package exchangeRates;

import com.sun.xml.internal.ws.server.ServerRtException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class parser{
    private static Document getPage() {
        String url = "http://www.cbr.ru/";
        try {
            return Jsoup.parse(new URL(url), 5000);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Pattern patternDay = Pattern.compile("\\d\\s\\d{2}\\.\\d{2}\\.\\d{4}");
    private static Pattern patternDollar = Pattern.compile("\\d{2}\\,\\d{4}");

    private static String parseDayFtomString(String stringDay) {
        Matcher matcher = patternDay.matcher(stringDay);
        if (matcher.find()) {
            return matcher.group().substring(2);
        } else return null;
    }
    private static String parseDollarExchangeFtomString(String dollarExchange) {
        Matcher matcher = patternDollar.matcher(dollarExchange);
        if (matcher.find()) {
            return matcher.group();
        } else return null;
    }

    public static void main(String[] args) {
        Document page = getPage();
        assert page != null;
        Element widgetExchange = page.select("div[id=widget_exchange]").first();
        Element dayInfo = widgetExchange.select("tr").first();
        String actualDate = parseDayFtomString(dayInfo.text()); //получаем актуальную дату с сайта
        Elements exchangeRateInfo = widgetExchange.select("div[class=w_data_wrap");
        String dollarRateInfo = parseDollarExchangeFtomString(exchangeRateInfo.text());

        Formatter resultData = new Formatter();
        resultData.format("%s %20s %s %11s %s", "date:", actualDate, "\ndollarRate:", dollarRateInfo, "rub");
        System.out.println(resultData);
    }
}
