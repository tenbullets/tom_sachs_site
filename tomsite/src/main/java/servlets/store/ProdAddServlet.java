package servlets.store;

import models.Product;
import repository.StoreRepository;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;


@MultipartConfig
public class ProdAddServlet extends HttpServlet {
    ServletConfig config;
    StoreRepository storeRepository;
    StoreRepository storeRepository1;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        storeRepository = (StoreRepository) config.getServletContext().getAttribute("storeRep");
        storeRepository1 = (StoreRepository) config.getServletContext().getAttribute("storeRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("addProd.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = null, status;

        String name = request.getParameter("name");
        String tag = request.getParameter("tag");
        String price = request.getParameter("price");
        String desc = request.getParameter("desc");
        String count = request.getParameter("count");

        Part img1 = request.getPart("img1");
        Part img2 = request.getPart("img2");
        Part img3 = request.getPart("img3");
        Part img4 = request.getPart("img4");

        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        String date = formatForDateNow.format(dateNow);

        if (
                !name.isEmpty()
                && !count.isEmpty()
                && !price.isEmpty()
                && !desc.isEmpty()
                && !tag.isEmpty()
                && isDigit(price)
                && sizeIsOk(img1, img2, img3, img4)
                && photosAttached(img1, img2, img3, img4)
        ) {

            List<Part> imgs = new ArrayList<>();
            imgs.add(img1);
            imgs.add(img2);
            imgs.add(img3);
            imgs.add(img4);

            storeRepository.saveProd(setFormat(name), tag, price, toHtmlFormat(desc), date, imgs, count);

            List<Product> products;

            try {
                products = storeRepository1.getAllProduct();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ServletContext servletContext = config.getServletContext();
            servletContext.setAttribute("products", products);

            request.getRequestDispatcher("addProd.html").forward(request, response);
        }

        status = "Товар не добавлен";

        if(!isDigit(price)) result = price + " не является числом";
        if(!isDigit(count)) result = count + " не является числом";
        if(name.isEmpty() || price.isEmpty() || desc.isEmpty() || tag.isEmpty() || count.isEmpty()) result = "Вы оставили поля пустыми";
        if(!sizeIsOk(img1, img2, img3, img4)) result = "Размер одного из фото превышает 10 Мбайт";
        if(!photosAttached(img1, img2, img3, img4)) result = "Отсутствует(ют) фото";

        request.setAttribute("resultOfAut", result);
        request.setAttribute("status", status);
        request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
    }

    public String setFormat(String rawText) {
        byte[] text = rawText.getBytes(ISO_8859_1);
        return new String(text, UTF_8);
    }

    public String toHtmlFormat(String rawStr) {
        String before = "<p class=\"description\" style=\"margin-top: 35px;\">";
        String ins = "</p> <p class=\"description\">";
        String after = "</p>";
        
        byte[] text = rawStr.getBytes(ISO_8859_1);
        String desc = new String(text, UTF_8);


        StringBuilder sb = getClearText(desc, before, after);

        return  sb.toString().replaceAll("\\b &  &  &  & \\b|\\B &  &  &  &\\B", ins);
    }

    private static StringBuilder getClearText(String desc, String before, String after) {
        char[] chars = desc.toCharArray();
        List<Character> charList = new ArrayList<>(Arrays.asList(toCharacterArray(chars)));
        List<Character> ch = new ArrayList<>();

        for (Character value : charList) {
            if ((int) value == 13 || (int) value == 10) {
                ch.add(' ');
                ch.add('&');
                ch.add(' ');
            } else {
                ch.add(value);
            }
        }

        StringBuilder sb = new StringBuilder(before);
        for (Character character : ch) sb.append(character);
        sb.append(after);
        return sb;
    }

    public static Character[] toCharacterArray(char[] charArray) {
        Character[] characterArray = new Character[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            characterArray[i] = charArray[i];
        }
        return characterArray;
    }




    public boolean sizeIsOk(Part img1, Part img2, Part img3, Part img4) {
        int lim = 10000000;
        return img1.getSize() <= lim && img2.getSize() <= lim && img3.getSize() <= lim && img4.getSize() <= lim;
    }

    public String saveImg(Part img, String tag, int num) throws IOException {
        String source = "C://Users/MSI/Desktop/tomsite/src/main/webapp/img/store_main/" + tag + "_" + num + ".jpg";
        Files.copy(img.getInputStream(), Paths.get(source));
        return "img/store_main/" + tag + "_" + num + ".jpg";
    }

    public boolean photosAttached(Part img1, Part img2, Part img3, Part img4) {
        return img1.getSize() > 0 && img2.getSize() > 0 && img3.getSize() > 0 && img4.getSize() > 0;
    }

    public static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
