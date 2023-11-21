$(document).ready(function () {
    // Popule a lista de estados (substitua com seus dados reais)
    var states = [];
    var cities = [];

    // Carregue os estados do arquivo JSON usando AJAX
    $.ajax({
        url: '../../../../dados_tratados/estados_tratados.json',
        dataType: 'json',
        success: function (data) {
            // Atribua os dados carregados à variável states
            states = data;

            // Popule a lista de estados no elemento select
            var stateSelect = $('#addressEstado');
            states.forEach(function (state) {
                stateSelect.append('<option value="' + state.id + '">' + state.nome + '</option>');
            });

            // Adicione o manipulador de mudança para atualizar as cidades
            stateSelect.on('change', function () {
                setCities();
            });
        },
        error: function (error) {
            console.error('Erro ao carregar estados:', error);
        }
    });

    // Função para definir as cidades
    function setCities() {
        var stateId = $('#addressEstado').val();

        // Carregue as cidades do arquivo JSON usando AJAX
        $.ajax({
            url: '../../../../dados_tratados/municipios_tratados.json',
            dataType: 'json',
            success: function (data) {
                // Atribua os dados carregados à variável cities
                cities = data;

                // Filtrar cidades pelo estado selecionado
                var filteredCities = cities.filter(function (city) {
                    // Verifique se os primeiros dígitos do ID da cidade correspondem ao ID do estado
                    return city.id.toString().startsWith(stateId);
                });

                // Popule a lista de cidades no elemento select
                var citySelect = $('#addressCidade');
                citySelect.empty(); // Limpe as opções anteriores
                citySelect.append('<option value="">Selecione</option>'); // Adicione a opção padrão

                filteredCities.forEach(function (city) {
                    citySelect.append('<option value="' + city.id + '">' + city.nome + '</option>');
                });
            },
            error: function (error) {
                console.error('Erro ao carregar cidades:', error);
            }
        });
    }

    // Manipulador de envio do formulário
    $('#myForm').submit(function (event) {
        // Evite o comportamento padrão de envio do formulário
        event.preventDefault();

        // Obtenha os nomes selecionados
        var temEstado = $('#addressEstado').val();
        var temCidade = $('#addressCidade').val();
        var estadoSelecionado = $('#addressEstado option:selected').text();
        var cidadeSelecionada = $('#addressCidade option:selected').text();
        var nomeDigitado = $('#name').val();

        // Verifique se todos os campos estão preenchidos
        if (temEstado && temCidade && nomeDigitado) {
            // Exemplo de uso: exiba os valores no console
            console.log('Estado:', estadoSelecionado);
            console.log('Cidade:', cidadeSelecionada);
            console.log('Nome:', nomeDigitado);

            // Adicione aqui a lógica para enviar os dados para o servidor ou realizar outras operações necessárias
        } else {
            // Caso algum campo não esteja preenchido, exiba uma mensagem de erro ou tome outra ação apropriada
            alert('Por favor, preencha todos os campos antes de enviar o formulário.');
        }
    });
    
    // Botão fora do formulário para redirecionar para data.html
    $('#viewData').on('click', function () {
        window.location.href = 'data.html';
    });

});

