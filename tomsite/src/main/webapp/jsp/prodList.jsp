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
    <link rel="stylesheet" type="text/css" href="<%=application.getContextPath() %>/css/prodList.css" >
    <link rel="shortcut icon" href="img/other/icon.png" />
    <title>Сервис</title>
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
            <div class="logo">
                <h1 class="logo_text">Товары</h1>
            </div>
        </div>
    </div>


    <div class="basic">
        <div class="container_1">
            <div class="basic_inner">
                <h1 class="title">Список доступных товаров</h1>
                <table>
                    <tr>
                        <th class="custom_c">№</th>
                        <th>Название</th>
                        <th>Тэг</th>
                        <th class="custom_c">Цена</th>
                        <th class="custom_c">Дата добавления</th>
                        <th class="custom_c">На складе</th>
                        <th class="custom_c">Действие</th>
                    </tr>

                    <%
                        List<Product> products = (List<Product>) request.getAttribute("prodList");
                        for ( int i = 0; i < products.size(); i++) {
                            int counter = i + 1;
                    %>
                    <tr>
                        <td class="custom_c"><%=counter%></td>
                        <td><%=products.get(i).getName()%></td>
                        <td><%=products.get(i).getTag()%></td>
                        <td class="custom_c"><%=products.get(i).getPrice()%> $</td>
                        <td class="custom_c"><%=products.get(i).getDate()%></td>
                        <td class="custom_c"><%=products.get(i).getCount()%></td>
                        <td>
                            <form action="delProd" method="GET">
                                <input type="submit" class="del" value="Удалить">
                                <input type="hidden" name="prodTag" value="<%=products.get(i).getTag()%>">
                            </form>
                        </td>
                    </tr>
                        <%  }
                        %>
                </table>
                <form action="addProd" method="GET">
                    <button>Добавить товар</button>
                </form>

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