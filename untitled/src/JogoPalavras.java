import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class JogoPalavras {
    private static final int TEMPO_LIMITE_SEGUNDOS = 60;
    private static final int TAMANHO_MINIMO_PALAVRA = 4;
    private static final String DICIONARIO_ARQUIVO = "br-utf8.txt";

    private final Set<String> palavrasCorretas;
    private final char letraSorteada;

    public JogoPalavras() {
        this.palavrasCorretas = new HashSet<>();
        this.letraSorteada = sortearLetra();
    }

    public void jogar() {
        Scanner scanner = new Scanner(System.in);
        long tempoInicio = System.currentTimeMillis();

        while (true) {
            System.out.printf("Digite uma palavra que comece com a letra '%c': ", letraSorteada);
            String palavra = scanner.nextLine().trim().toLowerCase();

            if (palavra.length() < TAMANHO_MINIMO_PALAVRA) {
                System.out.println("A palavra deve ter pelo menos 4 letras.");
                continue;
            }

            if (validarPalavra(palavra)) {
                palavrasCorretas.add(palavra);
            } else {
                System.out.println("Palavra inválida ou repetida.");
            }

            if (System.currentTimeMillis() - tempoInicio > TEMPO_LIMITE_SEGUNDOS * 1000) {
                break;
            }
        }

        System.out.printf("Você acertou %d palavras:\n", palavrasCorretas.size());
        for (String palavra : palavrasCorretas) {
            System.out.println(palavra);
        }
    }

    private char sortearLetra() {
        String alfabeto = "abcdefghijklmnopqrstuvwxyz";
        int indice = (int) (Math.random() * alfabeto.length());
        return alfabeto.charAt(indice);
    }

    private boolean validarPalavra(String palavra) {
        int i =0;
        try (Scanner fileScanner = new Scanner(new File(DICIONARIO_ARQUIVO))) {
            while (fileScanner.hasNextLine()) {
                String linha = fileScanner.nextLine().trim().toLowerCase();
                //System.out.println(linha);
                if (linha.equals(palavra)) {
                    return true;
                }
                i++;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.err.println("Arquivo de dicionário não encontrado.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(i);
        return false;
    }
}
