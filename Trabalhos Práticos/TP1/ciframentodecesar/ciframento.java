class ciframento{
    static public void main (String[] args){

        String frase_nova= "";       
        String palavra;
        
        //Inicializa o laço do-while como forma de parada
        do{
            palavra = MyIO.readString();
            for (int i=0; i<palavra.length(); i++){  
                //verifica se o caractere em questao esta entre os elementos unicode, caso estiver, faz o ciframento. Caso seja caracter especial, não.         
                if (palavra.charAt(i) <= 153 && palavra.charAt(i) >= 0){               
                    char deslocamento = (char) (palavra.charAt(i) + 3);
                    frase_nova += deslocamento;
                }else{
                    char deslocamento = (char) (palavra.charAt(i));
                    frase_nova += deslocamento; 
                }
            }
            
            MyIO.println(frase_nova);         
        }while (!palavra.equalsIgnoreCase("FIM"));
    }
}