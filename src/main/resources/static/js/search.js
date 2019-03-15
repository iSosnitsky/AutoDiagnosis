$(document).ready(function () {
    var search = new Vue({
        el: '#search',
        data: {
            symptoms: null,
            pathologies: null,
            medicines: null,
            medicine: null,
            pathology: null,
            useType: null,
            medicineType: null
        },
        methods: {
            pickPathology: function (pathologyId) {
                let that = this;
                that.medicine=null;
                that.useType = null;
                that.medicineType = null;
                $.get(`api/pathologies/${pathologyId}`).success(function (data) {
                    that.pathology = data;
                    console.log(data);
                    $.get(`${data._links.medicines.href}`).success(function (medicines) {
                        that.medicines = medicines._embedded.medicines;
                    })
                })
            },
            pickMedicine: function (medicineId) {
                let that = this;
                $.get(`api/medicines/${medicineId}`).success(function (data) {
                    that.medicine = data;
                    console.log(data);
                    $.get(`${data._links.useType.href}`).success(function (useType) {
                        that.useType = useType;
                    });
                    $.get(`${data._links.type.href}`).success(function (type) {
                        that.medicineType = type;
                    })

                })
            }
        },
        mounted: function () {
            let selectize = $("#search-symptoms").selectize(
                {
                    placeholder: "Выберите симптом",
                    labelField: "label",
                    valueField: "value",
                    loadThrottle: 400,
                    preload: true,
                    delimiter: ',',
                    maxItems: 10,
                    create: false,
                    load: function (query, callback) {
                        $.get(`api/symptoms/search/findTop10ByNameContaining?name=${query}`,
                            function (data) {
                                console.log(data)
                                var symptomsOption = [];
                                data._embedded.symptoms.forEach(function (entry) {
                                    symptomsOption.push({
                                        "label": entry.name,
                                        "value": entry._links.self.href
                                        // value: entry.id
                                    })
                                });
                                callback(symptomsOption);
                            }
                        );


                    },
                    onChange: (value) => {
                        if (value != null && value !== '') {
                            let that = this;
                            console.log(value);
                            $.get(`api/pathologies/search/findAllBySymptomsContaining?symptoms=${value}`).success(function (data) {
                                that.pathologies = data._embedded.pathologies;
                            })
                        }
                    }
                }
            );
        }

    })
});