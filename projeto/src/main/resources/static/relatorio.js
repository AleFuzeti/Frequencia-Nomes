$(document).ready(function () {
    // Variável para armazenar os dados do gráfico
    var chartData = {
        labels: [],  // Rótulos do gráfico
        datasets: [{
            label: 'Frequência',
            data: [],  // Valores do gráfico
            backgroundColor: 'rgba(75, 192, 192, 0.2)',  // Cor de preenchimento
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

        // Obtem os dados do formulário
        var regiao = $('#addressRegiao').val();
        var estado = $('#addressEstado').val();
        var cidade = $('#addressCidade').val();

        // Converter a string JSON em um objeto JavaScript
        cidade = JSON.parse(cidade);

        cidade = cidade.value2;

        // Adiciona os dados ao gráfico
        myChart.data.labels.push(regiao + ' - ' + estado + ' - ' + cidade);
        myChart.data.datasets[0].data.push(Math.floor(Math.random() * 100));  // Substitua isso pelos seus dados reais

        // Atualiza o gráfico
        myChart.update();
    });
});