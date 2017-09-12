---
---
/* the 2 lines above are for jekyll to process this file with liquid */
$(document).ready(function() {
    initSidebar();
    initLunrIndex();
    addSearchChangeListeners();
});

/**
 * this constant defines how many item changes must happen
 * at once for the program to choose the fast route of showing/hiding
 * things with .show() and .hide() instead of the standard animated way
 */
var MAX_ITEM_CHANGES = 30;
/** empty jquery query for internal use only */
var EMPTY_QUERY = $([]);

function initSidebar() {
    /* When clicked in the area around the checkbox, it should count as a check */
    $('.sidebar-list-item.checkbox').click(function(e) {
        if (e.target != this) return;
        var cBox = $(this).children("input");
        cBox.prop('checked', !cBox.prop('checked'));
        cBox.trigger("change");
    });

    $('#checkbox_allcats').change(function() {
        var val = $(this).prop('checked');
        if (val == null) {
            val = false
        }
        $('.sidebar-list-item.checkbox[category] > input')
            .prop('checked', val).trigger("change");
    });
    $('#checkbox_alltags').change(function() {
        var val = $(this).prop('checked');
        if (val == null) {
            val = false
        }
        $('.sidebar-list-item.checkbox[tag] > input')
            .prop('checked', val).trigger("change");
    });
}

function initLunrIndex() {
    // Download the data from the JSON file we generated
    window.search_index = $.getJSON("{{ '/search_index.json' | prepend: site.baseurl }}");

    // Wait for the data to load and add it to lunr
    window.search_index.then(function(loaded_data) {
      window.idx = lunr(function() {
        // remove stemmer to prevent 'caching' to be mapped to 'cach'
        this.pipeline.remove(lunr.stemmer)

        this.ref('id');
        this.field('title');
        this.field('category');
        this.field('tags');

        loaded_data.forEach(function (doc, index) {
          this.add(
              $.extend({
                  "id": index
              }, doc)
          );
        }, this)
      });
    });
}

function addSearchChangeListeners() {
    $('.sidebar-list-item.checkbox').on("change", function(e) {
        updateResults();
    });
    $('#searchBox').keyup(function(e) {
        var text = $(this).val();
        if (text.length > 2 || text.length == 0) {
            updateResults();
        }
    });
}

function updateResults() {
    // get the name of the checked boxes
    var checkedCats = $('.sidebar-list-item.checkbox[category] :checked')
        .map(function() {
            return $(this).parent().attr('category');
        }).get();
    var checkedTags = $('.sidebar-list-item.checkbox[tag] :checked')
        .map(function() {
            return $(this).parent().attr('tag');
        }).get();

    // prepare selectors, list-item's must have at least one matching category / tag (OR operator)
    var catsSelector = '[cats*=\'' + checkedCats.join('\'], [cats*=\'') + '\']';
    var tagsSelector = '[tags*=\'' + checkedTags.join('\'], [tags*=\'') + '\']';

    // select all list-item's that match the checked boxes
    var catsQuery = checkedCats.length > 0 ? $('.list-item' + catsSelector) : EMPTY_QUERY;
    var tagsQuery = checkedTags.length > 0 ? $('.list-item' + tagsSelector) : EMPTY_QUERY;

    // intersect both, categories AND tags must be present on a list-item
    var intersectionQuery = catsQuery.filter(tagsQuery);

    var text = $('#searchBox').val();
    var results = window.idx.search(text + '*');

    window.search_index.then(function(loaded_data) {
        // Are there any results?
        if (results.length) {
            var resultData = results.map(function(r) {
                return loaded_data[r.ref].title;
            })
            var textSelector = ':contains(\'' + resultData.join('\'), :contains(\'') + '\')';
            intersectionQuery = intersectionQuery.filter(textSelector);
        } else if (text.length > 2) {
            intersectionQuery = EMPTY_QUERY;
        }
        // update the pattern counter
        $('#current-pattern-count-showing').text(intersectionQuery.length);

        var diffQuery = $('.list-item').not(intersectionQuery);
        /* further narrow down to only REAL changes */
        diffQuery = diffQuery.filter(':visible');
        intersectionQuery = intersectionQuery.filter(':hidden');
        if (diffQuery.length + intersectionQuery.length > 30) {
            diffQuery.hide();
            intersectionQuery.show();
        } else {
            diffQuery.slideUp(200);
            intersectionQuery.slideDown();
        }
    });

    // lastly we squeeze in the styling of the master-checkbox
    var cbAllcats = $('#checkbox_allcats');
    if (checkedCats.length == 0) {
        cbAllcats.prop('checked', false);
        cbAllcats.parent().removeClass('indeterminate');
    } else if (checkedCats.length == $('.sidebar-list-item.checkbox[category]').length) {
        cbAllcats.prop('checked', true);
        cbAllcats.parent().removeClass('indeterminate');
    } else {
        cbAllcats.prop('checked', 'indeterminate');
        cbAllcats.parent().addClass('indeterminate');
    }

    var cbAlltags = $('#checkbox_alltags');
    if (checkedTags.length == 0) {
        cbAlltags.prop('checked', false);
        cbAlltags.parent().removeClass('indeterminate');
    } else if (checkedTags.length == $('.sidebar-list-item.checkbox[tag]').length) {
        cbAlltags.prop('checked', true);
        cbAlltags.parent().removeClass('indeterminate');
    } else {
        cbAlltags.prop('checked', 'indeterminate');
        cbAlltags.parent().addClass('indeterminate');
    }
}
