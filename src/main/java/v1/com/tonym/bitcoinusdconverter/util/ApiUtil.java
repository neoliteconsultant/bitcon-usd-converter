package v1.com.tonym.bitcoinusdconverter.util;

public class ApiUtil {
    private static final String BASE_URL ="https://api.coindesk.com/v1/bpi/";

    public static String getAbsoluteURL(String relativeUrl){
        return BASE_URL+relativeUrl;
    }
}
