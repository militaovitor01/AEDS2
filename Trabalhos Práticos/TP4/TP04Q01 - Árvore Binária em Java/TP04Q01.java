import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Jogador {

    private int id;
    private String nome;
    private int altura;
    private int peso;
    private String universidade;
    private int anoNascimento;
    private String cidadeNascimento;
    private String estadoNascimento;

    public Jogador(String requisicao, File tabela) {
        try {
            Scanner scanner = new Scanner(tabela);

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] elementos = linha.split(",", -1);

                for (int i = 0; i < elementos.length; i++) {
                    if (elementos[i].isEmpty()) {
                        elementos[i] = "não informado";
                    }
                }

                if (requisicao.equals(elementos[0]) && elementos.length == 8) {
                    setId(Integer.parseInt(elementos[0]));
                    setNome(elementos[1]);
                    setAltura(Integer.parseInt(elementos[2]));
                    setPeso(Integer.parseInt(elementos[3]));
                    setUniversidade(elementos[4]);
                    setAnoNascimento(Integer.parseInt(elementos[5]));
                    setCidadeNascimento(elementos[6]);
                    setEstadoNascimento(elementos[7]);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Arquivo não encontrado");
        }
    }

    public Jogador(String nome) {
        this.nome = nome;
        this.altura = 0;
        this.anoNascimento = 0;
        this.cidadeNascimento = "0";
        this.estadoNascimento = "0";
        this.id = 0;
        this.peso = 0;
        this.universidade = "0";
    }

    public void setId(int id) {
        this.id = id;
    }

    private void setNome(String nome) {
        this.nome = nome;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public void setUniversidade(String universidade) {
        this.universidade = universidade;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public void setCidadeNascimento(String cidadeNascimento) {
        this.cidadeNascimento = cidadeNascimento;
    }

    public void setEstadoNascimento(String estadoNascimento) {
        this.estadoNascimento = estadoNascimento;
    }

    public int getId() {
        return this.id;
    }

    public int getAltura() {
        return this.altura;
    }

    public String getNome() {
        return this.nome;
    }

    public int getPeso() {
        return this.peso;
    }

    public String getUniversidade() {
        return this.universidade;
    }

    public int getAnoNascimento() {
        return this.anoNascimento;
    }

    public String getCidadeNascimento() {
        return this.cidadeNascimento;
    }

    public String getEstadoNascimento() {
        return this.estadoNascimento;
    }

    public String dados() {
        return (
            " ## " +
            getNome() +
            " ## " +
            getAltura() +
            " ## " +
            getPeso() +
            " ## " +
            getAnoNascimento() +
            " ## " +
            getUniversidade() +
            " ## " +
            getCidadeNascimento() +
            " ## " +
            getEstadoNascimento() +
            " ##"
        );
    }
}

class No {
    Jogador elemento;
    No esq, dir;

    public No(Jogador elemento) {
        this(elemento, null, null);
    }

    public No(Jogador elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class ArvoreBinaria {

    No raiz;

    public ArvoreBinaria() {
        raiz = null;
    }

    public Jogador obterMaior() {
        Jogador tmp = null;

        if (raiz != null) {
            No i;
            for (i = raiz; i.dir != null; i = i.dir);
            tmp = i.elemento;
        }

        return tmp;
    }

    public Jogador obterMenor() {
        Jogador tmp = null;

        if (raiz != null) {
            No i;
            for (i = raiz; i.esq != null; i = i.esq);
            tmp = i.elemento;
        }

        return tmp;
    }

    public int obterAltura() {
        return obterAltura(raiz, 0);
    }

    public int obterAltura(No i, int altura) {
        if (i == null) {
            altura--;
        } else {
            int alturaEsq = obterAltura(i.esq, altura + 1);
            int alturaDir = obterAltura(i.dir, altura + 1);
            altura = (alturaEsq > alturaDir) ? alturaEsq : alturaDir;
        }
        return altura;
    }

    public void inserir(Jogador jogador) throws Exception {
        raiz = inserir(jogador, raiz);
    }

    private No inserir(Jogador jogador, No i) throws Exception {
        if (i == null) {
            i = new No(jogador);
        } else if (jogador.getNome().compareTo(i.elemento.getNome()) < 0) {
            i.esq = inserir(jogador, i.esq);
        } else if (jogador.getNome().compareTo(i.elemento.getNome()) > 0) {
            i.dir = inserir(jogador, i.dir);
        } else {
            throw new Exception("Erro ao inserir");
        }
        return i;
    }

    public void remover(Jogador jogador) throws Exception {
        raiz = remover(jogador, raiz);
    }

    private No remover(Jogador jogador, No i) throws Exception {
        if (i == null) {
            throw new Exception("Erro ao remover");
        } else if (jogador.getNome().compareTo(i.elemento.getNome()) < 0) {
            i.esq = remover(jogador, i.esq);
        } else if (jogador.getNome().compareTo(i.elemento.getNome()) > 0) {
            i.dir = remover(jogador, i.dir);
        } else if (i.esq == null) {
            i = i.dir;
        } else if (i.dir == null) {
            i = i.esq;
        } else {
            i.esq = maiorEsquerda(i, i.esq);
        }
        return i;
    }

    private No maiorEsquerda(No i, No j) {
        if (j.dir == null) {
            i.elemento = j.elemento;
            j = j.esq;
        } else {
            j.dir = maiorEsquerda(i, j.dir);
        }

        return j;
    }

    public void pesquisar(Jogador jogador) {
        System.out.print(" raiz");
        if (pesquisar(jogador, raiz)) {
            System.out.println(" SIM");
        } else {
            System.out.println(" NAO");
        }
    }

    private boolean pesquisar(Jogador jogador, No i) {
        boolean resp = false;

        if (i == null) {
            resp = false;
        } else if (jogador.getNome().compareTo(i.elemento.getNome()) == 0) {
            resp = true;
        } else if (jogador.getNome().compareTo(i.elemento.getNome()) < 0) {
            System.out.print(" esq");
            resp = pesquisar(jogador, i.esq);
        } else {
            System.out.print(" dir");
            resp = pesquisar(jogador, i.dir);
        }

        return resp;
    }

    public void mostrar() {
        mostrar(raiz);
    }

    private void mostrar(No i) {
        if (i != null) {
            System.out.println(i.elemento.getNome());
            mostrar(i.esq);
            mostrar(i.dir);
        }
    }
}

public class TP04Q01 {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        File tabela = new File("/tmp/players.csv");
        ArvoreBinaria arvore = new ArvoreBinaria();

        String requisicao = scanner.nextLine();
        while (!requisicao.equalsIgnoreCase("FIM")) {
            Jogador jogador = new Jogador(requisicao, tabela);
            arvore.inserir(jogador);
            requisicao = scanner.nextLine();
        }

        requisicao = scanner.nextLine();
        while (!requisicao.equalsIgnoreCase("FIM")) {
            Jogador jogador = new Jogador(requisicao);
            System.out.print(jogador.getNome());
            arvore.pesquisar(jogador);
            requisicao = scanner.nextLine();
        }

        scanner.close();
    }
}
