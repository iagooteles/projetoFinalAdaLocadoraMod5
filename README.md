# Sistema de Aluguel de Veículos
Este é um sistema de gerenciamento de veículos desenvolvido como parte do 
programa Ada Tech. A aplicação lida com o processo de administração de 
veículos e exibe o valor da diária tanto em reais quanto em dólar, 
utilizando uma [API de cotações](https://docs.awesomeapi.com.br/api-de-moedas) para obter a taxa de câmbio diária..

<p align="center">
     <a alt="Java" href="https://java.com" target="_blank">
        <img src="https://img.shields.io/badge/Java-v21-ED8B00.svg" />
    </a>
    <a alt="Spring Boot" href="https://spring.io/projects/spring-boot" target="_blank">
        <img src="https://img.shields.io/badge/SpringBoot-v3.3.5-lightgreen.
svg" />
    </a>
     <a alt="Maven" href="https://maven.apache.org/index.html" target="_blank">
        <img src="https://img.shields.io/badge/Maven-v4.0.0-CD2335.svg" />
    </a>
    <a alt="H2 database" href="https://www.h2database.com/html/main.html"  target="_blank">
        <img src="https://img.shields.io/badge/H2-v2.1.214-darkblue.svg" />
    </a>
    <a alt="Swagger" href="https://swagger.io/"  
target="_blank">
        <img src="https://img.shields.io/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white" />
    </a>
     <a alt="IntelliJ IDEA" href="https://www.jetbrains.com/idea/" target="_blank">
        <img src="https://img.shields.io/badge/IntelliJ IDEA-v1.18.32-087CFA.svg" />
    </a>
</p>

## Visão Geral do Projeto

O sistema permite:

* Cadastro e gerenciamento de veículos e usuários.
* Processamento de valores de diária em múltiplas moedas.
* Integração com APIs para obtenção de cotações e geração de dados.
* Implementação de autenticação com JWT para segurança.

---

## Sumário

- [Funcionalidades](#funcionalidades)
- [Diagrama de Classes](#diagrama-de-classes)
- [Regras de Negócio](#regras-de-negócio)
- [Acesso ao Projeto](#acesso-ao-projeto)
- [Autores](#autores)

---
## Funcionalidades

### Gerenciamento de Veículos:
- Cadastro, edição e exclusão de veículos, evitando duplicação.
- Atualização da disponibilidade dos veículos para locação.

### Cotações de Câmbio:
- Conversão de valores em reais para dólar utilizando a API de câmbio.

### Autenticação:
- Validação de login com autenticação JWT.
- Implementação de papéis de usuário.

### APIs Externas Integradas:
- [API de Câmbio](https://docs.awesomeapi.com.br/api-de-moedas) para obter a taxa USD-BRL.
- [Gerador de Nomes](https://gerador-nomes.wolan.net/) para gerar nomes de usuários.

---

## Diagrama de Classes
```mermaid
classDiagram
    class User {
        +long id
        +String login
        +String password
        +UserRole role
        +Collection~GrantedAuthority~ getAuthorities()
    }

    class Veiculo {
        +long id
        +String placa
        +String marca
        +String modelo
        +boolean disponivel
        +BigDecimal valorDiariaReais
        +BigDecimal valorDiariaDollar
    }

    class ExchangeClientFeign {
        +ExchangeResponseDTO getExchange()
    }

    class NameClientFeign {
        +List~String~ getRandomName()
    }

    User -- Veiculo : Gerencia
    
```

---
## Regras de Negócio

1. Cadastro de Veículos: Não é permitido cadastrar veículos com a mesma placa.
2. Consulta de Taxa de Câmbio: O valor da diária em dólar é calculado a partir 
do valor em reais, baseado na taxa do dia.
3. Autenticação: Apenas usuários autenticados têm acesso às operações de 
   cadastro e consulta de veículos.
---

## Acesso ao projeto

Para executar o projeto:

1. Clone o repositório.
Certifique-se de ter o Java 21 e o Maven configurados.
Execute o comando:
bash
Copiar código
```bash    
    `git clone git@github.com:biancasanches-dev/locadora-veiculos-ada.git` 
```
2.  Abra o projeto na sua IDE de preferência.
    <br>
---

## Autores
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/AlissonRafSilva">
        <img loading="lazy" src="https://avatars.githubusercontent.com/AlissonRafSilva?v=4" width="115"/><br />
        <sub><b>Alisson Silva</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/biancasanches-dev">
        <img loading="lazy" src="https://avatars.githubusercontent.com/biancasanches-dev?v=4" width="115"/><br />
        <sub><b>Bianca Sanches</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Elisabete-MO">
        <img loading="lazy" src="https://avatars.githubusercontent.com/Elisabete-MO?v=4" width="115"/><br />
        <sub><b>Elisabete Oliveira</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/iagooteles">
        <img loading="lazy" src="https://avatars.githubusercontent.com/iagooteles?v=4" width="115"/><br />
        <sub><b>Iago Teles</b></sub>
      </a>
    </td>
  </tr>
</table>
