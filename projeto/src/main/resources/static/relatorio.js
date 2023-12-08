$(document).ready(function () {
    // Variável para armazenar os dados do gráfico
    var chartData = {
        labels: [],  // Rótulos do gráfico
        datasets: [{
            label: 'Frequência',
            data: [],  // Valores do gráfico
            backgroundColor: [],  // Cor de preenchimento
            borderColor: 'rgba(75, 192, 192, 1)',  // Cor da borda
            borderWidth: 1  // Largura da borda
        }]
    };

    // Configurações do gráfico
    var chartOptions = {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            y: {
                beginAtZero: true
            }
        },
        plugins: {
            legend: {
                display: false
            },
            tooltip: {
                callbacks: {
                    label: function(context) {
                        var label = context.label || '';
                        var data = context.dataset.data || 0;
                        var total = data.reduce(function(sum, value) {
                            return sum + value;
                        }, 0);
                        var percentage = ((data[context.dataIndex] / total) * 100).toFixed(2);
                        return label + ': ' + percentage + '%';
                    }
                }
            }
        }
    };

    // Obtém o contexto do canvas
    var ctx = document.getElementById('myChart').getContext('2d');

    // Cria o gráfico
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: chartData,
        options: chartOptions
    });

    // Manipula o formulário ao ser enviado
    $('#myForm').submit(function (event) {
        event.preventDefault();

        var selectedCity = $('#addressCidade').val();
        var cidade = JSON.parse(selectedCity);
        cidade = cidade.value1;

        if (selectedCity) {
            // Ajuste a URL para incluir o parâmetro localidade
            var apiUrl = '/api/nomes/nomes?localidade=' + cidade;

            $.ajax({
                url: apiUrl,
                dataType: 'json',
                success: function (data) {
                    var names = data;

                    // Ordena os dados pelo eixo y (frequência) em ordem decrescente
                    names.sort(function (a, b) {
                        return b.frequencia - a.frequencia;
                    });

                    // Limpa os dados anteriores do gráfico
                    myChart.data.labels = [];
                    myChart.data.datasets[0].data = [];

                    // Adiciona os novos dados ao gráfico
                    names.forEach(function (name) {
                        myChart.data.labels.push(name.nome);
                        myChart.data.datasets[0].data.push(name.frequencia);
                        myChart.data.datasets[0].backgroundColor.push(randomColor());  // Use a função randomColor aqui
                    });

                    // Atualiza o gráfico
                    myChart.update();
                },
                error: function (error) {
                    console.error('Erro na requisição à API:', error);
                    $('#namesList').html('Erro na requisição à API: ' + JSON.stringify(error));
                }
            });
        } else {
            alert('Por favor, preencha todos os campos antes de enviar o formulário.');
        }
    });

    // Botão para alternar entre gráficos
    $('#toggleChart').click(function () {
        // Verifica o tipo atual do gráfico e alterna
        if (myChart.config.type === 'bar') {
            // Alterna para gráfico de pizza
            myChart.config.type = 'pie';
        } else {
            // Alterna para gráfico de coluna
            myChart.config.type = 'bar';
        }

        // Atualiza as configurações do gráfico
        myChart.options = updateChartOptions(myChart.config.type);

        // Atualiza o gráfico
        myChart.update();
    });

    function randomColor() {
        var r = Math.floor(Math.random() * 256);
        var g = Math.floor(Math.random() * 256);
        var b = Math.floor(Math.random() * 256);
        return `rgba(${r}, ${g}, ${b}, 0.8)`;
    }
    function updateChartOptions(type) {
        if (type === 'bar') {
            // Configurações do gráfico de barras
            return {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                },
                plugins: {
                    legend: {
                        display: false
                    },
                    tooltip: {
                        callbacks: {
                            label: function (context) {
                                return context.dataset.label + ': ' + context.parsed.y;
                            }
                        }
                    }
                }
            };
        } else if (type === 'pie') {
            // Configurações do gráfico de pizza
            return {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'right',
                        labels: {
                            boxWidth: 10,
                            padding: 10
                        }
                    },
                    tooltip: {
                        callbacks: {
                            label: function (context) {
                                var label = context.label || '';
                                var data = context.parsed;
                                var total = data.reduce(function (sum, value) {
                                    return sum + value;
                                }, 0);
                                var percentage = ((data[context.dataIndex] / total) * 100).toFixed(2);
                                return label + ': ' + percentage + '%';
                            }
                        }
                    },
                    annotation: {
                        annotations: [
                            {
                                drawTime: 'afterDatasetsDraw',
                                type: 'text',
                                mode: 'center',
                                fontFamily: 'Arial',
                                fontStyle: 'bold',
                                position: 'default',
                                backgroundColor: 'rgba(255, 255, 255, 0.8)',
                                fontSize: 14,
                                text: function (context) {
                                    var data = context.chart.data.datasets[0].data;
                                    return data[context.dataIndex];
                                }
                            },
                            {
                                drawTime: 'afterDatasetsDraw',
                                type: 'text',
                                mode: 'center',
                                fontFamily: 'Arial',
                                fontStyle: 'bold',
                                position: 'outside',
                                backgroundColor: 'rgba(255, 255, 255, 0.8)',
                                fontSize: 12,
                                text: function (context) {
                                    var data = context.chart.data.datasets[0].data;
                                    var total = data.reduce(function (sum, value) {
                                        return sum + value;
                                    }, 0);
                                    var percentage = ((data[context.dataIndex] / total) * 100).toFixed(2);
                                    return percentage + '%';
                                }
                            }
                        ]
                    }
                }
            };
        }
    }


});
