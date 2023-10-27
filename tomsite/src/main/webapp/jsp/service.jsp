<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;700&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=application.getContextPath() %>/css/service.css" >
    <link rel="shortcut icon" href="img/icon.png" />
    <title>Сервис</title>
</head>

<body>

<header class="header">
    <div class="container_1">
        <div class="header_inner">
            <a href="http://localhost:8080/tomsite_war/index.jsp">
                <img class="header_logo" src="img/logo.png" alt="">
            </a>
        </div>

        <nav class="nav" id="nav">
            <a class="nav_link" href="exhibitions.html" data-scroll="#">Выставки</a>
            <a class="nav_link" href="bio.html" data-scroll="#">Биография</a>
            <a class="nav_link" href="store.html"  data-scroll="#">Магазин</a>
            <a class="nav_link" href="contacts.html" data-scroll="#">Контакты</a>
            <a class="nav_link" href="log_or_reg.html" data-scroll="#">Аккаунт</a>
        </nav>

    </div>
</header>

<div class="intro">
    <div class="container_1">
        <div class="pre_logo"></div>
        <div class="logo">
            <h1 class="logo_text">Страничка Админа</h1>
        </div>
    </div>
</div>

<div class="basic">
    <div class="container_1">
        <div class="basic_inner">

            <div class="block">
                <% String name = (String) request.getAttribute("adminName");%>
                <h1 class="username"><%=name%></h1>
                <% String email = (String) request.getAttribute("adminEmail");%>
                <h2 class="email"><%=email%></h2>

                <form action="users" method="POST">
                    <button class="goto">Пользователи</button>
                </form>

                <form action="adminReg" method="GET">
                    <button class="goto">Предоставить доступ</button>
                </form>

                <form action="setProd" method="GET">
                    <button class="goto">+ Товар</button>
                </form>

                <form action="exitPage" method="POST">
                    <button class="goto">Выйти</button>
                </form>

            </div>

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
                <img class="block_2_img" src="img/footer_img_1.jpg" alt="">
            </div>
        </div>

    </div>
</footer>

</body>
</html>