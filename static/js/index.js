$(document).ready(function() {
    categoryAndTagDisplay();
    generateContent();
    backToTop();
    initBootstrapTooltips();
});

function fixFooterInit() {
    var footerHeight = $('footer').outerHeight();
    var footerMarginTop = getFooterMarginTop() - 0;

    fixFooter(footerHeight, footerMarginTop); //fix footer at the beginning

    $(window).resize(function() { //when resize window, footer can auto get the postion
        fixFooter(footerHeight, footerMarginTop);
    });
}

function fixFooter(footerHeight, footerMarginTop) {
    var windowHeight = $(window).height();
    var contentHeight = $('body>.container').outerHeight() + $('body>.container').offset().top + footerHeight + footerMarginTop;
    console.log(contentHeight);
    if (contentHeight < windowHeight) {
        $('footer').addClass('navbar-fixed-bottom');
    } else {
        $('footer').removeClass('navbar-fixed-bottom');
    }

    $('footer').show(400);
}

function getFooterMarginTop() {
    var margintop = $('footer').css('marginTop');
    var patt = new RegExp("[0-9]*");
    var re = patt.exec(margintop);
    // console.log(re[0]);
    return re[0];
}

function categoryAndTagDisplay() {
    /*
    $('.post-list-body>div[post-category!=All]').hide();
    $('.post-list-body>div[post-tag!=All]').hide();
    */
    /*show category when click categories list*/
    $('a.cat-button').click(function() {
        var category = $(this).attr('category'); //get category's name
        $('.list-item').not('[cats*=\'' + category + '\']').slideUp(200)
        $('.list-item[cats*=\'' + category + '\']').slideDown()
    });
    /*show category when click tags list*/
    $('a.tag-button').click(function() {
        var tag = $(this).attr('tag'); //get tag's name
        $('.list-item').not('[tags*=\'' + tag + '\']').slideUp(200)
        $('.list-item[tags*=\'' + tag + '\']').slideDown()
    });
}

function backToTop() {
    $(window).scroll(function() {
        if ($(window).scrollTop() > 100) {
            $("#top").fadeIn(500);
        } else {
            $("#top").fadeOut(500);
        }
    });
    $("#top").click(function() {
        window.scrollTo(0, 0);
    });
}

function generateContent() {

    // console.log($('#markdown-toc').html());
    if (typeof $('#markdown-toc').html() === 'undefined') {
        // $('#content .content-text').html('<ul><li>文本较短，暂无目录</li></ul>');
        $('#content').hide();
        $('#myArticle').removeClass('col-sm-9').addClass('col-sm-12');
    } else {
        $('#content .content-text').html('<ul>' + $('#markdown-toc').html() + '</ul>');
        /*   //数据加载完成后，加固定边栏
        $('#myAffix').attr({
            'data-spy': 'affix',
            'data-offset': '50'
        });*/
    }
    console.log("myAffix!!!");
}

function initBootstrapTooltips() {
    // Select all elements with data-toggle="tooltips" in the document
    $('[data-toggle="tooltip"]').tooltip();
}
