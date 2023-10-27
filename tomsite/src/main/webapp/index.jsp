<%@ page import="java.util.Random" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;700&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=application.getContextPath()%>/css/index.css" >
    <link rel="shortcut icon" href="img/icon.png"/>
    <title>Том Сакс</title>
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

    <div class="pre_intro"></div>
    <div class="intro">
        <div class="container_1">
            <img class="logo_img" src="img/tom.jpg" align="left">
            <p class="logo_text">
                Том Сакс – американский художник и дизайнер. Его работы сочетают в
                себе элементы поп-культуры, концептуального искусства, науки и истории.
                 Том славится своим копированием и повторением объектов известных брендов, 
                 таких как Macintosh, Hermès, Leica или NASA, используя дешевые материалы и ручной труд.
            </p>

            <p class="logo_text_1">
                «Я никогда не смог бы создать что-то столь же безупречное, как Iphone, 
                но и Apple никогда не смогла бы создать что-то столь же ущербное, как одна из моих скульптур»
                - Том Сакс в лекции Ted.
            </p>
        </div>
    </div>

    <div class="basic">
        <div class="container_1">
            <div class="basic_text">Обновление В Магазине</div>
            <div class="basic_pic">
                <div class="change-photos">
                    <div class="change-photo">
                        <img class="store_img" src="img/store/bag_1.jpg" alt="">
                    </div>
                    <div class="change-photo">
                        <img class="store_img" src="img/store/bag_2.jpg" alt="">
                    </div>
                    <form action="gotoProd" method="GET">
                        <input type="submit" class="product" value="Сумка для MacBook">
                        <input type="hidden" name="htmlContent" value="bag">
                        <p class="price">275 $</p>
                    </form>
                </div>

                <div class="change-photos">
                    <div class="change-photo">
                        <img class="store_img" src="img/store/book_1.jpg" alt="">
                    </div>
                    <div class="change-photo">
                        <img class="store_img" src="img/store/book_2.jpg" alt="">
                    </div>
                    <form action="gotoProd" method="GET">
                        <input type="submit" class="product" value="Книга Spaceships">
                        <input type="hidden" name="htmlContent" value="spaceships">
                        <p class="price">65 $</p>
                    </form>
                </div>
    
                <div class="change-photos">
                    <div class="change-photo">
                        <img class="store_img" src="img/store/notebook_1.jpg" alt="">
                    </div>
                    <div class="change-photo">
                        <img class="store_img" src="img/store/notebook_2.jpg" alt="">
                    </div>
                    <form action="gotoProd" method="GET">
                        <input type="submit" class="product" value="Блокнот Ten Bullets">
                        <input type="hidden" name="htmlContent" value="note">
                        <p class="price">25 $</p>
                    </form>
                </div>
            </div>

            <div class="basic_text">Действующие Выставки</div>

            <div class="basic_inner">
                <img class="exh_pic" src="img/exh.jpg" align="left">
                <p class="exh_text">Retail Experience</p>
                <p class="exh_text_1">Магазин ISETAN в Синдзюку, Япония</p>
                <p class="exh_text_1">С 20 сентября по 23 октября 2023</p>
            </div>

            <div class="basic_inner_2">
                <p class="exh_text_2">
                    Том Сакс представляет вторую выставку «Retail Experience» по случаю 90-летия Isetan.
                    Выставка открывается во вторник, 20 сентября, и продлится до 23 октября 2023 года. 
                    На выставке будут представлены новые картины, скульптуры, бумбоксы, керамика, 
                    предметы из мебельной линии Tom Sachs, одежда и другие товары. 
                </p>
            </div>


            <div class="basic_text">Прошедшие Выставки</div>
            <div class="basic_pic">
                <div class="basic_itm">
<!--                    <a href="html/exhibitions.html">-->
<!--                        <img class="basic_img" src="img/timeline.jpg">-->
<!--                        <div class="itm_text">TIMELINE</div>-->
<!--                    </a>-->

                    <%
                        String[] exh1 = {"img/timeline_1.jpg", "img/timeline_2.jpg"};
                        String[] exh2 = {"img/rare_earths_1.jpg", "img/rare_earths_2.jpg"};
                        String[] exh3 = {"img/spaceships_1.jpg", "img/spaceships_2.jpg"};
                        Random rand = new Random();
                        int i = rand.nextInt(2); int j = rand.nextInt(2); int k = rand.nextInt(2);
                    %>

                    <img class="basic_img" src="<%=exh1[i]%>">
                    <div class="itm_text">TIMELINE</div>
                </div>

                <div class="basic_itm">
                    <img class="basic_img" src="<%=exh2[j]%>">
                    <div class="itm_text">SPACE PROGRAM «RARE EARTHS»</div>
                </div>

                <div class="basic_itm">
                    <img class="basic_img" src="<%=exh3[k]%>">
                    <div class="itm_text">SPACESHIPS</div>
                </div>
            </div>
                
        </div>
    </div>

    <footer class="footer">        
        <div class="container_1">
            <div class="footer_img"></div>

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
                    <img class="block_2_img" src="img/footer_img_1.jpg" alt="">
                </div>
            </div>

        </div>
    </footer> 

</body>
</html>