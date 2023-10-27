package interfaces;

import models.Product;

import java.io.IOException;
import java.util.HashMap;

public interface UsingStore {
    Product getProduct(String tag) throws IOException;
    HashMap<String, String> getData(String filename) throws IOException;

}
