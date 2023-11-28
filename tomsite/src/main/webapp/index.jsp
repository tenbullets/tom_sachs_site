<%@ page import="java.util.List" %>
<%@ page import="repository.StoreRepositoryJdbc" %>
<%@ page import="models.Product" %>
<%@ page import="models.Exh" %>
<%@ page import="repository.ExhRepositoryJdbc" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;700&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=application.getContextPath()%>/css/index.css" >
    <link rel="shortcut icon" href="img/other/icon.png"/>
    <title>Том Сакс</title>
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

    <div class="pre_intro" id="point"></div>
    <div class="intro">
        <div class="container_1">
            <img class="logo_img" src="img/other/tom.jpg" align="left">
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
            <div class="basic_text" style="margin-bottom: 10px">Обновление В Магазине</div>

            <%
                List<Product> update = (List<Product>) config.getServletContext().getAttribute("update");
                StoreRepositoryJdbc storeRepository = (StoreRepositoryJdbc) config.getServletContext().getAttribute("storeRep");

            %>
            <div class="store_inner">
                <%
                    for (int i = 0; i < update.size(); i++) {
                        List<String> imgs = storeRepository.getImgs(update.get(i).getTag(), storeRepository.getImgsSource(update.get(i).getTag()));

                %>

                <div class="block">

                    <div class="change-photos">
                        <div class="change-photo">
                            <img class="block_img" src="<%=imgs.get(0)%>" alt="">
                        </div>
                        <div class="change-photo">
                            <img class="block_img" src="<%=imgs.get(1)%>" alt="">
                        </div>
                    </div>

                    <form action="${pageContext.servletContext.contextPath}/gotoProd" method="GET">
                        <input type="submit" class="product" value="<%=update.get(i).getName()%>">
                        <input type="hidden" name="htmlContent" value="<%=update.get(i).getTag()%>">
                        <p class="price"><%=update.get(i).getPrice()%> $</p>
                    </form>

                </div>
                <%
                    }
                %>

            </div>

            <div class="basic_text" style="margin-top: 17px">Действующие Выставки</div>

            <div class="basic_inner">
                <img class="exh_pic" src="img/other/exh.jpg" align="left">
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

            <%
                List<Exh> exhUpdate = (List<Exh>) config.getServletContext().getAttribute("exhUpdate");
                ExhRepositoryJdbc repository = (ExhRepositoryJdbc) config.getServletContext().getAttribute("ExhRep");

            %>


            <div class="basic_text" style="margin-bottom: 10px">Прошедшие Выставки</div>

            <%
                String style = "";
                for (int i = 0; i < exhUpdate.size(); i++) {
                    if(i == exhUpdate.size() - 1) style = "style=\"border-bottom: none\"";
                    if(i == 0) style = "style=\"margin-top: 38px\"";
                    String tag = exhUpdate.get(i).getTag();
                    List<String> imgs = repository.getImgs(tag, repository.getImgsSource(tag));
            %>

            <div class="exhs" <%=style%>>
                <div class="exh_block">
                    <img class="exh_img" src="<%=imgs.get(0)%>" alt="">
                </div>
                <div class="exh_info">
                    <div class="info_block_1">

                        <form action="${pageContext.servletContext.contextPath}/gotoExh" method="GET">
                            <input type="submit" class="exh_title_1" value="<%=exhUpdate.get(i).getName()%>">
                            <input type="hidden" name="tag" value="<%=exhUpdate.get(i).getTag()%>">
                        </form>

                        <p class="info_text"><%=exhUpdate.get(i).getPlace()%></p>
                        <p class="info_text"><%=exhUpdate.get(i).getEventDates()%></p>
                    </div>
                    <div class="info_block_2">
                        <h1 class="exh_title_2">О чем</h1>
                        <p class="about"><%=exhUpdate.get(i).getS_description()%></p>
                    </div>
                </div>
            </div>

            <%
                    style = "";
                }
            %>

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
                    <img class="block_2_img" src="img/other/footer_img_1.jpg" alt="">
                </div>
            </div>

        </div>
    </footer>

    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.4.1.min.js"></script>
    <script src="js/header.js"></script>

</body>
</html>