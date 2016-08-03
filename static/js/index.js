$(document).ready(function() {
    initLabels();
    initBackToTop();
    init();
});

function initLabels() {
    /*
    $('.post-list-body>div[post-category!=All]').hide();
    $('.post-list-body>div[post-tag!=All]').hide();
    */
    /*show category when click categories list*/
    $('a.cat-label').click(function() {
        var category = $(this).attr('category'); //get category's name
        $('.sidebar-list-item[category]').not('[category*=\"' + category + '\"]').find('input')
            .prop('checked', false).trigger("change");
        $('.sidebar-list-item[category*=\"' + category + '\"] > input')
            .prop('checked', true).trigger("change");
    });
    /*show category when click tags list*/
    $('a.tag-label').click(function() {
        var tag = $(this).attr('tag'); //get tag's name
        $('.sidebar-list-item[tag]').not('[tag*=\"' + tag + '\"]').find('input')
            .prop('checked', false).trigger("change");
        $('.sidebar-list-item[tag*=\"' + tag + '\"] > input')
            .prop('checked', true).trigger("change");
    });
}

function initBackToTop() {
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

function init() {
    // Select all elements with data-toggle="tooltips" in the document
    $('[data-toggle="tooltip"]').tooltip();

    // init heading anchor links
    $("h2, h3, h4, h5, h6").each(function(i, el) {
        var $el, icon, id;
        $el = $(el);
        id = $el.attr('id');
        icon = '<i class="fa fa-link"></i>';
        if (id) {
            return $el.prepend($("<a />").addClass("header-link").attr("href", "#" + id).html(icon));
        }
    });
}
