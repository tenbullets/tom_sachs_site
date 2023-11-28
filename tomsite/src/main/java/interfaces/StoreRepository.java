package interfaces;

import models.Product;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public interface StoreRepository {
    Product getProduct(String tag);
    List<Product> getAllProduct() throws IOException;
    void saveProd(String name, String tag, String price, String desc, String date, List<Part> imgs, String count) throws IOException;
    boolean findProduct(String tag);
    void delProduct(String tag, String imgsSource);
    String getImgsSource(String tag);
    List<String> getImgs(String tag, String imgsSource);
    List<Product> getProdUpdate(List<Product> prodList);
    Date getDate(String date);
    void addProdToCart(String tag);
    void delProdFromCart(String tag);
    void setCount(String tag, int count);
    List<String> getProdTags();
}
