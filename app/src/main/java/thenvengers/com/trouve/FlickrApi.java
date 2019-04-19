package thenvengers.com.trouve;

public class FlickrApi {
    public final String secret = "314eee2b2f4cb395";
    public final String key="6c763bfd86fd486dedcfe54dfe9ce0e8";

    public static String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=6c763bfd86fd486dedcfe54dfe9ce0e8&format=json&nojsoncallback=1";

    public static String getFinalUrl(String searchData) {
        String finalUrl = String.format("%s&text=%s", url, searchData);
        return finalUrl;
    }
}
