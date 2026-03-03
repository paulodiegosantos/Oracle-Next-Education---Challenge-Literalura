# Literalura - Challenger Oracle Next Education

Aplicação para consulta e registro de livros e autores, desenvolvida com Spring Boot e JPA/Hibernate, com foco em manipulação de dados, persistência em banco de dados e consultas personalizadas.

---

## 🚀 Demonstração

O usuário inicia a aplicação e tem acesso a um menu com as opções:

- Buscar livro pelo título
- Listar livros registrados
- Listar autores registrados
- Listar autores vivos em determinado ano
- Listar livros por idioma

<img width="484" height="248" alt="image" src="https://github.com/user-attachments/assets/fec7fa54-97c2-4ff9-b667-87078b009946" />

A aplicação exibe os livros cadastrados com informações sobre título, autor, idioma e downloads, e permite consultar autores vivos em um ano específico ou livros em um idioma específico.

---

## 🧠 Objetivo do Projeto

- Colocar em prática conhecimentos de Spring Boot, JPA/Hibernate, consumo de dados via DTO e persistência em banco de dados.
- Implementar consultas personalizadas e lógica de filtragem de dados (autores vivos em determinado ano, livros por idioma).
- Desenvolver uma aplicação estruturada, com menu interativo via terminal, manipulação de listas e tratamento de dados relacionais.
- Esse desafio faz parte do curso Tech Foundation da Oracle Next Education (ONE).

---

## ⚙️ Funcionalidades

- ✔️ Cadastro de livros e autores
- ✔️ Consulta de livros por título, idioma e autor
- ✔️ Listagem de autores e livros
- ✔️ Consulta de autores vivos em determinado ano
- ✔️ Persistência em banco PostgreSQL via JPA/Hibernate
- ✔️ Menu interativo via terminal

---

## 🛠️ Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven

---

## 🔌 Como Executar o Projeto

1. Clone o repositório
```bash
git clone https://github.com/paulodiegosantos/Oracle-Next-Education---Challenge-Literalura.git
```
2. Entre na pasta do projeto
```bash
cd Oracle-Next-Education---Challenge-Literalura
```

3. Configure o banco de dados no application.properties:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=[SENHA]
spring.jpa.hibernate.ddl-auto=update
```

Observação: Caso outra pessoa abra o projeto e não tenha um banco chamado literalura, ela precisará criar o banco manualmente ou alterar a URL para apontar para um banco existente. As tabelas e colunas serão criadas automaticamente pelo Hibernate.

4. Execute a aplicação

```bash
mvn spring-boot:run
```

## 📖 Aprendizados

Durante o desenvolvimento do projeto, foram aplicados e reforçados diversos conhecimentos:

Configuração de Spring Boot e integração com PostgreSQL

Mapeamento de entidades com JPA/Hibernate

Criação de DTOs e conversão para entidades

Manipulação de listas e consultas personalizadas via Spring Data JPA

Implementação de menus interativos via terminal

Tratamento de dados relacionais e persistência de relacionamentos OneToMany

## 👨‍💻 Autor

Paulo Diego Dos Santos

LinkedIn: https://www.linkedin.com/in/paulodiegosantos/

GitHub: https://github.com/paulodiegosantos
