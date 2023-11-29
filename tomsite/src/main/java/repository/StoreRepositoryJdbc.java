package repository;

import interfaces.StoreRepository;
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

public class StoreRepositoryJdbc implements StoreRepository {
    private static final String PROD = "select * from products";
    private final DataSource dataSource;

    public StoreRepositoryJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveProd(String name, String tag, String price, String desc, String date, List<Part> imgs, String count) throws IOException {
        String imgSaveSource = "C://Users/MSI/Desktop/tomsite/src/main/webapp/img/prod_imgs/" + tag + "_imgs" + "/";
        String imgsDataSource = "img/prod_imgs/" + tag + "_imgs";

        try {
            Connection conn = dataSource.getConnection();

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
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        new File(imgSaveSource).mkdir();

        for (int i = 0; i < imgs.size(); i++) {
            String source = imgSaveSource + tag + "_" + i + ".jpg";
            Files.copy(imgs.get(i).getInputStream(), Paths.get(source));
        }
    }

    @Override
    public void delProduct(String tag, String imgsSource) {
        String source = "C://Users/MSI/Desktop/tomsite/src/main/webapp/img/prod_imgs/" + tag + "_imgs";
        File file = new File(source);

        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = dataSource.getConnection();

            String SQL = "DELETE FROM products WHERE tag = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, tag);
            preparedStatement.executeUpdate();

            connection.close();
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

                if (product.getTag().equals(tag)) return product;

            }

            connection.close();
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

            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return products;
    }

    @Override
    public boolean findProduct(String tag) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(PROD);

            while (resultSet.next()) {
                Product product = Product.builder()
                        .tag(resultSet.getString("tag"))
                        .build();

                if (product.getTag().equals(tag)) return true;
            }

            connection.close();
            return false;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
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

                if(product.getTag().equals(tag)) return product.getImgSource();
            }

            connection.close();
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<String> getImgs(String tag, String imgsSource) {
        File folder = new File("C://Users/MSI/Desktop/tomsite/src/main/webapp/" + imgsSource);

        List<String> imgs = new ArrayList<>();

        int i = 0;
        for (final File fileEntry : folder.listFiles()) {
            String name = tag + "_" + i + ".jpg";
            if(name.equals(fileEntry.getName())) imgs.add(imgsSource + "/" + name);
            i++;
        }

        if(i < 4) for(int j = 0; j < (4-i); j++) imgs.add("img/default_img/default.jpg");

        return imgs;
    }

    @Override
    public List<Product> getProdUpdate(List<Product> prodList) {
        List<Product> prod = new ArrayList<>();

        Date maxDate = new Date(1999, 1, 1);
        Date reset = new Date(1999, 1, 1);

        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < prodList.size(); i++) {
                String strDate = prodList.get(i).getDate();
                Date date = getDate(strDate);
                int result = date.compareTo(maxDate);

                if(result > 0) maxDate = date;


            }

            for (int i = 0; i < prodList.size(); i++) {
                String strDate = prodList.get(i).getDate();
                Date date = getDate(strDate);
                int result = date.compareTo(maxDate);

                if(result == 0) {
                    prod.add(prodList.get(i));
                    prodList.remove(i);
                    break;
                }
            }
            maxDate = reset;
        }

        return prod;
    }

    @Override
    public Date getDate(String date) {
        String[] arrDate = date.split("\\.");
        int day = Integer.parseInt(arrDate[0]);
        int month = Integer.parseInt(arrDate[1]);
        int year = Integer.parseInt(arrDate[2]);

        return new Date(year, month, day);
    }

    @Override
    public void addProdToCart(String tag) {
        int count = 0;
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(PROD);

            while(resultSet.next()) {
                Product prod = Product.builder()
                        .tag(resultSet.getString("tag"))
                        .count(Integer.parseInt(resultSet.getString("count")))
                        .build();
                if(prod.getTag().equals(tag)) {
                    count = prod.getCount() - 1;
                    break;
                }

            }
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }


        try {Connection connection = dataSource.getConnection();

            String SQL ="UPDATE products SET count = ? WHERE tag = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, count);
            preparedStatement.setString(2, tag);
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delProdFromCart(String tag) {
        int count = 0;
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(PROD);

            while(resultSet.next()) {
                Product prod = Product.builder()
                        .tag(resultSet.getString("tag"))
                        .count(Integer.parseInt(resultSet.getString("count")))
                        .build();
                if(prod.getTag().equals(tag)) {
                    count = prod.getCount();
                    break;
                }
            }

            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        count += 1;

        try {
            Connection connection = dataSource.getConnection();

            String SQL ="UPDATE products SET count = ? WHERE tag = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, count);
            preparedStatement.setString(2, tag);
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void setCount(String tag, int count) {
        int originalCount = 0;
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(PROD);

            while(resultSet.next()) {
                Product prod = Product.builder()
                        .tag(resultSet.getString("tag"))
                        .count(Integer.parseInt(resultSet.getString("count")))
                        .build();
                if(prod.getTag().equals(tag)) {
                    originalCount = prod.getCount();
                    break;
                }
            }

            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        originalCount += count;

        try {
            Connection connection = dataSource.getConnection();

            String SQL ="UPDATE products SET count = ? WHERE tag = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, originalCount);
            preparedStatement.setString(2, tag);
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<String> getProdTags() {
        List<String> tags = new ArrayList<>();

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(PROD);

            while(resultSet.next()) {
                Product prod = Product.builder()
                        .tag(resultSet.getString("tag"))
                        .count(Integer.parseInt(resultSet.getString("count")))
                        .build();
                tags.add(prod.getTag());
            }

            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return tags;
    }

}