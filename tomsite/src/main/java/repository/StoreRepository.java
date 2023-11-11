package repository;

import interfaces.UsingStore;
import models.Product;

import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;


public class StoreRepository implements UsingStore {

    private static final String PROD = "select * from products";
    private final DataSource dataSource;

    public StoreRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void saveProd(String name, String tag, String price, String desc, String date, List<Part> imgs, String count) throws IOException {
        String imgSaveSource = "C://Users/MSI/Desktop/tomsite/src/main/webapp/img/prod_imgs/" + tag + "_imgs" + "/";
        String imgsDataSource = "img/prod_imgs/" + tag + "_imgs";

        try (Connection conn = dataSource.getConnection()) {

            String sql = "INSERT INTO products (tag, name, price, description, count, date, imgs_source) Values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, tag);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, Integer.parseInt(price));
            preparedStatement.setString(4, desc);
            preparedStatement.setInt(5, Integer.parseInt(count));
            preparedStatement.setString(6, date);
            preparedStatement.setString(7, imgsDataSource);

            preparedStatement.executeUpdate();

            System.out.println( name + " saved");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        new File(imgSaveSource).mkdir();

        for (int i = 0; i < imgs.size(); i++) {
            String source = imgSaveSource + tag + "_" + i + ".jpg";
            Files.copy(imgs.get(i).getInputStream(), Paths.get(source));
            System.out.println("img " + i + " saved");
        }
    }

    public void delProduct(String tag, String imgsSource) {
        String source = "C://Users/MSI/Desktop/tomsite/src/main/webapp/img/prod_imgs/" + tag + "_imgs";
        File file = new File(source);

        try {
            FileUtils.deleteDirectory(file);
            System.out.println("Папка с фото ( " + source + " ) удалена");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Connection connection = dataSource.getConnection()) {

            String SQL = "DELETE FROM products WHERE tag = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, tag);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public Product getProduct(String tag) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(PROD);

            while (resultSet.next()) {
                Product product = Product.builder()
                        .tag(resultSet.getString("tag"))
                        .name(resultSet.getString("name"))
                        .price(Integer.parseInt(resultSet.getString("price")))
                        .description(resultSet.getString("description"))
                        .count(Integer.parseInt(resultSet.getString("count")))
                        .date(resultSet.getString("date"))
                        .imgSource(resultSet.getString("imgs_source"))
                        .build();

                if (product.getTag().equals(tag)) {
                    return product;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public List<Product> getAllProduct() throws IOException {
        List<Product> products = new ArrayList<>();

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(PROD);

            while (resultSet.next()) {
                Product prod = Product.builder()
                        .tag(resultSet.getString("tag"))
                        .name(resultSet.getString("name"))
                        .price(Integer.parseInt(resultSet.getString("price")))
                        .description(resultSet.getString("description"))
                        .count(Integer.parseInt(resultSet.getString("count")))
                        .date(resultSet.getString("date"))
                        .imgSource(resultSet.getString("imgs_source"))
                        .build();

                products.add(prod);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return products;
    }

    public boolean findProduct(String tag) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(PROD);

            while (resultSet.next()) {
                Product product = Product.builder()
                        .tag(resultSet.getString("tag"))
                        .build();

                if (product.getTag().equals(tag)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    public String getImgsSource(String tag) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(PROD);

            while (resultSet.next()) {
                Product product = Product.builder()
                        .tag(resultSet.getString("tag"))
                        .imgSource(resultSet.getString("imgs_source"))
                        .build();

                if (product.getTag().equals(tag)) {
                    return product.getImgSource();
                }
            }
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<String> getImgs(String tag, String imgsSource) {
        File folder = new File(imgsSource);
        List<String> imgs = new ArrayList<>();

        for(int i = 0; i < 4; i++) {
            String name = tag + "_" + i + ".jpg";
            imgs.add(imgsSource + "/" + name);
        }

//        int i = 0;
//        for (final File fileEntry : folder.listFiles()) {
//
//            String name = tag + "_" + i + ".jpg";
//            if(name.equals(fileEntry.getName())) {
//                imgs.add(imgsSource + name);
//                i++;
//            }
//        }

        return imgs;
    }

}
