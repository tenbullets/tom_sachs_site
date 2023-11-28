package repository;

import interfaces.ExhRepository;
import models.Exh;
import models.Product;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExhRepositoryJdbc implements ExhRepository {

    private static final String EXH = "select * from exhibitions";
    private final DataSource dataSource;

    public ExhRepositoryJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Exh getExh(String tag) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(EXH);

            while (resultSet.next()) {
                Exh exh = Exh.builder()
                        .tag(resultSet.getString("tag"))
                        .name(resultSet.getString("name"))
                        .place(resultSet.getString("place"))
                        .eventDates(resultSet.getString("eventDates"))
                        .s_description(resultSet.getString("s_description"))
                        .description(resultSet.getString("description"))
                        .caps(resultSet.getString("caps"))
                        .date(resultSet.getString("date"))
                        .imgsSource(resultSet.getString("imgs_source"))
                        .build();

                if(exh.getTag().equals(tag)) return exh;
            }
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return null;
    }

    @Override
    public List<Exh> getExhList() throws IOException {
        List<Exh> list = new ArrayList<>();

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(EXH);

            while (resultSet.next()) {
                Exh exh = Exh.builder()
                        .tag(resultSet.getString("tag"))
                        .name(resultSet.getString("name"))
                        .place(resultSet.getString("place"))
                        .eventDates(resultSet.getString("eventDates"))
                        .s_description(resultSet.getString("s_description"))
                        .description(resultSet.getString("description"))
                        .caps(resultSet.getString("caps"))
                        .date(resultSet.getString("date"))
                        .imgsSource(resultSet.getString("imgs_source"))
                        .build();
                list.add(exh);
            }
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return list;
    }

    @Override
    public void saveExh(String name, String tag, String place, String date, String eventDates, String sDesc, String desc, List<String> caps, List<Part> imgs) throws IOException {
        String imgSaveSource = "C://Users/MSI/Desktop/tomsite/src/main/webapp/img/exh_imgs/" + tag + "_imgs" + "/";
        String imgsDataSource = "img/exh_imgs/" + tag + "_imgs";

        StringBuilder capsStr = new StringBuilder();
        for (int i = 0; i < caps.size(); i++) {
            capsStr.append(caps.get(i));
            if(!(i == caps.size() - 1)) capsStr.append("/");
        }


        try {
            Connection conn = dataSource.getConnection();

            String sql = "INSERT INTO exhibitions (tag, name, place, date, eventDates, s_description, description, caps, imgs_source) Values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, tag);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, place);
            preparedStatement.setString(5, eventDates);
            preparedStatement.setString(6, sDesc);
            preparedStatement.setString(7, desc);
            preparedStatement.setString(8, capsStr.toString());
            preparedStatement.setString(4, date);
            preparedStatement.setString(9, imgsDataSource);

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
    public boolean findExh(String tag) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(EXH);

            while (resultSet.next()) {
                Exh exh = Exh.builder()
                        .tag((resultSet.getString("tag")))
                        .build();

                if (exh.getTag().equals(tag)) return true;
            }

            connection.close();
            return false;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delExh(String tag, String imgsSource) {
        String source = "C://Users/MSI/Desktop/tomsite/src/main/webapp/img/exh_imgs/" + tag + "_imgs";
        File file = new File(source);

        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = dataSource.getConnection();

            String SQL = "DELETE FROM exhibitions WHERE tag = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, tag);
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public String getImgsSource(String tag) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(EXH);

            while (resultSet.next()) {
                Exh exh = Exh.builder()
                        .tag((resultSet.getString("tag")))
                        .imgsSource(resultSet.getString("imgs_source"))
                        .build();

                if(exh.getTag().equals(tag)) return exh.getImgsSource();
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
    public List<Exh> getExhUpdate(List<Exh> exhList) {
        List<Exh> exhs = new ArrayList<>();

        Date maxDate = new Date(1999, 1, 1);
        Date reset = new Date(1999, 1, 1);

        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < exhList.size(); i++) {
                String strDate = exhList.get(i).getDate();
                Date date = getDate(strDate);
                int result = date.compareTo(maxDate);

                if(result > 0) maxDate = date;
            }

            for (int i = 0; i < exhList.size(); i++) {
                String strDate = exhList.get(i).getDate();
                Date date = getDate(strDate);
                int result = date.compareTo(maxDate);

                if(result == 0) {
                    exhs.add(exhList.get(i));
                    exhList.remove(i);
                    break;
                }
            }
            maxDate = reset;
        }

        return exhs;
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
    public List<String> getCaps(String tag) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(EXH);

            while (resultSet.next()) {
                Exh exh = Exh.builder()
                        .tag((resultSet.getString("tag")))
                        .caps(resultSet.getString("caps"))
                        .build();

                if(exh.getTag().equals(tag)) {
                    String[] cArr = exh.getCaps().split("/");
                    return new ArrayList<>(Arrays.asList(cArr));
                }
            }
            connection.close();
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
