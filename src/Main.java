import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {

        //Fazer uma comunicação HTTP com a base de dados dos top 250 filmes -> https://imdb-api.com/en/API/Top250Movies/k_igme5nm1
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        //Afunilar apenas os dados de interesse (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        var diretorio = new File("figurinhas/");
        diretorio.mkdir();

        //Exibir e manipular os dados
        //Limitando em apenas 5 figurinhas
        var geradora = new GeradoraDeFigurinhas();
        for (int index = 0; index < 5; index++) {
            var filme = listaDeFilmes.get(index);

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = "figurinhas/" + titulo +".png";

            geradora.cria(inputStream, nomeArquivo);

            System.out.println(titulo);
            System.out.println();
        }
    }
}