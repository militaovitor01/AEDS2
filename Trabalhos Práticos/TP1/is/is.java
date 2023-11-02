public class is {
    
    static void retornaVogal(String entrada){
        boolean todosVogais = true;

        for (int i = 0; i < entrada.length(); i++) {
            char caractere = Character.toUpperCase(entrada.charAt(i));
            if (caractere != 'A' && caractere != 'E' && caractere != 'I' && caractere != 'O' && caractere != 'U') {
                todosVogais = false;
                break; // Sai do loop assim que encontrar uma consoante
            }
        }

        if (todosVogais) {
            MyIO.print("SIM ");
        } else {
            MyIO.print("NAO ");
        }
    }

    static void retornaConsoante(String entrada){
        boolean todosConsoantes = true;

        for (int i = 0; i < entrada.length(); i++) {
            char caractere = Character.toUpperCase(entrada.charAt(i));
            if (caractere == 'A' || caractere == 'E' || caractere == 'I' || caractere == 'O' || caractere == 'U' || !Character.isLetter(caractere)) {
                todosConsoantes = false;
                break; // Sai do loop assim que encontrar uma vogal ou não-letra
            }
        }

        if (todosConsoantes) {
            MyIO.print("SIM ");
        } else {
            MyIO.print("NAO ");
        }
    }

    static void retornaInteiro(String entrada){
        try {
            Integer.parseInt(entrada);
            MyIO.print("SIM ");
        } catch (NumberFormatException e) {
            MyIO.print("NAO "); //A conversão falhou, então não é um número inteiro
        }
    }

    static void retornaReal(String entrada){
        try {
            Float.parseFloat(entrada);
            MyIO.println("SIM "); // A conversão foi bem-sucedida, então é um número inteiro
        } catch (NumberFormatException e) {
            MyIO.println("NAO "); // A conversão falhou, então não é um número inteiro
        }
    }
       
    public static void main (String[]args){
        
        String entrada = MyIO.readString();
        while (!entrada.equalsIgnoreCase("FIM")){
           
            retornaVogal(entrada);
            retornaConsoante(entrada);
            retornaInteiro(entrada);
            retornaReal(entrada);
            
            entrada = MyIO.readLine();
        }
    }
}
