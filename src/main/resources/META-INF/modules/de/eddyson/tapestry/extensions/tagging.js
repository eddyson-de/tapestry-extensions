define(["t5/core/ajax", "t5/core/console","jquery", "select2"],function(ajax, console, $){
    return function(spec,completionUrl,placeholder){
        var $tagging = $("#"+spec);
        var options = {};

        options.tags = true;
        options.multiple = true;
        options.placeholder = {
            id: "-1",
            text: placeholder
        };

        options.ajax = {
            url: completionUrl,
                dataType: 'json',
                delay: 250,
                data: function (params) {
                return {
                    q: params.term
                };
            },
            processResults: function (data) {
                return {
                    results: data.data
                };
            },
            cache: true
        };
        options.minimumInputLength = 0;
        $tagging.select2(options);
        $tagging.show();
    };
});
