$(document).ready(function () {
    var regions = [];
    var states = [];
    var cities = [];

    $.ajax({
        url: '/api/localidades/regioes',
        dataType: 'json',
        success: function (data) {
            regions = data;
            var regionSelect = $('#addressRegiao');
            regions.forEach(function (region) {
                regionSelect.append('<option value="' + region.sigla + '">' + region.nome + '</option>');
            });

            regionSelect.on('change', function () {
                setStates();
            });
        },
        error: function (error) {
            console.error('Erro ao carregar regiões:', error);
        }
    });

    function setStates() {
        // value do select de regiões
        var regionSigla = $('#addressRegiao').val();
        $.ajax({
            url: '/api/localidades/estados',
            dataType: 'json',
            success: function (data) {
                states = data;;
                var filteredStates = states.filter(function (state) {
                    return state.sigla_regiao == regionSigla;
                });

                var stateSelect = $('#addressEstado');
                stateSelect.empty();
                stateSelect.append('<option value="">Selecione</option>');

                filteredStates.forEach(function (state) {
                    stateSelect.append('<option value="' + state.sigla + '">' + state.nome + '</option>');
                });

                stateSelect.on('change', function () {
                    setCities();
                });
            },
            error: function (error) {
                console.error('Erro ao carregar estados:', error);
            }
        });
    }

    function setCities() {
        var stateSigla = $('#addressEstado').val();
        $.ajax({
            url: '/api/localidades/cidades',
            dataType: 'json',
            success: function (data) {
                cities = data;

                var filteredCities = cities.filter(function (city) {
                    return city.sigla_estado == stateSigla;
                });

                var citySelect = $('#addressCidade');
                citySelect.empty();
                citySelect.append('<option value="">Selecione</option>');

                filteredCities.forEach(function (city) {
                    citySelect.append('<option value=\'{"value1": "'+ city.id + '","value2":"'+city.nome+'"} >' + city.nome + '</option>');
                });
            },
            error: function (error) {
                console.error('Erro ao carregar cidades:', error);
            }
        });
    }

    $('#myForm').submit(function (event) {
        event.preventDefault();
    
        var selectedCity = $('#addressCidade').val();
    
        if (selectedCity) {
            // Ajuste a URL para incluir o parâmetro localidade
            var apiUrl = '/api/nomes/nomes?localidade=' + selectedCity.value1;
    
            $.ajax({
                url: apiUrl,
                dataType: 'json',
                success: function (data) {
                    var names = data;
                    console.log('Nomes encontrados:', names);
                    var namesList = $('#namesList');
                    namesList.empty();
                    names.forEach(function (name) {
                        namesList.append('<li>' + name.nome + '</li>');
                    });
                },
                error: function (error) {
                    console.error('Erro na requisição à API:', error);
                    $('#apiResponse').html('Erro na requisição à API: ' + JSON.stringify(error));
                }
            });
        } else {
            alert('Por favor, preencha todos os campos antes de enviar o formulário.');
        }
    });
});
