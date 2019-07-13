# The Bot Api - Exercicio ![](https://img.shields.io/badge/build-passing-brightgreen.svg) ![](https://img.shields.io/static/v1.svg?label=coverage&message=65.7%&color=%3CCOLOR%3E)

### Pré Requisitos
- Java
- Maven
- Docker

### Bibliotecas Utilizadas
- spring-boot-starter-web
- spring-boot-starter-test
- spring-boot-starter-data-jpa
- flyway-core
- postgresql
- lombok

#### Inicializando o container do banco de dados PostgreSQL
Na raiz do projeto the-bot-api executar o comando abaixo:

`$ docker-compose up`

Criando o usuário de acesso ao banco de dados

`$ docker exec -it docker_for_postgres psql -U postgres -c "CREATE USER postgresdev WITH SUPERUSER PASSWORD 'postgresdev'"`

Retorno esperado: **CREATE ROLE**

Criando a data base que será utilizado para persistencia dos dados

`$ docker exec -it docker_for_postgres psql -U postgres -c "create database db_bot_api"`

Retorno esperado: **CREATE DATABASE**

### Compilação
Acessar a raiz the-bot-api do projeto e executar o seguinte comando:

`$ mvn clean install`

### Execução
O arquivo compilado foi gerado dentro da pasta ../target/the-bot-api.jar
Para executá-lo será necessário o seguinte comando:

`$ java -jar target/the-bot-api.jar`

### Flyway

Foi utilizado o Flyway para controle de versionamento e execução dos scripts SQL. Ele irá automáticamente executar os scripts necessários para funcionamento da aplicação.
Para validar a criação das tabelas deverá acessar a raiz do projeto the-bot-api e executar o seguinte comando:

`$ mvn flyway:info`

### Documentação dos serviços

```json
GET > http://localhost:8080/bots/
[
    {
        "id": "8aa28e9c-a44f-11e9-a9c2-0242ac120002",
        "name": "Iolanda"
    },
    {
        "id": "8aa2bf0c-a44f-11e9-a9c2-0242ac120002",
        "name": "Benito de Paula"
    },
    {
        "id": "8aa2e3ba-a44f-11e9-a9c2-0242ac120002",
        "name": "Nazzi"
    },
    {
        "id": "8aa3203c-a44f-11e9-a9c2-0242ac120002",
        "name": "Lulu Santos"
    },
    {
        "id": "8aa34530-a44f-11e9-a9c2-0242ac120002",
        "name": "Tião Carreiro"
    }
]

GET:id > http://localhost:8080/bots/8aa28e9c-a44f-11e9-a9c2-0242ac120002
{
    "id": "8aa28e9c-a44f-11e9-a9c2-0242ac120002",
    "name": "Iolanda"
}

POST > http://localhost:8080/bots
{
    "name": "Danilo"
}

PUT:id > http://localhost:8080/bots/13edaa3f-b43f-400a-adc3-909fc09ffcbc
{
    "name": "Dominguinhos"
}

DELETE:id > http://localhost:8080/bots/13edaa3f-b43f-400a-adc3-909fc09ffcbc
{
    "id": "91a08b92-ca63-4b3d-b215-bd863b4d662b",
    "name": "Dominguinhos"
}

DELETE > http://localhost:8080/bots
Observacao: irá deletar todos os registros.

POST > http://localhost:8080/messages/

{
	"conversationId": "9965ada8-3448-4acd-a1b7-d688e68fe9a1",
	"timestamp": "2019-07-12T18:17:00.691+0000",
	"from": "14edd3b3-3f75-40df-af07-2a3813a79ce9",
	"to": "12b9f842-ee97-11e8-9443-0242ac120002",
	"text": "Teste envio"
}

GET:id > http://localhost:8080/messages/0d9cc198-d3b7-4138-88a7-15aeba939628

{
    "id": "0d9cc198-d3b7-4138-88a7-15aeba939628",
    "conversationId": "9965ada8-3448-4acd-a1b7-d688e68fe9a1",
    "timestamp": "2019-07-12T18:17:00.691+0000",
    "from": "14edd3b3-3f75-40df-af07-2a3813a79ce9",
    "to": "12b9f842-ee97-11e8-9443-0242ac120002",
    "text": "Teste envio"
}


GET > http://localhost:8080/messages?conversationId=9965ada8-3448-4acd-a1b7-d688e68fe9a1
[
    {
        "id": "0d9cc198-d3b7-4138-88a7-15aeba939628",
        "conversationId": "9965ada8-3448-4acd-a1b7-d688e68fe9a1",
        "timestamp": "2019-07-12T18:17:00.691+0000",
        "from": "14edd3b3-3f75-40df-af07-2a3813a79ce9",
        "to": "12b9f842-ee97-11e8-9443-0242ac120002",
        "text": "Teste envio"
    },
    {
        "id": "53e29614-6fdc-4bd8-a730-4ed6ce07ba93",
        "conversationId": "9965ada8-3448-4acd-a1b7-d688e68fe9a1",
        "timestamp": "2019-07-12T18:18:00.691+0000",
        "from": "14edd3b3-3f75-40df-af07-2a3813a79ce9",
        "to": "12b9f842-ee97-11e8-9443-0242ac120002",
        "text": "Teste reenvio"
    },
    {
        "id": "cc02a2d0-7f46-41a8-a0ff-855c56aa768d",
        "conversationId": "9965ada8-3448-4acd-a1b7-d688e68fe9a1",
        "timestamp": "2019-07-12T18:18:01.000+0000",
        "from": "12b9f842-ee97-11e8-9443-0242ac120002",
        "to": "14edd3b3-3f75-40df-af07-2a3813a79ce9",
        "text": "Recebido"
    }
]
```
