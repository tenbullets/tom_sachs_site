package repository;

import interfaces.BucketCookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class BucketCookieJdbc implements BucketCookie {

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
}
