import os
import json

###### tratamento dos dados das regiões ######

# Especifica o caminho para o arquivo JSON na pasta "output"
caminho_arquivo = './output/estados_todas_regioes.json'

# Lê o arquivo JSON original com codificação UTF-8
with open(caminho_arquivo, 'r', encoding='utf-8') as arquivo:
    data = json.load(arquivo)

# Itera sobre os elementos do JSON e cria um novo JSON com os dados desejados
novo_data = []
for item in data:
    novo_item = {
        "id": item["id"],
        "sigla": item["sigla"],
        "nome": item["nome"],
        "sigla_reg": item["regiao"]["sigla"]
    }
    novo_data.append(novo_item)

# Especifica o caminho para a pasta "dados tratados"
caminho_pasta_saida = './dados_tratados/'

# Verifica se a pasta "dados tratados" já existe, se não existir, a cria
if not os.path.exists(caminho_pasta_saida):
    os.makedirs(caminho_pasta_saida)

# Especifica o caminho completo para o novo arquivo tratado
caminho_arquivo_saida = os.path.join(caminho_pasta_saida, 'estados_tratados.json')

# Salva o novo JSON tratado na pasta "dados tratados" com codificação UTF-8
with open(caminho_arquivo_saida, 'w', encoding='utf-8') as arquivo_novo:
    json.dump(novo_data, arquivo_novo, ensure_ascii=False, indent=2)

print("Arquivo estados_todas_regioes.json foi tratado, estados_tratados.json criado com sucesso!")

###### tratamento dos dados dos municipios ######

# Especifica o caminho para o arquivo JSON na pasta "output"
caminho_arquivo_entrada = './output/municipios_todas_ufs.json'

# Lê o arquivo JSON original com codificação UTF-8
with open(caminho_arquivo_entrada, 'r', encoding='utf-8') as arquivo:
    data = json.load(arquivo)

# Realiza o tratamento dos dados e cria o novo JSON tratado
novo_data = []
for item in data:
    novo_item = {
        "id": item["id"],
        "nome": item["nome"],
        "sigla_estado": item["microrregiao"]["mesorregiao"]["UF"]["sigla"]
    }
    novo_data.append(novo_item)

# Especifica o caminho completo para o novo arquivo tratado
caminho_arquivo_saida = os.path.join(caminho_pasta_saida, 'municipios_tratados.json')

# Salva o novo JSON tratado na pasta "dados tratados" com codificação UTF-8
with open(caminho_arquivo_saida, 'w', encoding='utf-8') as arquivo_novo:
    json.dump(novo_data, arquivo_novo, ensure_ascii=False, indent=2)

print("Arquivo municipios_todas_ufs.json foi tratado, municipios_tratados.json criado com sucesso!")

###### tratamento dos dados das regioes ######

# Especifica o caminho de entrada para o arquivo "regioes.json" na pasta "output"
caminho_arquivo_entrada = './output/regioes.json'

# Lê o arquivo JSON original com codificação UTF-8
with open(caminho_arquivo_entrada, 'r', encoding='utf-8') as arquivo_entrada:
    data = json.load(arquivo_entrada)

# Especifica o caminho completo para o novo arquivo tratado
caminho_arquivo_saida = os.path.join(caminho_pasta_saida, 'regioes_tratadas.json')

# Salva o JSON no novo arquivo tratado na pasta "dados tratados" com codificação UTF-8 e a mesma identação
with open(caminho_arquivo_saida, 'w', encoding='utf-8') as arquivo_saida:
    json.dump(data, arquivo_saida, ensure_ascii=False, indent=2)

print("Arquivo regioes.json foi tratado, regioes_tratadas.json criado com sucesso!")