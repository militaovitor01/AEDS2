import java.util.*;

class FilaDoRecreio{

    static void swap(int x, int y, int[] array) {
        int aux = array[x];
        array[x] = array[y];
        array[y] = aux;
    }

    public static int AlunosEstaticos(int[] array){
            
            int alunosTrocados = 0;
    
            for (int i = 0; i < array.length - 1; i++){
    
                int PosicaoMaiorValor = i;
    
                for (int j = i + 1; j < array.length; j++){
    
                    if (array[j] > array[PosicaoMaiorValor]){
                        PosicaoMaiorValor = j;
                    }
                }
    
                if (PosicaoMaiorValor != i){
                    swap(PosicaoMaiorValor, i, array);
                    alunosTrocados += 2;
                }
            }
    
            return array.length - alunosTrocados;
    }

    public static void main (String[] args){

        Scanner scan = new Scanner(System.in);

        int N, M; // N = numero de casos, M = numero de alunos, Array = notas dos alunos (EQUIVALENTE AO "Pi" do enunciado)
        
        N = scan.nextInt();
        
        for (int i = 0; i < N; i++){

            M = scan.nextInt();
            int [] array = new int [M];

            for (int j = 0; j < M; j++){
                array[j] = scan.nextInt();           
            }  

            System.out.println(AlunosEstaticos(array));
        }

        scan.close();
    }
}