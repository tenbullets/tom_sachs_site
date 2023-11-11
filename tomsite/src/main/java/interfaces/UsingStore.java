package interfaces;

import models.Product;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

public interface UsingStore {
    Product getProduct(String tag);
    List<Product> getAllProduct() throws IOException;
    void saveProd(String name, String tag, String price, String desc, String date, List<Part> imgs, String count) throws IOException;
    boolean findProduct(String tag);
    void delProduct(String tag, String imgsSource);
    String getImgsSource(String tag);
    List<String> getImgs(String tag, String imgsSource);

}
