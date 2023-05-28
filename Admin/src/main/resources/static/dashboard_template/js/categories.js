$('document').ready(function (){
    $('table #editButton').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (category, status){
            $('#idEdit').val(category.id);
            $('#nameEdit').val(category.name);
            $('#is_deleted').val(category.is_deleted);
            $('#is_activated').val(category.is_activated);
        });
        $('#editModal').modal();
    });
});