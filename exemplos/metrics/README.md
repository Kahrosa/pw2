# Smallrye Metrics

Instale o quarkus acessando este site https://code.quarkus.io/ 
Selecione SmallRye Metrics e clique em generate application, ao fazer o download, abra na IDE recomendada para este tutorial, o VS Code.
ou utilize este comando:
>mvn io.quarkus:quarkus-maven-plugin:2.1.3.Final:create \
    -DprojectGroupId=ifrs.metrics \
    -DprojectArtifactId=ifrs-metrics-service \
    -DclassName="ifrs.metrics.NumberResource" \
    -Dpath="/" \
    -Dextensions="resteasy,smallrye-metrics"
cd ifrs-metrics-service

O SmallRye Metrics permite que os aplicativos reúnam várias métricas e estatísticas que fornecem informações sobre o que ocorre dentro do aplicativo.
As métricas podem ser lidas usando o formato JSON ou o formato OpenMetrics, para que possam ser processadas por ferramentas adicionais.
Neste exemplo o sistema irá determinar se o número informado é par ou não, a classe de implementação é anotada com algumas anotações de métricas, para serem coletadas conforme o uso da aplicação.
Esse comando gera um projeto Maven, importando o smallrye-metrics

##### Estas são as métricas utilizadas:
- performChecks: um contador que é aumentado em um cada vez que o usuário pergunta sobre um número.
- highestPar: Este é um medidor que armazena o maior número que foi perguntado pelo usuário e que foi determinado como par.
- checksTimer: Este é um cronômetro, portanto, uma métrica composta que avalia quanto tempo os testes levam.
- min: A menor duração necessária para realizar o teste, provavelmente foi realizado para um pequeno número.
- max: A duração mais longa, com um grande número.
- mean: O valor médio das durações medidas.
stddev: O desvio padrão.
- count: O número de observações (será o mesmo valor que performedChecks).
- p50, p75, p95, p99, p999: Percentuais da duração das medições.
- meanRate, oneMinRate, fiveMinRate, fifteenMinRate: Taxa de transferência média e taxa de transferência média móvel exponencialmente ponderada em um, cinco e quinze minutos.

Agora vamos ao código:


```@Path("/")
public class NumberChecker {
    private long highestPar = 1;
    @GET
    @Path("/{number}")
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "performedChecks", description = "How many primality checks have been performed.")
    @Timed(name = "checksTimer", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
    public String checkParImpar(@PathParam long number) {
        if (number < 1) {
            return "Apenas numeros naturais";
        }
        if (number % 2 == 1) {
            return number + " é impar";
        }
        if (number > highestPar) {
            highestPar = number;
        }
        return number + " é par";
    }
    //o numero ultimo numero par mais alto registrado
    @Gauge(name = "highestPar", unit = MetricUnits.NONE, description = "Highest number so far.")
    public Long highestPar() {
        return highestPar;
    }
}
```

Para rodar a aplicação digite 
>./mvnw compile quarkus:dev

Utilize os comandos a seguir no terminal:
>curl localhost:8080/2
>curl localhost:8080/7

confira as métricas:
>curl -H"Accept: application/json" localhost:8080/q/metrics/application
