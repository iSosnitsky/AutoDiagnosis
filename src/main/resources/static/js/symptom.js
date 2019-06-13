$(document).ready(function () {
    var symptomEditor = new $.fn.dataTable.Editor({
        ajax: {
            create: {
                contentType: 'application/json',
                url: "api/symptoms", // json datasource
                data: function (d) {
                    var newdata;
                    $.each(d.data, function (key, value) {
                        newdata = JSON.stringify(value);

                    });
                    console.log(newdata);
                    return newdata;
                },

                success: function (response) {
                    symptomTable.draw();
                    symptomEditor.close();
                    // alert(response.responseText);
                },
                error: function (jqXHR, exception) {
                    alert(jqXHR.responseJSON.cause.message);
                },
                type: 'POST'
            }
        },
        table: '#symptomsTable',
        fields: [ {label: 'Название', name: 'name', type: 'text'}
        ]
    });
    var symptomEditorPatch = new $.fn.dataTable.Editor({
        ajax: {
            edit: {
                contentType: 'application/json',
                url: "api/symptoms/_id_",
                data: function (d) {
                    var newdata;
                    $.each(d.data, function (key, value) {
                        newdata = JSON.stringify(value);

                    });
                    console.log(newdata);
                    return newdata;
                },
                success: function (response) {
                    symptomTable.draw();
                    symptomEditorPatch.close();
                    // alert(response.responseText);
                },
                error: function (jqXHR, exception) {
                    alert(jqXHR.responseJSON.cause.message);
                },
                type: 'PATCH'
            }
        },
        fields: [ {label: 'Название', name: 'name', type: 'text'}],
        table: '#symptomsTable',
        idSrc: 'id'

    });
    var symptomEditorRemove = new $.fn.dataTable.Editor({
        ajax: {
            remove: {
                contentType: 'application/json',
                url: "api/symptoms/_id_",
                data: function (d) {
                    return '';
                },
                success: function (response) {
                   symptomTable.draw();
                   symptomEditorRemove.close();
                    // alert(response.responseText);
                },
                error: function (jqXHR, exception) {
                    alert(jqXHR.responseJSON.cause.message);
                },
                type: 'DELETE'
            }
        },
        fields: [ {label: 'Название', name: 'name', type: 'text'}],
        table: '#symptomsTable',
        idSrc: 'id'

    });



    var symptomTable = $("#symptomsTable").DataTable({
        processing: true,
        serverSide: true,
        searchDelay: 800,
        ajax: {
            contentType: 'application/json',
            processing: true,
            data: function (d) {
                return JSON.stringify(d);
            },
            url: "symptoms", // json datasource
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
            console.log("fgsfds");
        },

        "paging": 10,
        "columns": [
            {"name": "id", "data": "id", title: "id", visible:false},
            {"name": "name", "data": "name", title: "Название"}
        ],
        buttons: [
            {extend: "create", text:"Создать", editor: symptomEditor},
            {extend: "edit", text:"Изменить", editor: symptomEditorPatch},
            {extend: "remove", text:"Удалить", editor: symptomEditorRemove}
        ]

    });

});