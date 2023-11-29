<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<%
    String name = (String) request.getAttribute("name");
    String eventd = (String) request.getAttribute("eventd");
    String place = (String) request.getAttribute("place");
    String sDesc = (String) request.getAttribute("sDesc");
    String desc = (String) request.getAttribute("desc");

%>
<head>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;500;700&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=application.getContextPath() %>/css/exhpres.css" >
    <link rel="shortcut icon" href="img/other/icon.png" />
    <title><%=name%></title>
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
                <a class="nav_link_2" href="jsp/exhibitions.jsp" data-scroll="#">Выставки</a>
                <a class="nav_link" href="bio.html" data-scroll="#">Биография</a>
                <a class="nav_link" href="jsp/store.jsp"  data-scroll="#">Магазин</a>
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
            <div class="basic_inner" style="margin-top: 30px;">

                <div class="about">
                    <h1 class="name"><%=name%></h1>
                    <h2 class="place"><%=place%></h2>
                    <h2 class="event_date"><%=eventd%></h2>
                    <h2 class="s_desc"><%=sDesc%></h2>
                </div>

            </div>

            <div class="basic_inner" style="margin-top: 32px;">                
                <div class="imgs">
                    <%
                        List<String> caps = ( List<String>) request.getAttribute("caps");
                        List<String> imgs = ( List<String>) request.getAttribute("imgs");
                        for (int i = 0; i < imgs.size(); i++) {
                    %>
                    <div class="img_block">
                        <img class="img" src="<%=imgs.get(i)%>" alt="">
                        <h3 class="cap"><%=caps.get(i)%></h3>
                    </div>

                    <%}%>
                </div>
            </div>

            <div class="basic_inner">
                <div class="text"><%=desc%></div>
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

    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.4.1.min.js"></script>
    <script src="js/header.js"></script>

</body>
</html>