# 📊 Análise de Frequência de Nomes por Municípios

Este repositório contém o sistema desenvolvido para o **trabalho de Coleta e Análise de Dados Abertos** da disciplina de **Banco de Dados I**.

O objetivo do projeto é analisar a **frequência de nomes por município no Brasil**, associando os dados obtidos a partir de APIs públicas para gerar **relatórios e visualizações informativas** sobre a distribuição dos nomes mais comuns em diferentes regiões do país.

## 🌐 APIs utilizadas

Trabalhamos com duas APIs abertas disponibilizadas pelo IBGE:

- 🔗 [API Nomes](https://servicodados.ibge.gov.br/api/docs/nomes?versao=2): dados estatísticos sobre frequência de nomes no Brasil
- 🔗 [API de Localidades](https://servicodados.ibge.gov.br/api/docs/localidades): informações geográficas e administrativas sobre os municípios

## 🗂️ Estrutura de Dados

- Modelo Entidade-Relacionamento (MER)
- Modelo Relacional
- Integração com banco de dados e arquitetura em camadas:
  - `Model`
  - `DAO`
  - `Service`
  - `Frontend`

## 🚀 Linha do tempo do projeto

| Data         | Atividades Realizadas |
|--------------|------------------------|
| 02–07/10     | Definição do tema e pesquisa sobre integração com APIs |
| 08/10        | Criação do repositório e início da implementação dos requests |
| 09/10        | Finalização dos requests, criação do MER e modelo relacional; slides preparados |
| 20/10        | Coleta dos dados de ranking de nomes |
| 02/11        | Criação do projeto Maven |
| 16–21/11     | Implementação das classes `models`, `dao` e `service` |
| 01/12        | Início do desenvolvimento do front-end |
| 05–07/12     | Ajustes de CSS e implementação de gráficos |

## 💻 Tecnologias Utilizadas

- Java + Maven
- APIs REST (IBGE)
- HTML, CSS, JavaScript
- Gráficos com bibliotecas JS (ex: Chart.js ou similares)
- Banco de Dados relacional

## 🎯 Objetivo Final

Permitir ao usuário consultar e visualizar, por meio de gráficos e relatórios, **quais são os nomes mais comuns em cada município brasileiro**, com base nos dados públicos fornecidos pelo IBGE.

---

> Projeto com fins acadêmicos, voltado ao desenvolvimento de habilidades em análise de dados, consumo de APIs públicas e integração de sistemas web com banco de dados.
