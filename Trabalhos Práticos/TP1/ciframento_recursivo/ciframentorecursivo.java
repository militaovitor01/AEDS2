public class ciframentorecursivo {

    public static String ciframento(String texto, int posicao){
        String textoCifrado = "";
        int tamanho = texto.length();
        if(posicao < tamanho - 1){
            textoCifrado = ciframento(texto, posicao + 1);
        }
        return (char)(texto.charAt(posicao) + 3) + textoCifrado;
    }

    public static boolean isFim(String texto){
        return (texto.length() == 3 && texto.charAt(0) == 'F' && texto.charAt(1) == 'I' && texto.charAt(2) == 'M');
    }

    public static void main(String[] args){
        String entrada = "test";
        while(!isFim(entrada)){
            entrada = MyIO.readLine();
            MyIO.println(ciframento(entrada, 0));
        }
    }
}
