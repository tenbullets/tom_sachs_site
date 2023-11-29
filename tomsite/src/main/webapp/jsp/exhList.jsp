<%@ page import="java.util.List" %>
<%@ page import="models.Exh" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;700&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=application.getContextPath() %>/css/exhList.css" >
    <link rel="shortcut icon" href="img/other/icon.png" />
    <title>Сервис</title>
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
            <div class="pre_logo" id="point"></div>
            <div class="logo">
                <h1 class="logo_text">Выставки</h1>
            </div>
        </div>
    </div>


    <div class="basic">
        <div class="container_1">
            <div class="basic_inner">
                <h1 class="title">Список выставок</h1>
                <table>
                    <tr>
                        <th class="custom_c">№</th>
                        <th>Название</th>
                        <th>Тэг</th>
                        <th class="custom_c">Дата добавления</th>
                        <th class="custom_c">Действие</th>
                    </tr>

                    <%
                        List<Exh> list = (List<Exh>) request.getAttribute("exhList");
                        for ( int i = 0; i < list.size(); i++) {
                            int counter = i + 1;
                    %>

                    <tr>
                        <td class="custom_c"><%=counter%></td>
                        <td>
                            <form action="${pageContext.servletContext.contextPath}/gotoExh" method="GET">
                                <input type="submit" class="goto" value="<%=list.get(i).getName()%>">
                                <input type="hidden" name="tag" value="<%=list.get(i).getTag()%>">
                            </form>
                        </td>
                        <td><%=list.get(i).getTag()%></td>
                        <td class="custom_c"><%=list.get(i).getDate()%></td>
                        <td>
                            <form action="delExh" method="GET">
                                <input type="submit" class="del" value="Удалить">
                                <input type="hidden" name="tag" value="<%=list.get(i).getTag()%>">
                            </form>
                        </td>
                    </tr>

                    <%
                        }
                    %>

                </table>

                <form action="addExh" method="GET" class="add">
                    <button>Разместить выставку</button>
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

<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.4.1.min.js"></script>
<script src="js/header.js"></script>

</body>
</html>