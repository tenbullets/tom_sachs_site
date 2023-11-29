<%@ page import="models.Order" %>
<%@ page import="models.Product" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;700&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=application.getContextPath() %>/css/orders.css" >
    <link rel="shortcut icon" href="img/icon.png" />
    <title>Магазин</title>
</head>

<body>

<header class="header">
    <div class="container_1" id="content">
        <div class="header_inner">
            <a href="http://localhost:8080/tomsite_war/index.jsp">
                <img class="header_logo" src="img/other/logo.png" alt="">
            </a>
        </div>

        <nav class="nav" id="nav">
            <a class="nav_link" href="jsp/exhibitions.jsp" data-scroll="#">Выставки</a>
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
            <div class="logo">
                <h1 class="logo_text">Заказы</h1>
            </div>
        </div>
    </div>

    <div class="basic">
        <div class="container_1">
            <div class="basic_inner">
                <%
                    Order order = (Order) request.getAttribute("order");
                    List<Product> products = (List<Product>) request.getAttribute("prod");
                %>
                <div class="info">
                    <h3 class="name"><%=order.getUniqueID()%></h3>
                    <h3 class="date"><%=order.getDate()%></h3>
                    <h3 class="status"><%=order.getStatus()%></h3>
                    <h3 class="price"><%=order.getPrice()%> $</h3>
                </div>
                
                <table>
                    <tr class="str">
                        <th>Товар</th>
                        <th class="tc">Цена</th>
                    </tr>

                    <%
                        for (int i = 0; i < products.size(); i++) {

                    %>

                    <tr class="str">
                        <td>
                            <form action="gotoProd" method="GET">
                                <input type="submit" class="goto" value="<%=products.get(i).getName()%>">
                                <input type="hidden" name="htmlContent" value="<%=products.get(i).getTag()%>">
                            </form>
                        </td>

                        <td class="tc"><%=products.get(i).getPrice()%> $</td>
                    </tr>

                    <%
                        }
                    %>

                </table>

                
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
                <a class="footer_main_text" href="https://tenbullets.com/">tenbullets.com</a>
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