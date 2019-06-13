$(document).ready(function () {

    var medicineEditor = new $.fn.dataTable.Editor({
        ajax: {
            create: {
                contentType: 'application/json',
                url: "api/medicines", // json datasource
                data: function (d) {
                    var newdata;
                    $.each(d.data, function (key, value) {
                        newdata = JSON.stringify(value);

                    });
                    console.log(newdata);
                    return newdata;
                },

                success: function (response) {
                    medicineTable.draw();
                    medicineEditor.close();
                    // alert(response.responseText);
                },
                error: function (jqXHR, exception) {
                    alert(jqXHR.responseJSON.cause.message);
                },
                type: 'POST'
            },
            edit: {
                contentType: 'application/json',
                url: "api/medicines/_id_",
                data: function (d) {
                    var newdata;
                    $.each(d.data, function (key, value) {
                        newdata = JSON.stringify(value);

                    });
                    console.log(newdata);
                    return newdata;
                },
                success: function (response) {
                    medicineTable.draw();
                    medicineEditor.close();
                    // alert(response.responseText);
                },
                error: function (jqXHR, exception) {
                    alert(jqXHR.responseJSON.cause.message);
                },
                type: 'PATCH'
            },
            remove: {
                contentType: 'application/json',
                url: "api/medicines/_id_",
                data: function (d) {
                    return '';
                },
                success: function (response) {
                    medicineTable.draw();
                    medicineEditor.close();
                    // alert(response.responseText);
                },
                error: function (jqXHR, exception) {
                    alert(jqXHR.responseJSON.cause.message);
                },
                type: 'DELETE'
            }
        },
        table: '#medicineTable',
        idSrc: 'id',
        fields: [ {label: 'Название', name: 'name', type: 'text'},
            {label: 'Тип', name: 'type', type: 'selectize', options: [],  opts: {
                    searchField: "label",
                    create: false,
                    load: function (query, callback) {
                        $.get(`api/medicineTypes/search/findTop10ByNameContaining?name=${query}`,

                            function (data) {
                                let pointOptions = [];
                                data._embedded.medicineTypes.forEach(function (entry) {

                                    pointOptions.push({
                                        "label": entry.name,
                                        "value": entry._links.self.href
                                    });
                                });
                                callback(pointOptions);
                            }
                        )
                    },
                    preload: true,
                    delimiter: null,
                    loadThrottle: 500
                }
            },
            {label: 'Применения', name: 'useType', type: 'selectize', options: [],  opts: {
                    searchField: "label",
                    create: false,
                    load: function (query, callback) {
                        $.get(`api/useTypes/search/findTop10ByNameContaining?name=${query}`,

                            function (data) {
                                let pointOptions = [];
                                data._embedded.useTypes.forEach(function (entry) {

                                    pointOptions.push({
                                        "label": entry.name,
                                        "value": entry._links.self.href
                                    });
                                });
                                callback(pointOptions);
                            }
                        )
                    },
                    preload: true,
                    delimiter: null,
                    loadThrottle: 500
                }
            },
            {label: 'Действующие вещество', name: 'substanceDescription', type: 'text'},
            {label: 'Описание', name: 'description', type: 'text'},
            {label: 'Средняя цена', name: 'price', type: 'text'}
        ]
    });

        var medicineTable = $("#medicineTable").DataTable({
                processing: true,
                serverSide: true,
                searchDelay: 800,
                ajax: {
                    contentType: 'application/json',
                    processing: true,
                    data: function (d) {
                        return JSON.stringify(d);
                    },
                    url: "medicines", // json datasource
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
                    {"name": "name", "data": "name", title: "Название"},
                    {
                        "name": "type.name",
                        "data": "type.name",
                        orderable: false,
                        title:"Тип",
                        defaultContent: ""
                    },
                    {
                        "name": "useType.name",
                        "data": "useType.name",
                        orderable: false,
                        title: "Применение",
                        defaultContent: ""
                    },
                    {
                        "name": "substanceDescription",
                        "data": "substanceDescription",
                        title: "Действующее вещество",
                        searchable: false,
                        orderable: false
                    },
                    {
                        "name": "description",
                        "data": "description",
                        title: "Описание",
                        searchable: false,
                        orderable: false
                    },
                    {
                        "name": "price",
                        "data": "price",
                        title: "Средняя цена",
                        searchable: false,
                        orderable: false
                    }],
            buttons:  [
                {extend: "create", text:"Создать", editor: medicineEditor,},
                {extend: "edit", text:"Изменить", editor: medicineEditor},
                {extend: "remove", text:"Удалить", editor: medicineEditor}
            ]
            });

});