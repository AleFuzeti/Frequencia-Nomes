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
                regionSelect.append('<option value="' + region.id + '">' + region.nome + '</option>');
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
        var regionId = $('#addressRegiao').val();
        var regiaoo = $('#addressRegiao');
        console.log('Região:', regiaoo);
        console.log('Região selecionada:', regionId);
        $.ajax({
            url: '/api/localidades/estados',
            dataType: 'json',
            success: function (data) {
                states = data;
                console.log('Estados:', states);

                var filteredStates = states.filter(function (state) {
                    return state.regiao.id == regionId;
                });

                console.log('Estados filtrados:', filteredStates);

                var stateSelect = $('#addressEstado');
                stateSelect.empty();
                stateSelect.append('<option value="">Selecione</option>');

                console.log('Estados filtrados:', filteredStates);

                filteredStates.forEach(function (state) {
                    stateSelect.append('<option value="' + state.id + '">' + state.nome + '</option>');
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
        var estadoo = $('#addressEstado');
        var stateId = $('#addressEstado').val();
        console.log('Estado selecionado:', stateId);
        console.log('Estado:', estadoo);
        $.ajax({
            url: '/api/localidades/cidades',
            dataType: 'json',
            success: function (data) {
                cities = data;

                var filteredCities = cities.filter(function (city) {
                    return city.sigla_estado == stateId;
                });

                var citySelect = $('#addressCidade');
                citySelect.empty();
                citySelect.append('<option value="">Selecione</option>');

                filteredCities.forEach(function (city) {
                    citySelect.append('<option value="' + city.id + '">' + city.nome + '</option>');
                });
            },
            error: function (error) {
                console.error('Erro ao carregar cidades:', error);
            }
        });
    }

    $('#myForm').submit(function (event) {
        event.preventDefault();

        var selectedState = $('#addressEstado').val();
        var selectedCity = $('#addressCidade').val();

        if (selectedState && selectedCity) {
            console.log('Cidade:', selectedCity);

            // Ajuste a URL conforme necessário com base na estrutura do seu controlador
            var apiUrl = '/api/localidades/' + selectedCity;

            $.ajax({
                url: apiUrl,
                dataType: 'json',
                success: function (data) {
                    formatAndDisplayData(data);
                    sendToDB(data);
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

    function formatAndDisplayData(data) {
        // Implemente a formatação dos dados conforme necessário
    }

    function sendToDB(data) {
        var requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        };

        fetch('/nomes', requestOptions)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro na requisição para /nomes');
                }
                return response.json();
            })
            .then(data => {
                console.log('Resposta do servidor:', data);
            })
            .catch(error => {
                console.error('Erro na requisição:', error.message);
            });
    }
});
