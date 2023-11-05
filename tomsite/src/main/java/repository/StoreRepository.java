package repository;

import interfaces.UsingStore;
import models.Product;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class StoreRepository implements UsingStore {
    HashMap<String, String> productsName = new HashMap<>();
    HashMap<String, String> productsPrice = new HashMap<>();
    HashMap<String, String> productsDescription = new HashMap<>();
    HashMap<String, String> productsImgs = new HashMap<>();
    List<String> prod_tags = new ArrayList<>();
    HashMap<String, String> date  = new HashMap<>();


    public void saveProd(String name, String tag, String price, String desc, String date) {
        try(FileWriter writer = new FileWriter("C:\\Users\\MSI\\Desktop\\tomsite\\src\\main\\java\\store_data\\name.txt", UTF_8, true))
        {
            writer.append('\n');
            writer.write(name);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        try(FileWriter writer = new FileWriter("C:\\Users\\MSI\\Desktop\\tomsite\\src\\main\\java\\store_data\\tag.txt", UTF_8, true))
        {
            writer.append('\n');
            writer.write(tag);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        try(FileWriter writer = new FileWriter("C:\\Users\\MSI\\Desktop\\tomsite\\src\\main\\java\\store_data\\price.txt", UTF_8, true))
        {
            writer.append('\n');
            writer.write(price);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        try(FileWriter writer = new FileWriter("C:\\Users\\MSI\\Desktop\\tomsite\\src\\main\\java\\store_data\\description.txt", UTF_8, true))
        {
            writer.append('\n');
            writer.write(desc);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        try(FileWriter writer = new FileWriter("C:\\Users\\MSI\\Desktop\\tomsite\\src\\main\\java\\store_data\\date.txt", UTF_8, true))
        {
            writer.append('\n');
            writer.write(date);
            writer.flush();
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }

        try(FileWriter writer = new FileWriter("C:\\Users\\MSI\\Desktop\\tomsite\\src\\main\\java\\store_data\\img_source.txt", UTF_8, true))
        {
            writer.append('\n');
            writer.write("img/store_main/" + tag + "_" + 1 + ".jpg");
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

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

    @Override
    public List<Product> getAllProduct() throws IOException {
        List<Product> products = new ArrayList<>();

        productsName = getData("name");
        productsPrice = getData("price");
        productsDescription = getData("description");
        productsImgs = getData("img_source");
        date = getData("date");
        prod_tags = getTags();

        Product product;

        for (String tag : prod_tags) {
            product = Product.builder()
                    .tag(tag)
                    .name(productsName.get(tag))
                    .price(productsPrice.get(tag))
                    .description(productsDescription.get(tag))
                    .imgSource(productsImgs.get(tag))
                    .date(date.get(tag))
                    .build();
            products.add(product);
        }

        return products;
    }

    public HashMap<String, String> getData(String filename) throws IOException {
        File txt = new File("C:\\Users\\MSI\\Desktop\\tomsite\\src\\main\\java\\store_data\\" + filename +".txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(txt.toPath()), StandardCharsets.UTF_8));
        HashMap<String, String> data = new HashMap<>();
        prod_tags = getTags();

        String line;
        int i = 0;
        while((line = reader.readLine()) != null) {
            data.put(prod_tags.get(i), line);
            i++;
        }
        reader.close();

        return data;
    }

    public List<String> getTags() throws IOException {
        File txt = new File("C:\\Users\\MSI\\Desktop\\tomsite\\src\\main\\java\\store_data\\tag.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(txt.toPath()), StandardCharsets.UTF_8));
        List<String> tags = new ArrayList<>();

        String line;
        int i = 0;
        while((line = reader.readLine()) != null) {
            tags.add(i,line);
            i++;
        }
        reader.close();

        return tags;
    }

}
