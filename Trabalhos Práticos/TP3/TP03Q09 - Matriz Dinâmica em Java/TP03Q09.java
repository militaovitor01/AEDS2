import java.util.Scanner;

public class TP03Q09 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numCasosTeste = scanner.nextInt(); // número de casos de teste

        // Entrada
        Matriz[] matrizes = new Matriz[numCasosTeste * 2];
        for (int i = 0; i < numCasosTeste * 2; i++) {
            matrizes[i] = Matriz.lerMatriz(scanner);
        }

        // Saída
        for (int i = 0; i < numCasosTeste * 2; i += 2) {
            matrizes[i].mostrarDiagonalPrincipal();
            System.out.println();
            matrizes[i].mostrarDiagonalSecundaria();

            Matriz matrizSoma = matrizes[i].soma(matrizes[i + 1]);
            System.out.println();
            matrizSoma.imprimirMatriz();

            Matriz matrizMult = matrizes[i].multiplicar(matrizes[i + 1]);
            matrizMult.imprimirMatriz();
        }
    }
}

class CelulaMatriz {

    protected CelulaMatriz clone() {
        CelulaMatriz clone = new CelulaMatriz();
        clone.elemento = this.elemento;
        clone.sup = this.sup;
        clone.dir = this.dir;
        clone.inf = this.inf;
        clone.esq = this.esq;
        return clone;
    }

    public int elemento;
    public CelulaMatriz sup;
    public CelulaMatriz dir;
    public CelulaMatriz inf;
    public CelulaMatriz esq;

    public CelulaMatriz(int x) {
        this.elemento = x;
        this.sup = this.dir = this.inf = this.esq = null;
    }

    public CelulaMatriz() {
        this(0);
    }
}

class Matriz {

    public CelulaMatriz inicio;
    public int linha, coluna;

    public Matriz() {
        this(3, 3);
    }

    public Matriz(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        alocacaoDeCelulas();
    }

    public void alocacaoDeCelulas() {
        inicio = new CelulaMatriz();
        CelulaMatriz temp = inicio;
        for (int i = 1; i < coluna; i++) {
            temp.dir = new CelulaMatriz();
            temp.dir.esq = temp;
            temp = temp.dir;
        }

        CelulaMatriz tmp = inicio;
        for (int l = 1; l < linha; l++, tmp = tmp.inf) {
            CelulaMatriz i = tmp;
            i.inf = new CelulaMatriz();
            i.inf.sup = i;
            CelulaMatriz j = i.inf;
            for (int c = 1; c < coluna; c++, j = j.dir) {
                i = i.dir;
                i.inf = new CelulaMatriz();
                i.inf.sup = i;
                i.inf.esq = j;
                j.dir = i.inf;
            }
        }
    }

    public void setar(int l, int c, int x) {
        CelulaMatriz atual = inicio;
        int i, j;
        for (i = 0; i < c; i++) {
            atual = atual.dir;
        }
        for (j = 0; j < l; j++) {
            atual = atual.inf;
        }
        atual.elemento = x;
    }

    public int pegar(int l, int c) {
        CelulaMatriz atual = inicio;
        int i, j;
        for (i = 0; i < c; i++) {
            atual = atual.dir;
        }
        for (j = 0; j < l; j++) {
            atual = atual.inf;
        }
        return atual.elemento;
    }

    public Matriz soma(Matriz m) {
        Matriz resultado = null;

        if (this.linha == m.linha && this.coluna == m.coluna) {
            resultado = new Matriz(this.linha, this.coluna);

            for (int i = 0; i < linha; i++) {

                for (int j = 0; j < coluna; j++) {
                    int elemento = this.pegar(i, j) + m.pegar(i, j);
                    resultado.setar(i, j, elemento);
                }
            }
        }

        return resultado;
    }

    public Matriz multiplicar(Matriz m) {
        Matriz resultado = null;
        if (this.coluna == m.linha) {
            resultado = new Matriz(this.linha, m.coluna);

            for (int i = 0; i < this.linha; i++) {
                for (int j = 0; j < m.coluna; j++) {
                    int soma = 0;
                    for (int k = 0; k < this.coluna; k++) {
                        soma += this.pegar(i, k) * m.pegar(k, j);
                    }
                    resultado.setar(i, j, soma);
                }
            }
        }
        return resultado;
    }

    public void mostrarDiagonalPrincipal() {
        for (int i = 0; i < this.linha; i++) {
            System.out.print(pegar(i, i) + " ");
        }
    }

    public void mostrarDiagonalSecundaria() {
        for (int i = 0; i < this.linha; i++) {
            System.out.print(pegar(i, this.coluna - 1 - i) + " ");
        }
    }

    public void imprimirMatriz() {
        for (int i = 0; i < this.linha; i++) {
            for (int j = 0; j < this.coluna; j++) {
                System.out.print(pegar(i, j) + " ");
            }
            System.out.println();
        }
    }

    public static Matriz lerMatriz(Scanner scanner) {

        int linhas = scanner.nextInt();
        int colunas = scanner.nextInt();
        Matriz matriz = new Matriz(linhas, colunas);

        scanner.nextLine();
        for (int i = 0; i < linhas; i++) {
            String entrada = scanner.nextLine();
            String[] valores = entrada.split(" ");
            for (int j = 0; j < colunas; j++) {
                matriz.setar(i, j, Integer.parseInt(valores[j]));
            }
        }

        return matriz;
    }
}
