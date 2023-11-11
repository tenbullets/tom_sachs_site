<%@ page import="models.Product" %>
<%@ page import="repository.StoreRepository" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;700&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=application.getContextPath() %>/css/bucket.css" >
    <link rel="shortcut icon" href="img/other/icon.png" />
    <title>Том Сакс</title>
</head>

<body>

<header class="header">
    <div class="container_1">
        <div class="header_inner">
            <a href="http://localhost:8080/tomsite_war/index.jsp">
                <img class="header_logo" src="img/other/logo.png" alt="">
            </a>
        </div>

        <nav class="nav" id="nav">
            <a class="nav_link" href="exhibitions.html" data-scroll="#">Выставки</a>
            <a class="nav_link" href="bio.html" data-scroll="#">Биография</a>
            <a class="nav_link" href="jsp/store.jsp" data-scroll="#">Магазин</a>
            <a class="nav_link" href="contacts.html" data-scroll="#">Контакты</a>
            <a class="nav_link" href="log_or_reg.html" data-scroll="#">Аккаунт</a>
        </nav>
    </div>
</header>

    <div class="intro">
        <div class="container_1">
            <div class="pre_logo"></div>

            <div class="intro_inner">
                <h1 class="title">Корзина</h1>
            </div>
        </div>
    </div>

    <div class="basic">
        <div class="container_1">

            <% String[] prod = (String[]) request.getAttribute("products");
                StoreRepository storeRepository = (StoreRepository) request.getAttribute("storeRep");
                int price = 0;
                for (String s : prod) {
                    if (!s.isEmpty()) {
                        Product product = storeRepository.getProduct(s);
                        int number = product.getPrice();
                        price += number;

                        List<String> imgs = storeRepository.getImgs(product.getTag(), storeRepository.getImgsSource(product.getTag()));
            %>

            <div class="basic_inner">
                <div class="img_block">
                    <img class="img" src="<%=imgs.get(0)%>" alt="">
                </div>
                <div class="block_1">
                    <form action = "gotoProd" method = "GET">
                        <input type = "submit" class="product" value = "<%=product.getName()%>">
                        <input type = "hidden" name = "htmlContent" value = "<%=product.getTag()%>">
                        <p class="price" ><%=product.getPrice()%> $</p>
                    </form>
                </div>
                <div class="block_2">
                    <form action = "del" method = "GET">
                        <input type = "submit" class="del" value = "Удалить">
                        <input type = "hidden" name = "delProd" value = "<%=product.getTag()%>">
                    </form>
                </div>
            </div>

            <%
                    }
                }
            %>

            <div class="basic_inner" style="border-bottom: none">
                <div class="block_3">
                    <h1 class="result">Итого: <%=price%> $</h1>
                </div>
            </div>

        </div>
    </div>

    <footer class="footer">
        <div class="container_1">

            <div class="footer_inner">
                <div class="footer_block">
                    <p class="footer_title_text">Том Сакс</p>
                    <a class="footer_main_text" href="https://twitter.com/tom_sachs">twitter</a>
                    <a class="footer_main_text" href="https://www.youtube.com/user/tomsachsmovies">youtube</a>
                    <a class="footer_main_text" href="https://www.instagram.com/tomsachs/">instagram</a>
                    <a class="footer_main_text" href="http://tenbullets.com/">tenbullets.com</a>
                </div>
                <div class="footer_block">
                    <p class="footer_title_text">Автор Сайта</p>
                    <a class="footer_main_text" href="https://t.me/zhertvapropagandynolana">telegram</a>
                    <a class="footer_main_text" href="https://instagram.com/zhertvapropagandynolana?igshid=NTc4MTIwNjQ2YQ==">instagram</a>
                </div>

                <div class="footer_block_2">
                    <img class="block_2_img" src="img/other/footer_img_1.jpg" alt="">
                </div>
            </div>

        </div>
    </footer>

</body>
</html>