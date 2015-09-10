define(["jquery"],function($){
    return function(spec){

        $("[name='"+spec+"'] ").next().children().first().after("<input id='filter-input-for-"+spec+"' placeholder='Filter...' type='text'/>");
        $("#filter-input-for-"+spec).keyup(function(){
            var filterField = $(this);
            var searchTerm = filterField.val().toLowerCase();
            $.map($("#filter-input-for-"+spec).next().children(),function(cur){
                if($(cur).text().toLowerCase().indexOf(searchTerm) > -1){
                    $(cur).show(100);
                } else {
                    $(cur).hide(100);
                }
            })

        })
    };
});