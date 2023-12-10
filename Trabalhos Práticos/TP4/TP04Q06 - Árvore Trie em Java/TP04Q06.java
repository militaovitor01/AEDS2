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

    public Jogador(String pedido, File tabela) {
        try {
            Scanner scan = new Scanner(tabela);

            while (scan.hasNextLine()) {
                String linha = scan.nextLine();
                String[] elementos = linha.split(",", -1);

                for (int i = 0; i < elementos.length; i++) {
                    if (elementos[i].isEmpty()) {
                        elementos[i] = "nao informado";
                    }
                }

                if (pedido.equals(elementos[0]) && elementos.length == 8) {
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

            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("arquivo não encontrado");
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
    No[] filhos;
    boolean isWord;

    public No() {
        this.filhos = new No[95];
        this.isWord = false;
    }
}

class Trie {
    No raiz;

    public Trie() {
        raiz = new No();
    }

    public Trie(Trie t1, Trie t2) {
        this();
        this.juntar(t1);
        this.juntar(t2);
    }

    public void juntar(Trie t) {
        juntar(t.raiz, "");
    }

    private void juntar(No no, String s) {
        if (no.isWord) {
            this.inserir(s);
        }
        for (int i = 0; i < no.filhos.length; i++) {
            if (no.filhos[i] != null) {
                juntar(no.filhos[i], s + (char)(i + 32));
            }
        }
    }

    public void inserir(String nome) {
        No atual = this.raiz;

        for (int i = 0; i < nome.length(); i++) {
            char c = nome.charAt(i);
            int index = c - 32;

            if (atual.filhos[index] == null) {
                No node = new No();
                atual.filhos[index] = node;
                atual = node;
            } else {
                atual = atual.filhos[index];
            }
        }

        atual.isWord = true;
    }

    public void pesquisar(String nome) {
        if (pesquisar(nome, raiz)) {
            System.out.println(nome + " SIM");
        } else {
            System.out.println(nome + " NAO");
        }
    }

    private boolean pesquisar(String nome, No raiz) {
        No atual = raiz;
        boolean resp = false;

        for (int i = 0; i < nome.length(); i++) {
            char c = nome.charAt(i);
            int index = c - 32;

            if (atual.filhos[index] == null) {
                resp = false;
            } else {
                atual = atual.filhos[index];
            }
        }

        if (atual.isWord) {
            resp = true;
        } else {
            resp = false;
        }

        return resp;
    }
}

public class TP04Q06 {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        File tabela = new File("/tmp/players.csv");

        Trie arvore1 = new Trie();
        Trie arvore2 = new Trie();

        String pedido = scan.nextLine();
        while (!pedido.equalsIgnoreCase("FIM")) {
            Jogador player = new Jogador(pedido, tabela);
            arvore1.inserir(player.getNome());
            pedido = scan.nextLine();
        }

        pedido = scan.nextLine();
        while (!pedido.equalsIgnoreCase("FIM")) {
            Jogador player = new Jogador(pedido, tabela);
            arvore2.inserir(player.getNome());
            pedido = scan.nextLine();
        }

        Trie arvoreMerge = new Trie(arvore1, arvore2);

        pedido = scan.nextLine();
        while (!pedido.equalsIgnoreCase("FIM")) {
            arvoreMerge.pesquisar(pedido);
            pedido = scan.nextLine();
        }

        scan.close();
    }
}
