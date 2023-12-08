$(document).ready(function () {
    // Popule a lista de estados (substitua com seus dados reais)
    var regions = [];
    var states = [];
    var cities = [];

    // Carregue as regiões do endpoint usando AJAX
    $.ajax({
        url: '/api/localidades/regioes',
        dataType: 'json',
        success: function (data) {
            // Atribua os dados carregados à variável regions
            regions = data;

            // Popule a lista de regiões no elemento select
            var regionSelect = $('#addressRegiao');
            regions.forEach(function (region) {
                regionSelect.append('<option value="' + region.id + '">' + region.nome + '</option>');
            });
            
            // Adicione o manipulador de mudança para atualizar os estados
            regionSelect.on('change', function () {
                setStates();
            });
        },
        error: function (error) {
            console.error('Erro ao carregar regiões:', error);
        }
    });

    // Função para definir os estados
    function setStates() {
        var regionId = $('#addressRegiao').val();

        // Carregue os estados do endpoint usando AJAX
        $.ajax({
            url: '/api/localidades/estados',
            dataType: 'json',
            success: function (data) {
                // Atribua os dados carregados à variável states
                states = data;

                // Filtrar estados pelo ID da região selecionada
                var filteredStates = states.filter(function (state) {
                    return state.regiao.id == regionId;
                });

                // Popule a lista de estados no elemento select
                var stateSelect = $('#addressEstado');
                stateSelect.empty(); // Limpe as opções anteriores
                stateSelect.append('<option value="">Selecione</option>'); // Adicione a opção padrão

                filteredStates.forEach(function (state) {
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
    }

    // Função para definir as cidades
    function setCities() {
        var stateId = $('#addressEstado').val();

        // Carregue as cidades do endpoint usando AJAX
        $.ajax({
            url: '/api/localidades/cidades',
            dataType: 'json',
            success: function (data) {
                // Atribua os dados carregados à variável cities
                cities = data;

                // Filtrar cidades pelo ID do estado selecionado
                var filteredCities = cities.filter(function (city) {
                    return city.microrregiao.mesorregiao.UF.id == stateId;
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

        // Verifique se todos os campos estão preenchidos
        if (temEstado && temCidade) {
            // Exemplo de uso: exiba os valores no console
            console.log('Cidade:', temCidade);
            var apiUrl = 'https://servicodados.ibge.gov.br/api/v2/censos/nomes/ranking?localidade=' + temCidade;
            console.log('URL da requisição:', apiUrl);

            // Faça a requisição AJAX
            $.ajax({
                url: apiUrl,
                dataType: 'json',
                success: function (data) {
                    // Exiba a resposta na div
                    formatAndDisplayData(data);
                    sentToDB(data);
                },
                error: function (error) {
                    console.error('Erro na requisição à API:', error);

                    // Exiba o erro na div
                    $('#apiResponse').html('Erro na requisição à API: ' + JSON.stringify(error));
                }
            });
        } else {
            // Caso algum campo não esteja preenchido, exiba uma mensagem de erro ou tome outra ação apropriada
            alert('Por favor, preencha todos os campos antes de enviar o formulário.');
        }
    });

    // Função para formatar e exibir os dados em uma tabela
    function formatAndDisplayData(data) {
        // Implemente o tratamento e formatação dos dados aqui
        var formattedData = '<table border="1">';
        formattedData += '<tr><th>Nome</th><th>Rank</th><th>Frequência</th></tr>';

        // Verifique se há dados na resposta
        if (data.length > 0 && data[0].res.length > 0) {
            // Exemplo: percorra os itens da resposta e adicione-os à tabela
            data[0].res.forEach(function (item) {
                formattedData += '<tr>';
                formattedData += '<td>' + item.nome + '</td>';
                formattedData += '<td>' + item.ranking + '</td>';
                formattedData += '<td>' + item.frequencia + '</td>';
                formattedData += '</tr>';
            });
        } else {
            // Caso não haja dados na resposta, exiba uma mensagem na tabela
            formattedData += '<tr><td colspan="3">Nenhum dado disponível</td></tr>';
        }

        formattedData += '</table>';

        // Exiba a tabela na div
        $('#apiResponse').html(formattedData);

    }
});

function sentToDB(data) {
    // Configuração da requisição
    const requestOptions = {
      method: 'POST', // Você pode ajustar o método conforme necessário
      headers: {
        'Content-Type': 'application/json'
        // Adicione headers adicionais se necessário
      },
      body: JSON.stringify(data) // Converte o objeto JSON para uma string
    };
  
    // Faz a requisição para o controlador /nomes
    fetch('/nomes', requestOptions)
      .then(response => {
        // Verifica se a resposta da requisição foi bem-sucedida
        if (!response.ok) {
          throw new Error('Erro na requisição para /nomes');
        }
        // Pode retornar a resposta se necessário
        return response.json();
      })
      .then(data => {
        // Lida com os dados da resposta, se necessário
        console.log('Resposta do servidor:', data);
      })
      .catch(error => {
        // Lida com erros durante a requisição
        console.error('Erro na requisição:', error.message);
      });
}
