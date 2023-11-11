<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;700&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=application.getContextPath() %>/css/account.css" >
    <link rel="shortcut icon" href="img/other/icon.png" />
    <title>Личный кабинет</title>
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
            <a class="nav_link_2" href="log_or_reg.html" data-scroll="#">Аккаунт</a>
        </nav>

    </div>
</header>
    
    <div class="intro">
        <div class="container_1">
            <div class="pre_logo"></div>
            <div class="logo">
                <h1 class="logo_text">Личный кабинет</h1>
            </div>
            
        </div>
    </div>

    <div class="basic">
        <div class="container_1">
            <div class="basic_inner">
                <div class="block">
                    <% String name = (String) request.getAttribute("username");%>
                    <h1 class="username"><%=name%></h1>

                    <% String email = (String) request.getAttribute("user_email");%>
                    <h2 class="email"><%=email%></h2>

                    <a class="goto" href="jsp/store.jsp" data-scroll="#">Магазин</a>

                    <form action="bucketPage" method="POST">
                        <button class="goto">Корзина (${cookie.get("count").value})</button>
                    </form>

                    <a class="goto" href="">Заказы</a>

                    <form action="exitPage" method="POST">
                        <button class="goto">Выйти</button>
                    </form>
                </div>
    
                <div class="block_2">
                    <h1 class="title">Правовая информация</h1>
                    <a class="about_link" href="about_del&pay.html">Условия доставки и оплаты</a>
                    <a class="about_link" href="about_persdata.html">Согласие на обработку персональных данных</a>

                    <h1 class="title">Нужна помощь? Свяжитесь с нами</h1>
                    <a class="about_link" href="mailto:lnayc368@gmail.com">lnayc368@gmail.com</a>
                    <a class="about_link" href="https://t.me/zhertvapropagandynolana">telegram</a>
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