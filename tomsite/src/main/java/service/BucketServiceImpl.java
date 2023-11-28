package service;

import interfaces.BucketService;
import interfaces.StoreRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BucketServiceImpl implements BucketService {

    @Override
    public boolean cookieIsReal(Cookie[] cookie, String name) {
        for (Cookie value : cookie) {
            if (value.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getProd(Cookie[] cookie) {
        for (Cookie value : cookie) {
            if (value.getName().equals("prod")) return value.getValue();
        }
        return null;
    }

    @Override
    public int getCountOfProducts(String products) {
        int count = 0;
        for (char element : products.toCharArray()){
            if (element == '_') count++;
        }
        if(products.charAt(0) == '_') return count;

        return count + 1;
    }

    @Override
    public String delProd(String[] products, String tag) {
        for (int i = 0; i < products.length; i++) {
            if(products[i].equals(tag)) {
                products[i] = "";
                break;
            }
        }

        StringBuilder result = new StringBuilder();
        for (String product : products) {
            if (!product.isEmpty()) result.append("_").append(product);
        }
        return result.toString();
    }

    @Override
    public String getCount(Cookie[] cookie) {
        for (Cookie value : cookie) {
            if (value.getName().equals("count")) return value.getValue();
        }
        return null;
    }

    @Override
    public void addCookie(String name, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        response.addCookie(cookie);
        cookie.setMaxAge(3600);
    }

    @Override
    public void delAllBucketCookie(HttpServletResponse response) {
        Cookie prodCookie = new Cookie("prod", "");
        Cookie countCookie = new Cookie("count", "");
        response.addCookie(prodCookie);
        response.addCookie(countCookie);
        prodCookie.setMaxAge(0);
        countCookie.setMaxAge(0);
    }

    @Override
    public int getOrderPrice(String prods, StoreRepository repository) {
        String[] tags = prods.split("_");
        List<String> prod = new ArrayList<>(Arrays.asList(tags));

        for (int i = 0; i < prod.size() ; i++) {
            if(prod.get(i).isEmpty()) prod.remove(i);
        }

        int price = 0;
        for (String s : prod) {
            int prodPrice = repository.getProduct(s).getPrice();
            price += prodPrice;
        }

        return price;
    }

    @Override
    public void returnOriginalCount(String tags, List<String> prodTags, StoreRepository repository) {
        for (String prodTag : prodTags) {
            int count = tags.split(prodTag, -1).length - 1;
            if (count != 0) repository.setCount(prodTag, count);
        }
    }
}
