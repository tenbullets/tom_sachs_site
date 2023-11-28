<%@ page import="models.UsersListDTO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;700&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=application.getContextPath() %>/css/userlist.css" >
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
                <a class="nav_link" href="exhibitions.jsp" data-scroll="#">Выставки</a>
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
                <h1 class="logo_text">Пользователи</h1>
            </div>
        </div>
    </div>


    <div class="basic">
        <div class="container_1">
            <div class="basic_inner">
                <h1 class="title">Аккаунты Пользователей</h1>
                <table>
                    <tr class="str">
                        <th>Имя</th>
                        <th>Почта</th>
                        <th>Действие</th>
                    </tr>
                    <%
                        List<UsersListDTO> users = (List<UsersListDTO>) request.getAttribute("users");
                        for ( int i = 0; i < users.size(); i++) {
                    %>
                    <tr class="str">
                        <td><%=users.get(i).getUsername()%></td>
                        <td>
                            <a class="about_link" href="mailto:<%=users.get(i).getEmail()%>"><%=users.get(i).getEmail()%></a>
                        </td>
                        <td>
                            <form action="delUser" method="GET">
                                <input type="submit" class="del" value="Заблокировать">
                                <input type="hidden" name="username" value="<%=users.get(i).getUsername()%>">
                            </form>
                        </td>
                    </tr>

                        <%  }
                        %>
                </table>
                <h1 class="title">Админы</h1>
                <table>
                    <tr class="str">
                        <th>Имя</th>
                        <th>Почта</th>
                    </tr>

                    <%
                        List<UsersListDTO> admins = (List<UsersListDTO>) request.getAttribute("admins");
                        for ( int i = 0; i < admins.size(); i++) {
                    %>

                    <tr class="str">
                        <td><%=admins.get(i).getUsername()%></td>
                        <td>
                            <a class="about_link" href="mailto:<%=admins.get(i).getEmail()%>"><%=admins.get(i).getEmail()%></a>
                        </td>
                    </tr>

                    <%  }
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

    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.4.1.min.js"></script>
    <script src="js/header.js"></script>

</body>
</html>