# Arcnal Edtech

## Descrição

Sistema de gerenciamento de questões de concursos e vestibulares, permitindo organizar questões por matéria, assunto, banca e criar revisões personalizadas.

## Tecnologias

- **Java 21**
- **Spring Boot 3.4.12**
- **Spring Data JPA**
- **PostgreSQL**
- **MapStruct** (para mapeamento de DTOs)
- **JWT** (para autenticação)
- **Maven** (gerenciamento de dependências)

## Funcionalidades

A API oferece endpoints para gerenciar:

- **Usuários** - Cadastro e autenticação
- **Questões** - CRUD de questões com metadados
- **Matérias** - Organização por matérias
- **Assuntos** - Categorização de questões por assunto
- **Bancas** - Registro de bancas organizadoras
- **Revisões** - Sistema de revisão espaçada

## ⚙️ Configuração

### Pré-requisitos

- Java 21 ou superior
- PostgreSQL
- Maven 3.6+

### Variáveis de Ambiente

Configure as seguintes variáveis de ambiente:

```properties
DB_USERNAME=seu_usuario_postgres
DB_PASSWORD=sua_senha_postgres
DB_URL=jdbc:postgresql://localhost:5432/arcnal
JWT_SECRET=sua_chave_secreta_jwt
```

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── br/com/arcnal/arcnal/
│   │       ├── application/      # Camada de aplicação (DTOs, Services, Mappers)
│   │       ├── domain/           # Camada de domínio (Entidades, Repositórios)
│   │       ├── infra/            # Infraestrutura (Segurança, Utilitários)
│   │       └── presentation/     # Camada de apresentação (Controllers)
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── br/com/arcnal/arcnal/
            └── service/          # Testes unitários
```

## 🧪 Testes

Execute os testes com:
```bash
./mvnw test
```
