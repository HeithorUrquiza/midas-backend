# M.I.D.A.S BackEnd

RESTful API para o projeto M.I.D.A.S da disciplina de Fábrica de Software.

## Principais Tecnologias
- **Java JDK 21**
- **Spring Boot 3**
- **Spring Data JPA**
- **PostgresSQL**
- **Lombok**
- **OpenAPI (Swagger)**

## Database

- O projeto contém um arquivo chamado docker-compose.yml. 
Nesse arquivo contém toda estrutura para subir o banco de dados PostgreSQL para rodar a aplicação.
Para subir o banco, basta ir até a pasta raiz do projeto e rodar o comando:

```bash
docker-compose up
```

## Diagrama de Classes

```mermaid
classDiagram
class Client {
  id
  firstName
  lastName
  email
  phone
}

class Commodity {
  Long id
  name
  code
  client_id
}

class Site {
  id
  name
  url
  client_id
}

class Token {
  id
  token
  client_id
}

class Strategy {
  id
  name
  client_id
  commodity_id
}

class Group {
  id
  name
  description
}

class Groups_Clients {
  group_id
  client_id
}

class Strategies_Tokens {
  strategy_id
  token_id
}

class Strategies_Sites {
  strategy_id
  site_id
}

Client "1" -- "N" Strategy
Client "1" -- "N" Commodity
Commodity "1" -- "N" Strategy
Client "1" -- "N" Site
Site "1" -- "N" Strategies_Sites
Strategy "1" -- "N" Strategies_Sites
Client "1" -- "N" Token
Token "1" -- "N" Strategies_Tokens
Strategy "1" -- "N" Strategies_Tokens
Group "1" -- "N" Groups_Clients
Client "1" -- "N" Groups_Clients
```
## Documentação da API (Swagger)

Para ter acesso a documentação da API, insira o seguinte link no navegador ao rodar o projeto em sua máquina local.
#### [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
