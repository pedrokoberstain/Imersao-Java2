import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.awt.font.TextLayout;
import javax.imageio.ImageIO;

public class GeradoraDeFigurinhas {


    public void cria(InputStream inputStream, String nomeArquivo) throws Exception {

        // Leitura da imagem
        // InputStream inputStream =
        //             new FileInputStream(new File("entrada/filme-maior.jpg"));
        // InputStream inputStream =
        //                 new URL("https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_UX.jpg")
        //                 .openStream();
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        // Cria uma nova imagem em memória com transparência e com tamanho novo
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // Copiar a imagem original para nova imagem (em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        // Configuração da fonte
        var fonte = new Font(Font.MONOSPACED, Font.BOLD, 90);
        graphics.setColor(Color.MAGENTA);
        graphics.setFont(fonte);

        // Escrever uma frase na nova imagem
        String texto = "Esse é brabo!";
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retangulo = fontMetrics.getStringBounds(texto, graphics);
        int larguraTexto = (int) retangulo.getWidth();
        int posicaoTextoX = (int) ((largura - larguraTexto) / 2);
        int posicaoTextoY = novaAltura - 100;
        graphics.drawString(texto, (int) posicaoTextoX, novaAltura - 100);

        //Inserindo contorno
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        TextLayout textLayout = new TextLayout(texto, fonte, fontRenderContext);
        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(posicaoTextoX, posicaoTextoY);
        graphics.setTransform(transform);

        var outlineStroke = new BasicStroke(largura * 0.004f);
        graphics.setStroke(outlineStroke);

        graphics.setColor(Color.BLACK);
        graphics.draw(outline);
        graphics.setClip(outline);

        // Escrever a nova imagem em um arquivo
        ImageIO.write(novaImagem, "png", new File(nomeArquivo));

    }

}