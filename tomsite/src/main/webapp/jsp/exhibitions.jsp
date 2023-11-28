<%@ page import="models.Exh" %>
<%@ page import="java.util.List" %>
<%@ page import="repository.ExhRepositoryJdbc" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;700&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=application.getContextPath() %>/css/exh.css" >
    <link rel="shortcut icon" href="../img/other/icon.png" />
    <title>Выставки</title>
</head>

<body>

<header class="header">
    <div class="container_1" id="content">
        <div class="header_inner">
            <a href="http://localhost:8080/tomsite_war/index.jsp">
                <img class="header_logo" src="../img/other/logo.png" alt="">
            </a>
        </div>

        <nav class="nav" id="nav">
            <a class="nav_link_2" href="exhibitions.jsp" data-scroll="#">Выставки</a>
            <a class="nav_link" href="../bio.html" data-scroll="#">Биография</a>
            <a class="nav_link" href="store.jsp" data-scroll="#">Магазин</a>
            <a class="nav_link" href="../contacts.html" data-scroll="#">Контакты</a>
            <a class="nav_link" href="../log_or_reg.html" data-scroll="#">Аккаунт</a>
        </nav>
    </div>
</header>

    <div class="basic">
        <div class="container_1">
            <div class="pre_logo" id="point"></div>
            <div class="logo">
                <h1 class="logo_text">Выставки</h1>
            </div>

            <%

                List<Exh> list = (List<Exh>) config.getServletContext().getAttribute("exhs");

                ExhRepositoryJdbc repositoryJdbc =  (ExhRepositoryJdbc) config.getServletContext().getAttribute("ExhRep");

                String style = "";
                for (int i = 0; i < list.size(); i++) {
                    String tag = list.get(i).getTag();

                    List<String> imgs = repositoryJdbc.getImgs(tag, repositoryJdbc.getImgsSource(tag));
                    if(i == list.size() - 1) {
                        style = "style=\"border-bottom: none\"";
                    }



            %>

            <div class="basic_inner" <%=style%>>
                <div class="block">
                    <img class="block_img" src="../<%=imgs.get(0)%>" alt="">
                </div>
                <div class="text">
                    <div class="block_2">

                        <form action="${pageContext.servletContext.contextPath}/gotoExh" method="GET">
                            <input type="submit" class="block_title" value="<%=list.get(i).getName()%>">
                            <input type="hidden" name="tag" value="<%=list.get(i).getTag()%>">
                        </form>

                        <p class="block_text"><%=list.get(i).getPlace()%></p>
                        <p class="block_text"><%=list.get(i).getEventDates()%></p>
                    </div>
                    <div class="block_3">
                        <h1 class="block_title_2">О чем</h1>
                        <p class="about"><%=list.get(i).getS_description()%></p>
                    </div>
                </div>
            </div>

            <%
                }
            %>


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
                    <img class="block_2_img" src="../img/other/footer_img_1.jpg" alt="">
                </div>
            </div>

        </div>
    </footer>

    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.4.1.min.js"></script>
    <script src="../js/header.js"></script>

</body>
</html>