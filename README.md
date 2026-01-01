# 📱 Sistema de Gerenciamento de Dispositivos Móveis (MDM)

Este projeto é um sistema web RESTful desenvolvido com **Java** e **Spring Boot**, com foco em auxiliar empresas a realizarem o **controle e gerenciamento de dispositivos móveis corporativos**, bem como a organização de usuários e departamentos.

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.4
- Spring Web (REST)
- Spring Data JPA
- Hibernate
- PostgreSQL
- Hibernate Validator
- Swagger (OpenAPI)
- MapStruct
- OpenCSV

---

## ⚙️ Funcionalidades Implementadas

- ✅ **Camadas** bem definidas: `Controller`, `Service`, `Repository`, `DTO`, `Enums`, `CSV`, `Exception`, `Mapper`
- ✅ **Registro** e **controle** sobre `Departamentos`, `Usuários`, `Dispositivos`, `Agentes` e `Comandos`
- ✅ Criação de **exceptions personalizadas** para regras de negócio
- ✅ **Tratamento global de exceções** com `@RestControllerAdvice`
- ✅ **Validação** de dados com Hibernate Validator
- ✅ **Mapeameto** de DTOs para Entidades usando a API **MapStruct**
- ✅ Endpoint que permite o cadastro de múltiplos `Usuários` atraves da **importação de um arquivo `.csv`**
- ✅ Documentação usando **Swagger (OpenAPI)**

---

## 🧱 Estrutura do Projeto

```java
src/
└── main/
      └── java/
            └── com.alessandromelo/
                      ├── config/  //Configurações da aplicação (OpenAPI por exemplo)
                      ├── controller/  //Endpoints REST
                      ├── csv/  //Operações voltadas a importação e exportação de arquivos .csv
                      ├── dto/  //Objetos de transferência de dados
                      ├── entity/  //Entidades JPA
                      ├── enums/ 
                      ├── exceptions/  //Exceptions personalizadas
                      ├── exceptionhandler/  //Tratamento global de exceções
                      ├── mapper/  //Conversão entre entidades e DTOs usando MapStruct
                      ├── repository/  //Interface com o banco de dados
                      └── service/  //Regras de negócio
```

---

## ▶️ Como Executar Localmente

### Pré-requisitos:
- Java 21
- PostgreSQL
- Maven

### Passos:

1. Clone o repositório:
```bash
git clone https://github.com/AlessandroMelo22/mobile-device-management.git
```
2. Configure as credenciais do banco no `application.properties` ou `application.yml`.

3. Execute a aplicação:
```
./mvnw spring-boot:run
```
Acesse:
```
http://localhost:8080
```
## 📌 Status do Projeto
**🚧 Em evolução contínua**  
O sistema já está funcional e com boa cobertura das funcionalidades principais. Próximas etapas previstas:

- Testes automatizados
- Integração da API a um Agente externo
- Novas funcionalidades envolvendo arquivos `.csv`


## 🤝 Contribuições
Sinta-se à vontade para abrir issues ou sugestões. Toda contribuição é bem-vinda!


## 👤 Autor
Desenvolvido por [Alessandro Melo](https://github.com/AlessandroMelo22)   
🔗 [LinkedIn](https://www.linkedin.com/in/alessandro-melo-dev/)


## 📝 Licença
Este projeto está licenciado sob a MIT License.

