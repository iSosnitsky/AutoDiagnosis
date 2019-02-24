$(document).ready(function () {
    var pathologyEditor = new $.fn.dataTable.Editor({
        ajax: {
            create: {
                contentType: 'application/json',
                url: "api/pathologies", // json datasource
                data: function (d) {
                    var newdata;
                    $.each(d.data, function (key, value) {
                        newdata = JSON.stringify(value);
                        console.log(newdata);
                    });
                    return newdata;
                },

                success: function (response) {
                    pathologyTable.draw();
                    pathologyEditor.close();
                    // alert(response.responseText);
                },
                error: function (jqXHR, exception) {
                    alert(jqXHR.responseJSON.cause.message);
                },
                type: 'POST'
            },
            edit: {
                contentType: 'application/json',
                url: "api/pathologies/_id_",
                data: function (d) {
                    var newdata;
                    $.each(d.data, function (key, value) {
                        newdata = JSON.stringify(value);

                    });
                    console.log(newdata);
                    return newdata;
                },
                success: function (response) {
                    pathologyTable.draw();
                    pathologyEditor.close();
                    // alert(response.responseText);
                },
                error: function (jqXHR, exception) {
                    alert(jqXHR.responseJSON.cause.message);
                },
                type: 'PATCH'
            },
            remove: {
                contentType: 'application/json',
                url: "api/pathologies/_id_",
                data: function (d) {
                    return '';
                },
                success: function (response) {
                    pathologyTable.draw();
                    pathologyEditor.close();
                    // alert(response.responseText);
                },
                error: function (jqXHR, exception) {
                    alert(jqXHR.responseJSON.cause.message);
                },
                type: 'DELETE'
            }
        },
        table: '#pathologyTable',
        idSrc: 'id',
        fields: [ {label: 'Название', name: 'name', type: 'text'},
            {label: 'Описание', name: 'description', type: 'text'},
            {label: 'Симптомы', name: 'symptoms', type: 'selectize', options: [],  opts: {
                    searchField: "label",
                    create: false,
                    maxItems :1,
                    load: function (query, callback) {
                        $.get(`api/symptoms/search/findTop10ByNameContaining?name=${query}`,

                            function (data) {
                                let pointOptions = [];
                                data._embedded.symptoms.forEach(function (entry) {

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
            {
                label: 'Лекарство',
                name: 'medicines',
                type: 'selectize',
                options: [],
                opts: {
                    searchField: "label",
                    create: false,
                    maxItems :10,
                    load: function (query, callback) {
                        $.get(`api/medicines/search/findTop10ByNameContaining?name=${query}`,

                            function (data) {
                                let pointOptions = [];
                                console.log(JSON.stringify(data._embedded.medicines));
                                data._embedded.medicines.forEach(function (entry) {

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
                    loadThrottle: 500
                }
            }

        ]

    });
    var pathologyTable = $("#pathologyTable").DataTable({
        processing: true,
        serverSide: true,
        searchDelay: 800,
        ajax: {
            contentType: 'application/json',
            processing: true,
            data: function (d) {
                return JSON.stringify(d);
            },
            url: "pathologies", // json datasource
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
                "name": "description",
                "data": "description",
                title: "Описание",
                searchable: false,
                orderable: false
            },
            {
                "name": "symptoms",
                "data": "symptoms",
                title: "Симптомы",
                defaultContent: "",
                searchable: false,
                orderable: false


            },
            {
                "name": "medicines",
                "data": "medicines",
                title: "Лекарство",
                defaultContent: "",
                searchable: false,
                orderable: false
            }
        ],
        buttons: [
            {extend: "create", editor: pathologyEditor},
            {extend: "edit", editor: pathologyEditor},
            {extend: "remove", editor: pathologyEditor}
        ]
    });

});