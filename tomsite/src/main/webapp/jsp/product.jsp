<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%
        String tag = (String) request.getAttribute("tag");
        String name = (String) request.getAttribute("name");
        String price = (String) request.getAttribute("price");
        String description = (String) request.getAttribute("description");
        List<String> imgs = (List<String>) request.getAttribute("imgs");
        String count = (String) request.getAttribute("count");
    %>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;700&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=application.getContextPath() %>/css/product.css" >
    <link rel="shortcut icon" href="img/other/icon.png" />
    <title><%=name%></title>
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
                <a class="nav_link" href="jsp/exhibitions.jsp" data-scroll="#">Выставки</a>
                <a class="nav_link" href="bio.html" data-scroll="#">Биография</a>
                <a class="nav_link_2" href="jsp/store.jsp" data-scroll="#">Магазин</a>
                <a class="nav_link" href="contacts.html" data-scroll="#">Контакты</a>
                <a class="nav_link" href="log_or_reg.html" data-scroll="#">Аккаунт</a>
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

    <div class="basic">
        <div class="container_1">
            <div class="basic_inner">

                <div class="block_1">
                    <main>
                        <div class="slider-container">
                            <div class="slider">
                                <img src="<%=imgs.get(0)%>" alt="1">
                                <img src="<%=imgs.get(1)%>" alt="2">
                                <img src="<%=imgs.get(2)%>" alt="3">
                                <img src="<%=imgs.get(3)%>" alt="4">
                            </div>

                            <button class="prev-button" aria-label="Посмотреть предыдущий слайд">&lt</button>
                            <button class="next-button" aria-label="Посмотреть следующий слайд">&gt</button>
                        </div>
                    </main>

                    <script src="js/script.js"></script>
                </div>


                <div class="block_2">
                    <h1 class="product"><%=name%></h1>
                    <h2 class="price"><%=price%> $</h2>
                    <h2 class="count">В наличии <%=count%> шт.</h2>

                    <div class="actions">

                        <div class="act_block">
                            <form action="addBucket" method="POST">
                                <input class="addLink" type="submit" value="+ Корзину">
                                <input type="hidden" name="product" value="<%=tag%>">
                            </form>
                        </div>

                        <div class="act_block">
                            <form action="bucketPage" method="POST">
                                <button>Корзина</button>
                            </form>
                        </div>

                    </div>

                    <div class="text">
                        <%=description%>
                    </div>

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