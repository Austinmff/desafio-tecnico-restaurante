# 🍽️ Desafio Técnico – Sistema de Votação de Restaurante

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2_Database-In_Memory-blue?style=for-the-badge)
![Swagger](https://img.shields.io/badge/Swagger-API_Docs-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)

> Solução desenvolvida para o Desafio Técnico da **DBServer**.

Este projeto consiste numa API REST robusta para gerir a votação diária de almoço dos colaboradores. O sistema garante a integridade dos votos, apura o vencedor do dia e cumpre estritamente as regras de negócio propostas, utilizando práticas modernas de desenvolvimento em Java.

---

## 📑 Índice

* [Sobre o Projeto](#-sobre-o-projeto)
* [Estrutura do Projeto](#-estrutura-do-projeto)
* [Tecnologias](#-tecnologias)
* [Regras de Negócio](#-regras-de-negócio)
* [Documentação da API](#-documentação-da-api-endpoints)
* [Como Executar](#-como-executar)

---

## 📋 Sobre o Projeto

O objetivo da aplicação é democratizar a escolha do restaurante de forma automatizada. A arquitetura foi desenhada para ser escalável, separando claramente as responsabilidades entre exposição da API, regras de negócio e persistência.

A solução inclui tratamento de exceções centralizado (`exception handler`) e testes unitários para validar a lógica principal.

---

## 📂 Estrutura do Projeto

A organização do código segue o padrão de arquitetura em camadas, garantindo desacoplamento e facilidade de manutenção:

```text
src/main/java/br/comdbserver/almocovotacao
├── config       # Configurações globais (ex: OpenAPI/Swagger)
├── controller   # Camada REST (Endpoints)
├── dto          # Objetos de Transferência de Dados (Request/Response)
├── entity       # Entidades JPA (Banco de Dados)
├── exception    # Tratamento centralizado de erros e exceções
├── repository   # Interfaces de persistência (Spring Data)
└── service      # Regras de negócio e lógica da votação

```

Também foram implementados testes para garantir a qualidade:

* `VotacaoServiceTest`: Validação das regras de negócio (voto único, apuração).

---

## 🚀 Tecnologias

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3
* **Base de Dados:** H2 Database (Em memória)
* **Documentação:** OpenAPI / Swagger UI
* **Gerenciamento de Dependências:** Maven

---

## ⚖️ Regras de Negócio

O núcleo da aplicação respeita as seguintes restrições lógicas:

1. **Unicidade do Voto:** Um profissional não pode votar duas vezes no mesmo dia.
2. **Identificação:** O voto é registado associando o **ID do profissional** ao **Nome do restaurante**.
3. **Apuração:** O sistema retorna o restaurante vencedor do dia atual com a contagem total de votos.
4. **Validação:** Tentativas de votos inválidos ou duplicados retornam mensagens de erro claras via `ControllerAdvice`.

---

## 🔗 Documentação da API (Endpoints)

### 👤 Profissionais

#### Criar Profissional

`POST /profissionais`

* **Body:**
```json
{
  "nome": "João"
}

```



#### Listar Profissionais

`GET /profissionais`

---

### 🍴 Restaurantes

#### Criar Restaurante

`POST /restaurantes`

* **Body:**
```json
{
  "nome": "Restaurante A"
}

```



#### Listar Restaurantes

`GET /restaurantes`

---

### 🗳️ Votação e Apuração

#### Realizar Voto

`POST /votacao/votar`

* **Body:**
```json
{
  "profissionalId": 1,
  "nomeRestaurante": "Restaurante A"
}

```



#### Resultado do Dia

`GET /votacao/resultado`

* **Response (Exemplo):**
```json
{
  "restauranteId": 1,
  "restauranteNome": "Restaurante A",
  "totalVotos": 3
}

```



---

## 📖 Swagger UI

Para testar os *endpoints* visualmente:

👉 **http://localhost:8080/swagger-ui.html**

---

## 📦 Como Executar

### Pré-requisitos

* **Java 17** (JDK) instalado.
* **Git** instalado.

### Passo a passo

1. **Clonar o repositório:**
```bash
git clone [https://github.com/Austinmff/desafio-tecnico-restaurante.git](https://github.com/Austinmff/desafio-tecnico-restaurante.git)
cd desafio-tecnico-restaurante

```


2. **Compilar e Executar:**
* **Linux / macOS:**
```bash
./mvnw spring-boot:run

```


* **Windows:**
```bash
./mvnw spring-boot:run

```




3. **Base de Dados (H2 Console):**
* **URL:** `http://localhost:8080/h2-console`
* **JDBC URL:** `jdbc:h2:mem:almocovotacao` 
* **User:** `sa`
* **Password:** *(vazio)*



---

**Desenvolvido por Austinmff**
