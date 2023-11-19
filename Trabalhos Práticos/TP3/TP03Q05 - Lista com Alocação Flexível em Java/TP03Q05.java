import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TP03Q05 {

    static public void doComando(String[] comando, ListaFlexivel lista, File tabela) {
        if (comando[0].equalsIgnoreCase("II")) {
            Jogador player = new Jogador(comando[1], tabela);
            lista.inserirInicio(player);
        }

        if (comando[0].equalsIgnoreCase("IF")) {
            Jogador player = new Jogador(comando[1], tabela);
            lista.inserirFim(player);
        }

        if (comando[0].equalsIgnoreCase("I*")) {
            Jogador player = new Jogador(comando[2], tabela);
            lista.inserir(player, Integer.parseInt(comando[1]));
        }

        if (comando[0].equalsIgnoreCase("RI")) {
            System.out.println("(R) " + lista.removerInicio().getNome());
        }

        if (comando[0].equalsIgnoreCase("RF")) {
            System.out.println("(R) " + lista.removerFim().getNome());
        }

        if (comando[0].equalsIgnoreCase("R*")) {
            System.out.println("(R) " + lista.remover(Integer.parseInt(comando[1])).getNome());
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        File tabela = new File("/tmp/players.csv");
        ListaFlexivel lista = new ListaFlexivel();

        String pedido = scan.nextLine();
        while (!pedido.equalsIgnoreCase("FIM")) {
            Jogador player = new Jogador(pedido, tabela);
            lista.inserirFim(player);
            pedido = scan.nextLine();
        }

        int pedidoRegistro = scan.nextInt();
        String linha;

        for (int i = 0; i < pedidoRegistro; i++) {
            do {
                linha = scan.nextLine();
            } while (linha.equals(""));

            String[] comando = linha.split(" ");
            doComando(comando, lista, tabela);
        }

        lista.mostrar();
        scan.close();
    }
}

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

    public Jogador() {
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
                        " ##");
    }
}

class Celula {

    public Jogador elemento;
    public Celula prox;

    public Celula() {
        this(null);
    }

    public Celula(Jogador elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

class ListaFlexivel {

    private Celula primeiro;
    private Celula ultimo;

    public ListaFlexivel() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void inserirInicio(Jogador jogador) {
        Celula tmp = new Celula(jogador);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;

        if (primeiro == ultimo) {
            ultimo = tmp;
        }
        tmp = null;
    }

    public void inserirFim(Jogador jogador) {
        ultimo.prox = new Celula(jogador);
        ultimo = ultimo.prox;
    }

    public void inserir(Jogador jogador, int pos) {
        int tamanho = tamanho();

        if (pos < 0 || pos > tamanho) {
            System.err.println("Erro ao Inserir na posição");
        } else if (pos == 0) {
            inserirInicio(jogador);
        } else if (pos == tamanho) {
            inserirFim(jogador);
        } else {
            Celula i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox) ;
            Celula tmp = new Celula(jogador);
            tmp.prox = i.prox;
            i.prox = tmp;
            tmp = i = null;
        }
    }

    public Jogador removerInicio() {
        if (primeiro == ultimo) {
            System.err.println("Erro ao remover no inicio");
        }
        Celula tmp = primeiro.prox;
        primeiro.prox = tmp.prox;
        Jogador removido = tmp.elemento;
        tmp.prox = null;
        tmp = null;
        return removido;
    }

    public Jogador removerFim() {
        if (primeiro == ultimo) {
            System.err.println("Erro ao remover no fim");
        }
        Celula i;
        for (i = primeiro; i.prox != ultimo; i = i.prox) ;
        Jogador removido = ultimo.elemento;
        ultimo = i;
        i = null;
        ultimo.prox = null;
        return removido;
    }

    public Jogador remover(int pos) {
        int tamanho = tamanho();
        Jogador removido = null;

        if (pos < 0 || pos > tamanho) {
            System.err.println("Erro ao Inserir na posição");
        } else if (pos == 0) {
            removido = removerInicio();
        } else if (pos == tamanho) {
            removido = removerFim();
        } else {
            Celula i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox) ;
            Celula tmp = i.prox;
            removido = tmp.elemento;
            i.prox = tmp.prox;
            tmp.prox = null;
            tmp = i = null;
        }

        return removido;
    }

    public void mostrar() {
        int j = 0;
        for (Celula i = primeiro.prox; i != null; i = i.prox, j++) {
            System.out.println("[" + (j) + "]" + i.elemento.dados());
        }
        ;
    }

    public int tamanho() {
        int tamanho = 0;
        for (Celula i = primeiro; i != ultimo; i = i.prox) {
            tamanho++;
        }
        return tamanho;
    }
}


