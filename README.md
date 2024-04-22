# customer-credit
## Aplicação desenvolvida em Kotlin + Spring Framework
## Aplicação para um sistema capaz de fornecer crédito a um cliente.
### Testes integrados e unitários mal desenvolvidos ( necessário um estudo aprofundado )




```mermaid
classDiagram
  class Customer {
    -Id: Integer
    -FirstName: String
    -LastName: String
    -Cpf: String
    -Email: String
    -Password: String
    -Income: BigDecimal
    -Address: Adress
  }
   class Adress{
    -Street: String
    -ZipCode: Integer
  }

  class Credit {
    -Id: Integer
    -CreditCode: UUID
    -CreditValue: String
    -DayFirstIntallment: String
    -NumberOfInstallment: String
    -Status: Status
  }

  Customer "1..*" -- "*..1" Credit
  Customer "1" -- "1" Adress
```
