import java.io.*;
import java.net.URL;

public class leiturahtml{
    public static boolean ehFim(String palavra){
        return (palavra.charAt(0) == 'F' && palavra.charAt(1) == 'I'&& palavra.charAt(2) == 'M' && palavra.length() == 3);
    }

    public static void leitura()throws FileNotFoundException{
        try{
            String nomeSite = "teste";

            while(!ehFim(nomeSite))  {
                String linha;
                int indice = 0;
                int aContagem = 0;
                int eContagem = 0;
                int iContagem = 0;
                int oContagem = 0;
                int uContagem = 0;
                int aAgudoContagem = 0;
                int eAgudoContagem = 0;
                int iAgudoContagem = 0;
                int oAgudoContagem = 0;
                int uAgudoContagem = 0;
                int aTilContagem = 0;
                int oTilContagem = 0;
                int aCraseContagem = 0;
                int eCraseContagem = 0;
                int iCraseContagem = 0;
                int oCraseContagem = 0;
                int uCraseContagem = 0;
                int aFlexoContagem = 0;
                int eFlexoContagem = 0;
                int iFlexoContagem = 0;
                int oFlexoContagem = 0;
                int uFlexoContagem = 0;
                int consoanteContagem = 0;
                int tableContagem = 0;
                int brContagem = 0;

                nomeSite = MyIO.readLine();

                if(ehFim(nomeSite)){
                    break;
                }

                String endereco = MyIO.readLine();
                URL url = new URL(endereco);
                BufferedReader leitorURL = new BufferedReader(new InputStreamReader(url.openStream()));
                
                while ((linha = leitorURL.readLine()) != null){
                    for (indice = 0; indice < linha.length(); indice++){
                        char caractere = linha.charAt(indice);

                        switch (caractere) {
                            case 'a':
                                aContagem++;
                                break;
                            case 'e':
                                eContagem++;
                                break;
                            case 'i':
                                iContagem++;
                                break;
                            case 'o':
                                oContagem++;
                                break;
                            case 'u':
                                uContagem++;
                                break;
                            case 'á':
                                aAgudoContagem++;
                                break;
                            case 'é':
                                eAgudoContagem++;
                                break;
                            case 'í':
                                iAgudoContagem++;
                                break;
                            case 'ó':
                                oAgudoContagem++;
                                break;
                            case 'ú':
                                uAgudoContagem++;
                                break;
                            case 'à':
                                aCraseContagem++;
                                break;
                            case 'è':
                                eCraseContagem++;
                                break;
                            case 'ì':
                                iCraseContagem++;
                                break;
                            case 'ò':
                                oCraseContagem++;
                                break;
                            case 'ù':
                                uCraseContagem++;
                                break;
                            case 'ã':
                                aTilContagem++;
                                break;
                            case 'õ':
                                oTilContagem++;
                                break;
                            case 'â':
                                aFlexoContagem++;
                                break;
                            case 'ê':
                                eFlexoContagem++;
                                break;
                            case 'î':
                                iFlexoContagem++;
                                break;
                            case 'ô':
                                oFlexoContagem++;
                                break;
                            case 'û':
                                uFlexoContagem++;
                                break;
                            default:
                                if ((caractere >= 'a' && caractere <= 'z' && caractere != 'a' && caractere != 'e' && caractere != 'i' && caractere != 'o' && caractere != 'u')
                                        || (caractere >= 'A' && caractere <= 'Z' && caractere != 'A' && caractere != 'E' && caractere != 'I' && caractere != 'O' && caractere != 'U')) {
                                    consoanteContagem++;
                                }
                                break;
                        }

                        if (caractere == '<' && linha.charAt(indice + 1) == 't' && linha.charAt(indice + 2) == 'a' && linha.charAt(indice + 3) == 'b' && linha.charAt(indice + 4) == 'l' && linha.charAt(indice + 5) == 'e' && linha.charAt(indice + 6) == '>') {
                            tableContagem++;
                        }

                        if (caractere == '<' && linha.charAt(indice + 1) == 'b' && linha.charAt(indice + 2) == 'r' && linha.charAt(indice + 3) == '>') {
                            brContagem++;
                        }
                    }
                }

                System.out.print("a" + "(" + aContagem + ")");
                System.out.print(" " +"e" + "(" + eContagem + ")");
                System.out.print(" " +"i" + "(" + iContagem + ")");
                System.out.print(" " +"o" +"(" + oContagem + ")");
                System.out.print(" " +"u" + "(" + uContagem + ")");
                System.out.print(" " +(char)225 + "(" + aAgudoContagem + ")");
                System.out.print(" " +(char)233 + "(" + eAgudoContagem + ")");
                System.out.print(" " +(char)237 + "(" + iAgudoContagem + ")");
                System.out.print(" " +(char)243 + "(" + oAgudoContagem + ")");
                System.out.print(" " +(char)250 + "(" + uAgudoContagem + ")");
                System.out.print(" " +(char)224 + "(" + aCraseContagem + ")");
                System.out.print(" " +(char)232 + "(" + eCraseContagem + ")");
                System.out.print(" " +(char)236 + "(" + iCraseContagem + ")");
                System.out.print(" " +(char)242 + "(" + oCraseContagem + ")");
                System.out.print(" " +(char)249 + "(" + uCraseContagem + ")");
                System.out.print(" " +(char)227 + "(" + aTilContagem + ")");
                System.out.print(" " +(char)245 + "(" + oTilContagem + ")");
                System.out.print(" " +(char)226 + "(" + aFlexoContagem + ")");
                System.out.print(" " +(char)234 + "(" + eFlexoContagem + ")");
                System.out.print(" " +(char)238 + "(" + iFlexoContagem + ")");
                System.out.print(" " +(char)244 + "(" + oFlexoContagem + ")");
                System.out.print(" " +(char)251 + "(" + uFlexoContagem + ")");
                System.out.print(" " +"consoante" + "(" + consoanteContagem + ")");
                System.out.print(" " +"<br>" + "(" + brContagem + ")");
                System.out.print(" " +"<table>" + "(" + tableContagem + ")");
                System.out.print(" " + nomeSite);
                System.out.printf("\n");
            }
            
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) throws FileNotFoundException{
        leitura();
    }
}
