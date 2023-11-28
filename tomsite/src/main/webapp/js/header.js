$(function() {

    let nav = $("#nav");
    let intro = $("#point");
    let con = $("#content");

    let introH = intro.innerHeight() + 87;
    let scrollPos = $(window).scrollTop();

    $(window).on("scroll load", function() {

        scrollPos = $(this).scrollTop();
        if(scrollPos > introH) {
            nav.addClass("fixed");
            con.addClass("wid");
        } else {
            nav.removeClass("fixed");
            con.removeClass("wid");
        }
    });

});