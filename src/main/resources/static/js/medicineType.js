

    $(document).ready(function() {
        var medicineTypeEditor = new $.fn.dataTable.Editor({
            ajax: {
                create: {
                    contentType: 'application/json',
                    url: "api/medicineTypes", // json datasource
                    data: function (d) {
                        var newdata;
                        $.each(d.data, function (key, value) {
                            newdata = JSON.stringify(value);

                        });
                        console.log(newdata);
                        return newdata;
                    },

                    success: function (response) {
                        medicineTypeTable.draw();
                        medicineTypeEditor.close();
                        // alert(response.responseText);
                    },
                    error: function (jqXHR, exception) {
                        alert(jqXHR.responseJSON.cause.message);
                    },
                    type: 'POST'
                }
            },
            table: '#medicineTypeTable',
            fields: [ {label: 'Название', name: 'name', type: 'text'}
            ]
        });
        var medicineTypeEditorPatch = new $.fn.dataTable.Editor({
             ajax: {
                 edit: {
                     contentType: 'application/json',
                     url: "api/medicineTypes/_id_",
                     data: function (d) {
                         var newdata;
                         $.each(d.data, function (key, value) {
                             newdata = JSON.stringify(value);

                         });
                         console.log(newdata);
                         return newdata;
                     },
                     success: function (response) {
                         medicineTypeTable.draw();
                         medicineTypeEditorPatch.close();
                         // alert(response.responseText);
                     },
                     error: function (jqXHR, exception) {
                         alert(jqXHR.responseJSON.cause.message);
                     },
                     type: 'PATCH'
                 }
             },
            fields: [ {label: 'Название', name: 'name', type: 'text'}],
            table: '#medicineTypeTable',
            idSrc: 'id'

         });
        var medicineTypeEditorRemove = new $.fn.dataTable.Editor({
            ajax: {
                remove: {
                    contentType: 'application/json',
                    url: "api/medicineTypes/_id_",
                    data: function (d) {
                       return '';
                    },
                    success: function (response) {
                        medicineTypeTable.draw();
                        medicineTypeEditorRemove.close();
                        // alert(response.responseText);
                    },
                    error: function (jqXHR, exception) {
                        alert(jqXHR.responseJSON.cause.message);
                    },
                    type: 'DELETE'
                }
            },
            fields: [ {label: 'Название', name: 'name', type: 'text'}],
            table: '#medicineTypeTable',
            idSrc: 'id'

        });


        var medicineTypeTable = $("#medicineTypeTable").DataTable({
            processing: true,
            serverSide: true,
            searchDelay: 800,
            ajax: {
                contentType: 'application/json',
                processing: true,
                data: function (d) {
                    return JSON.stringify(d);
                },
                url: "medicineTypes", // json datasource
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
                {"name": "id", "data": "id", title: "id", visible: false},
                {"name": "name", "data": "name", title: "Название"}
            ],
            buttons: [
                {extend: "create", editor: medicineTypeEditor},
                {extend: "edit", editor: medicineTypeEditorPatch},
                 {extend: "remove", editor: medicineTypeEditorRemove}
            ]
        });

    });