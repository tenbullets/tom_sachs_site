<%@ page import="java.util.List" %>
<%@ page import="models.Order" %>
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
                <h1 class="title">Список заказов</h1>
                <table>
                    <tr class="str">
                        <th>Заказ</th>
                        <th>Статус</th>
                        <th class="tc">Дата</th>
                        <th class="tc">Стоимость</th>
                    </tr>

                    <%
                        List<Order> orders = (List<Order>) request.getAttribute("ordersList");

                        for (int i = 0; i < orders.size(); i++) {

                    %>

                    <tr class="str">
                        <td>
                            <form action="gotoOrder" method="GET">
                                <input type="submit" class="goto" value="<%=orders.get(i).getUniqueID()%>">
                                <input type="hidden" name="uuid" value="<%=orders.get(i).getUniqueID()%>">
                            </form>
                        </td>
                        <td><%=orders.get(i).getStatus()%></td>
                        <td  class="tc"><%=orders.get(i).getDate()%></td>
                        <td  class="tc"><%=orders.get(i).getPrice()%> $</td>
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