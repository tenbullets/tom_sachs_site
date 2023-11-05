<%@ page import="java.util.List" %>
<%@ page import="models.Product" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;700&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=application.getContextPath() %>/css/store.css" >
    <link rel="shortcut icon" href="../img/icon.png" />
    <title>Магазин</title>
</head>

<body>

<header class="header">
    <div class="container_1">
        <div class="header_inner">
            <a href="http://localhost:8080/tomsite_war/index.jsp">
                <img class="header_logo" src="../img/logo.png" alt="">
            </a>
        </div>

        <nav class="nav" id="nav">
            <a class="nav_link" href="../exhibitions.html" data-scroll="#">Выставки</a>
            <a class="nav_link" href="../bio.html" data-scroll="#">Биография</a>
            <a class="nav_link_2" href="jsp/store.jsp"  data-scroll="#">Магазин</a>
            <a class="nav_link" href="../contacts.html" data-scroll="#">Контакты</a>
            <a class="nav_link" href="../log_or_reg.html" data-scroll="#">Аккаунт</a>
        </nav>

    </div>
</header>
    
    <div class="intro">
        <div class="container_1">
            <div class="pre_logo"></div>
            <div class="logo">
                <h1 class="logo_text">Магазин</h1>
            </div>
            
        </div>
    </div>

    <%
        List<Product> products = (List<Product>) config.getServletContext().getAttribute("products");
    %>

    <div class="store">
        <div class="container_1">
            <div class="store_inner">
                <%
                    for(int i = 0; i < products.size(); i++) {

                %>

                <div class="block">
                    <div class="change-photos">
                        <img class="block_img" src="../<%=products.get(i).getImgSource()%>" alt="">

<%--                        <div class="change-photo">--%>
<%--                            <img class="block_img" src="../<%=products.get(i).getImgSource()%>" alt="">--%>
<%--                        </div>--%>
<%--                        <div class="change-photo">--%>
<%--                            <img class="block_img" src="../img/store_main/bag_2.jpg" alt="">--%>
<%--                        </div>--%>

                    </div>
                    <form action="${pageContext.servletContext.contextPath}/gotoProd" method="GET">
                        <input type="submit" class="product" value="<%=products.get(i).getName()%>">
                        <input type="hidden" name="htmlContent" value="<%=products.get(i).getTag()%>">
                        <p class="price"><%=products.get(i).getPrice()%> $</p>
                    </form>

                </div>

                <%
                    }
                %>

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
                <img class="block_2_img" src="../img/footer_img_1.jpg" alt="">
            </div>
        </div>

    </div>
</footer>

</body>
</html>