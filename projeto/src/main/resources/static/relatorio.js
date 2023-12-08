$(document).ready(function () {
    var chartData = {
        labels: [],
        datasets: [{
            label: 'Frequência',
            data: [],
            backgroundColor: [],
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1
        }]
    };

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
                    label: function (context) {
                        var label = context.label || '';
                        var data = context.dataset.data || 0;
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

    var ctx = document.getElementById('myChart').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: chartData,
        options: chartOptions
    });

    $('#myForm').submit(function (event) {
        event.preventDefault();

        var selectedCity = $('#addressCidade').val();
        var cidade = JSON.parse(selectedCity);
        cidade = cidade.value1;

        if (selectedCity) {
            var apiUrl = '/api/nomes/nomes?localidade=' + cidade;

            $.ajax({
                url: apiUrl,
                dataType: 'json',
                success: function (data) {
                    var names = data;
                    names.sort(function (a, b) {
                        return b.frequencia - a.frequencia;
                    });

                    myChart.data.labels = [];
                    myChart.data.datasets[0].data = [];

                    names.forEach(function (name) {
                        myChart.data.labels.push(name.nome);
                        myChart.data.datasets[0].data.push(name.frequencia);
                        myChart.data.datasets[0].backgroundColor.push(randomColor());
                        myChart.data.datasets[0].borderColor = 'rgba(0,0,0)';
                    });

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

    $('#toggleChart').click(function () {
        if (myChart.config.type === 'bar') {
            myChart.config.type = 'pie';
        } else {
            myChart.config.type = 'bar';
        }

        myChart.options = updateChartOptions(myChart.config.type);
        myChart.update();
    });

    function randomColor() {
        var r = Math.floor(Math.random() * 256);
        var g = Math.floor(Math.random() * 256);
        var b = Math.floor(Math.random() * 256);
        return `rgba(${r}, ${g}, ${b})`;
    }

    function updateChartOptions(type) {
        if (type === 'bar') {
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
