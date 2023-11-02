class algebrabooleana{
   public static String substituirExpressaoBooleana(String palavra) {    
      String novaPalavra = "";
      char quantidade;
      quantidade = palavra.charAt(0);
      int tamanho = palavra.length();

      if (quantidade == '2') {
         for (int i = 5; i < tamanho; i++) {
            if (palavra.charAt(i) == 'A') {
               novaPalavra = novaPalavra + palavra.charAt(2);
            } else if (palavra.charAt(i) == 'B') {
               novaPalavra = novaPalavra + palavra.charAt(4);
            } else if (palavra.charAt(i) == 'C') {
               novaPalavra = novaPalavra + palavra.charAt(6);
            } else {
               novaPalavra = novaPalavra + palavra.charAt(i);
            }
         }
      } else if (quantidade == '3') {
         for (int i = 7; i < tamanho; i++) {  
            if (palavra.charAt(i) == 'A') {
               novaPalavra = novaPalavra + palavra.charAt(2);
            } else if (palavra.charAt(i) == 'B') {
               novaPalavra = novaPalavra + palavra.charAt(4);
            } else if (palavra.charAt(i) == 'C') {
               novaPalavra = novaPalavra + palavra.charAt(6);
            } else {
               novaPalavra = novaPalavra + palavra.charAt(i);
            }
         }
      }

      return novaPalavra;
   }

   public static int retornarUltimoParentese(String palavra) {    
      int posicao = -1;
      int tamanho = palavra.length();

      for (int i = 0; i < tamanho; i++) {
         if (palavra.charAt(i) == '(') {
            posicao = i;
         }
      }

      return posicao;
   }

   public static String resolverExpressaoBooleana(String palavra, int posicao) {    
      String novaPalavra = "";
      char resultado;
      int j;
      int posicaoParentese = 0;
      int tamanho = palavra.length();

      if (palavra.charAt(posicao - 1) == 't') {
         for (int i = 0; i < tamanho; i++) {
            if (i == posicao - 3) {
               if (palavra.charAt(posicao + 1) == '1') {
                  novaPalavra = novaPalavra + '0';
               } else {
                  novaPalavra = novaPalavra + '1';
               }

               for (j = posicao - 2; j < tamanho; j++) {
                  if (palavra.charAt(j) == ')') {
                     posicaoParentese = j;
                     j = tamanho;
                  }
               }

               i = posicaoParentese;
            } else {
               novaPalavra = novaPalavra + palavra.charAt(i);
            }   
         }
      } else if (palavra.charAt(posicao - 1) == 'd') {
         resultado = '1';
         for (int i = posicao; i < tamanho; i++) {
            if (palavra.charAt(i) == ')') {
               i = tamanho;
            } else if (palavra.charAt(i) == '0') {
               resultado = '0';
               i = tamanho;
            }
         }
         
         for (int i = 0; i < tamanho; i++) {
            if (i == posicao - 3) {
               novaPalavra = novaPalavra + resultado;
               for (j = posicao - 2; j < tamanho; j++) {
                  if (palavra.charAt(j) == ')') { 
                     posicaoParentese = j;
                     j = tamanho;
                  }
               }
               i = posicaoParentese;
            } else {
               novaPalavra = novaPalavra + palavra.charAt(i);
            }
         }
      } else {
         resultado = '0';
         for (int i = posicao; i < tamanho; i++) {
            if (palavra.charAt(i) == ')') {
               i = tamanho;
            } else if (palavra.charAt(i) == '1') {
               resultado = '1';
               i = tamanho;
            }
         }
         
         for (int i = 0; i < tamanho; i++) {
            if (i == posicao - 2) {
               novaPalavra = novaPalavra + resultado;
               for (j = posicao - 1; j < tamanho; j++) {
                  if (palavra.charAt(j) == ')') { 
                     posicaoParentese = j;
                     j = tamanho;
                  }
               }
               i = posicaoParentese;
            } else {
               novaPalavra = novaPalavra + palavra.charAt(i);
            }
         }
      }

      return novaPalavra ;
   }

   public static boolean ehFim(String palavra) {
      return palavra.charAt(0) == '0';
   }

   public static void main(String[] args) {
      String[] entrada = new String[1000];
      int numEntrada = 0;
      String novaPalavra, palavraAux;
      int posicao;

      do {
         entrada[numEntrada] = MyIO.readLine();
      } while (!ehFim(entrada[numEntrada++]));

      numEntrada--;

      for (int i = 0; i < numEntrada; i++) {
         novaPalavra = substituirExpressaoBooleana(entrada[i]);
         do {
            posicao = retornarUltimoParentese(novaPalavra);
            if (posicao > 0) {
               palavraAux = resolverExpressaoBooleana(novaPalavra, posicao);
               novaPalavra = palavraAux;
            }
         } while (posicao > 0);

         MyIO.println(novaPalavra);
      }
   }
}
