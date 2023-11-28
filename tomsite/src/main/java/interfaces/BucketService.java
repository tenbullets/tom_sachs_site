package interfaces;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface BucketService {
    boolean cookieIsReal(Cookie[] cookie, String name);
    String getProd(Cookie[] cookie);
    int getCountOfProducts(String products);
    String delProd(String[] products, String tag);
    String getCount(Cookie[] cookie);
    void addCookie(String name, String value, HttpServletResponse response);
    void delAllBucketCookie(HttpServletResponse response);
    int getOrderPrice(String prods, StoreRepository repository);
    void returnOriginalCount(String tags, List<String> tagsList, StoreRepository repository);
}
