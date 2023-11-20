$(document).ready(function () {
    // Popule a lista de estados (substitua com seus dados reais)
    var states = [
        { id: 1, name: 'Paraná' },
        { id: 2, name: 'Santa Catarina' },
        // Adicione mais estados conforme necessário
    ];

    var cities = [
        // Adicione as cidades correspondentes aos estados
        { stateId: 1, id: 101, name: 'Arapongas' },
        { stateId: 1, id: 102, name: 'Londrina' },
        { stateId: 1, id: 103, name: 'Rolândia' },
        { stateId: 2, id: 201, name: 'Blumenau' },
        { stateId: 2, id: 202, name: 'Florianópolis' },
        // Adicione mais cidades conforme necessário
    ];

    // Popule a lista de estados no elemento select
    var stateSelect = $('#addressEstado');
    states.forEach(function (state) {
        stateSelect.append('<option value="' + state.id + '">' + state.name + '</option>');
    });

    // Atualize as cidades quando um estado for selecionado
    stateSelect.on('change', function () {
        setCities();
    });

    // Função para definir as cidades
    function setCities() {
        var stateId = stateSelect.val();

        // Filtrar cidades pelo estado selecionado
        var filteredCities = cities.filter(function (city) {
            return city.stateId == stateId;
        });

        // Popule a lista de cidades no elemento select
        var citySelect = $('#addressCidade');
        citySelect.empty(); // Limpe as opções anteriores
        citySelect.append('<option value="">Selecione</option>'); // Adicione a opção padrão

        filteredCities.forEach(function (city) {
            citySelect.append('<option value="' + city.id + '">' + city.name + '</option>');
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

