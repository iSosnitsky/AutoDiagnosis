$(document).ready(function () {

    var useTypesEditor = new $.fn.dataTable.Editor({
        ajax: {
            create: {
                contentType: 'application/json',
                url: "api/useTypes", // json datasource
                data: function (d) {
                    var newdata;
                    $.each(d.data, function (key, value) {
                        newdata = JSON.stringify(value);

                    });
                    console.log(newdata);
                    return newdata;
                },

                success: function (response) {
                    useTypesTable.draw();
                    useTypesEditor.close();
                    // alert(response.responseText);
                },
                error: function (jqXHR, exception) {
                    alert(jqXHR.responseJSON.cause.message);
                },
                type: 'POST'
            },
            edit: {
                contentType: 'application/json',
                url: "api/useTypes/_id_",
                data: function (d) {
                    var newdata;
                    $.each(d.data, function (key, value) {
                        newdata = JSON.stringify(value);

                    });
                    console.log(newdata);
                    return newdata;
                },
                success: function (response) {
                    useTypesTable.draw();
                    useTypesEditor.close();
                    // alert(response.responseText);
                },
                error: function (jqXHR, exception) {
                    alert(jqXHR.responseJSON.cause.message);
                },
                type: 'PATCH'
            },
            remove: {
                contentType: 'application/json',
                url: "api/useTypes/_id_",
                data: function (d) {
                    return '';
                },
                success: function (response) {
                    useTypesTable.draw();
                    useTypesEditor.close();
                    // alert(response.responseText);
                },
                error: function (jqXHR, exception) {
                    alert(jqXHR.responseJSON.cause.message);
                },
                type: 'DELETE'
            }
        },
        table: '#useTypesTable',
        idSrc: 'id',
        fields: [ {label: 'Название', name: 'name', type: 'text'}
        ]
    });

    var useTypesTable = $("#useTypesTable").DataTable({
        processing: true,
        serverSide: true,
        searchDelay: 800,
        ajax: {
            contentType: 'application/json',
            processing: true,
            data: function (d) {
                return JSON.stringify(d);
            },
            url: "useTypes", // json datasource
            type: "post"  // method  , by default get
        },
        dom: 'Bfrtp',
        language: {
            url: '/localization/dataTablesRus.json'
        },
        select: {
            style: 'single'
        },

        initComplete: function () {
            console.log("darova");
        },

        "paging": 10,
        "columns": [
            {"name": "id", "data": "id", title: "id", visible:false},
            {"name": "name", "data": "name", title: "Название"}
            ],
        buttons:  [
            {extend: "create", editor: useTypesEditor},
            {extend: "edit", editor: useTypesEditor},
            {extend: "remove", editor: useTypesEditor}
        ]
    });

});