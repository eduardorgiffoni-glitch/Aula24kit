# StarLog Logistics — Aula 24: Kit MVC

Projeto Java Swing com arquitetura **Model–View–Controller**.

## Estrutura
- `src/br/com/starlog/model/Carga.java`: regras de validação, seguro de 2% e sobretaxa de R$ 150,00 para categoria PERIGOSA ou peso acima de 100 kg.
- `src/br/com/starlog/view/TelaCadastroCarga.java`: interface Swing, tabela e relatório.
- `src/br/com/starlog/controller/CargaController.java`: cadastro, conversão, erros amigáveis e prevenção de códigos duplicados.
- `src/br/com/starlog/main/MainApp.java`: inicialização na EDT.
- `test/br/com/starlog/model/CargaTest.java`: testes JUnit 5.

## Execução
Importe no Eclipse como projeto Java e execute `br.com.starlog.main.MainApp`.

Ou execute pelo terminal:

```bash
mkdir -p bin
javac -encoding UTF-8 -d bin src/br/com/starlog/model/*.java src/br/com/starlog/view/*.java src/br/com/starlog/controller/*.java src/br/com/starlog/main/*.java
java -cp bin br.com.starlog.main.MainApp
```

## Testes manuais
- Peso `abc`: mensagem amigável, sem travamento.
- Valor `-50`: validação no Model.
- Três cargas válidas: tabela e relatório atualizados.
