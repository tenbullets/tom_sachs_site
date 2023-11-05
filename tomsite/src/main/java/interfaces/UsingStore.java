package interfaces;

import models.Product;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface UsingStore {
    Product getProduct(String tag) throws IOException;
    HashMap<String, String> getData(String filename) throws IOException;
    List<Product> getAllProduct() throws IOException;
    void saveProd(String name, String tag, String price, String desc, String date);
    List<String> getTags() throws IOException;

}
