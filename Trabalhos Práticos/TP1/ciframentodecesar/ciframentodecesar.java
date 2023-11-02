import java.util.Scanner;

public class ciframentodecesar {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String palavra = scanner.nextLine(); // pegando a frase

        // Inicializa o laço while como forma de parada
        while (!palavra.equalsIgnoreCase("FIM")) {

            String frase_nova = "";

            for (int i = 0; i < palavra.length(); i++) {

                // verifica se o caractere em questao esta entre os elementos unicode,
                // caso esteja, faz o ciframento. Caso seja caractere especial, não.
                if (palavra.charAt(i) <= 153 && palavra.charAt(i) >= 0) {
                    char deslocamento = (char) (palavra.charAt(i) + 3); 
                    frase_nova += deslocamento;
                } else {
                    char caracter = (char) (palavra.charAt(i)); 
                    frase_nova += caracter;
                }

            }

            System.out.println(frase_nova);
            palavra = scanner.nextLine();
        }
        
        scanner.close();
    }
}
