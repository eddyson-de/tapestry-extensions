/**
 * Created by fkretzer on 24.07.15.
 */
define(["t5/core/ajax", "t5/core/console","jquery", "select2"],function(ajax, console, $){
    return function(spec, multiple, required, blanklabel, theme){
        var $multiSelect = $("#"+spec);
        var $changeEventUri = $multiSelect.data("change-uri")
        var options = {};
        if(multiple){
            options.multiple = true;
        }
        if(theme != null){
          options.theme = theme;
        }

        if (!required){
            options.placeholder = {
                id: "-1",
                text: blanklabel
            };
            options.allowClear = true;
        }

        $multiSelect.select2(options);
        $multiSelect.show();
        if ($changeEventUri){

            $multiSelect.on("change", function(e){
                var jsonString = JSON.stringify($multiSelect.val());
                console.debug(spec+" changed. Submitting new values: "+jsonString);
                ajax($changeEventUri, {
                    data: {values: jsonString}});
            });
        }

    };
});
