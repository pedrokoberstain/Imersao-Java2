import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        //Fazer uma comunicação HTTP com a base de dados dos top 250 filmes -> https://imdb-api.com/en/API/Top250Movies/k_igme5nm1
        String url = "https://imdb-api.com/en/API/MostPopularMovies/k_igme5nm1";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        System.out.println(body);

        //Afunilar apenas os dados de interesse (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        //Exibir e manipular os dados
        for (Map<String,String> filme : listaDeFilmes) {
            System.out.println("\u001b[97m \u001b[104m TÍTULO: ➡️\u001b[m" + filme.get("title"));
            System.out.println("\u001b[1mVeja o banner do filme:\u001b[m " + filme.get("image"));
            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            System.out.print("Avaliação:");

            int numeroEstrelinhas = (int) classificacao;
            for (int n = 0; n <= numeroEstrelinhas; n++) {
                System.out.print("\uD83D\uDC4D");
            }
            System.out.println();
        }

    }

}