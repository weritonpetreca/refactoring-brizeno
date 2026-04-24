# Capítulo 1: Introdução e Cultura de Testes

Antes de aplicar qualquer Padrão de Projeto ou técnica de refatoração, é obrigatório garantir que o comportamento do sistema esteja protegido. A refatoração só é segura quando amparada por uma suíte de testes confiável.

## O Padrão AAA (Arrange, Act, Assert)

Para manter os testes limpos, expressivos e profissionais, todos os testes deste repositório devem seguir estritamente a anatomia AAA:

1. **Arrange (Arranjo):** Preparação do cenário. Onde instanciamos os objetos, configuramos os *Mocks* e definimos os dados de entrada.
2. **Act (Ação):** Onde a ação-alvo (o método sendo testado) é invocada. Deve ser geralmente uma única linha de código.
3. **Assert (Asserção):** Onde validamos se o resultado da ação corresponde à expectativa ou se as dependências corretas foram chamadas.

### Exemplo de Estrutura:
```java
@Test
void deveRetornarResultadosPorPaginaQuandoEspecificado() {
    // Arrange
    Parametros parametros = new Parametros("Um produto", 20);
    
    // Act
    Criterio criterio = Busca.criarCriterio(parametros);
    
    // Assert
    assertEquals(20, criterio.getResultadosPorPagina());
}
```
## Dublês de Teste (Mockito)
Quando testamos uma classe que interage com serviços externos ou lógicas pesadas (como banco de dados ou chamadas REST), utilizamos o **Mockito** para isolar o comportamento.

* `mock()`: Cria um objeto falso que imita a interface da dependência.

* `when().thenReturn()`: Ensina o mock a retornar um dado específico quando determinado método for chamado.

* `verify()`: Confirma que o nosso código interagiu com a dependência corretamente.

***Nota DevSecOps:** Mocks são excelentes para o módulo `book-examples`. No entanto, no módulo `real-world-implementations`, daremos preferência aos **Testcontainers** para testes de integração com o banco de dados real, garantindo máxima fidelidade.*