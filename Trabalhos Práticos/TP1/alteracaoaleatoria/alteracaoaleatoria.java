import java.util.Random;

public class alteracaoaleatoria {
    public static void main (String[] args){        
        
        Random gerador = new Random();
        gerador.setSeed(4);
        String entrada = MyIO.readLine();

        while (!entrada.equalsIgnoreCase("FIM")){          
            //gera as letras que serao altera
            char primeira_letra = (char) ('a' + (Math.abs(gerador.nextInt())) % 26);
			char segunda_letra = (char) ('a' + (Math.abs(gerador.nextInt())) % 26);

            alteracaodeString(entrada, primeira_letra, segunda_letra);
            entrada = MyIO.readLine();
        }    
    }

    //metodo que promove a mudança
    static void alteracaodeString(String palavra, char a, char b){
        String nova_string = "";

        for(int i = 0; i < palavra.length(); i++){
            if(palavra.charAt(i) == a){
                nova_string += b;
            } else {
                nova_string += palavra.charAt(i);
            }           
        }
        MyIO.println(nova_string);
    }
}
