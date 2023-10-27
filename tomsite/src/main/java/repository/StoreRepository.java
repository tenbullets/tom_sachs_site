package repository;
import interfaces.UsingStore;
import models.Product;

import java.io.*;
import java.util.HashMap;

public class StoreRepository implements UsingStore {
    HashMap<String, String> productsName = new HashMap<>();
    HashMap<String, String> productsPrice = new HashMap<>();
    HashMap<String, String> productsDescription = new HashMap<>();
    HashMap<String, String> productsImgs = new HashMap<>();

    @Override
    public Product getProduct(String tag) throws IOException {
        productsName = getData("name");
        productsPrice = getData("price");
        productsDescription = getData("description");
        productsImgs = getData("img_source");

        Product product;
        product = Product.builder()
                .tag(tag)
                .name(productsName.get(tag))
                .price(productsPrice.get(tag))
                .description(productsDescription.get(tag))
                .imgSource(productsImgs.get(tag))
                .build();

        return product;
    }

    public HashMap<String, String> getData(String filename) throws IOException {
        File txt = new File("C:\\Users\\MSI\\Desktop\\tomsite\\src\\main\\java\\store_data\\" + filename +".txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(txt), "UTF8"));
        String[] tags = {"bag", "pers", "lander", "ipad", "spaceships", "nsstee", "note", "nsptee", "fpack", "yard", "nut"};
        HashMap<String, String> data = new HashMap<>();

        String line;
        int i = 0;
        while((line = reader.readLine()) != null) {
            data.put(tags[i], line);
            i++;
        }
        reader.close();

        return data;
    };

}
