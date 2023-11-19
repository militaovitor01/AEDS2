import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TP03Q03 {

    static public void doComando(String[] comando, Pilha pilha, File tabela) throws Exception {
        if (comando[0].equalsIgnoreCase("I")) {
            Jogador player = new Jogador(comando[1], tabela);
            pilha.inserirFim(player);
        }

        if (comando[0].equalsIgnoreCase("R")) {
            System.out.println("(R) " + pilha.removerFim().getNome());
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        File tabela = new File("/tmp/players.csv");
        Pilha pilha = new Pilha(3921);

        String pedido = scan.nextLine();
        while (!pedido.equalsIgnoreCase("FIM")) {
            Jogador player = new Jogador(pedido, tabela);
            pilha.inserirFim(player);
            pedido = scan.nextLine();
        }

        int pedidoRegistro = scan.nextInt();
        String linha;

        for (int i = 0; i < pedidoRegistro; i++) {
            do {
                linha = scan.nextLine();
            } while (linha.equals(""));

            String[] comando = linha.split(" ");
            doComando(comando, pilha, tabela);
        }

        pilha.mostrar();
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

class Pilha {

    private Jogador[] array;
    private int n;

    public Pilha() {
        this(6);
    }

    public Pilha(int tamanho) {
        array = new Jogador[tamanho];
        n = 0;
    }

    public void inserirFim(Jogador jogador) throws Exception {
        if (n >= array.length) {
            throw new Exception("Erro ao inserir no fim");
        }

        array[n] = jogador;
        n++;
    }

    public Jogador removerFim() throws Exception {
        if (n == 0) {
            throw new Exception("Erro ao remover no fim");
        }

        return array[--n];
    }

    public void mostrar() {
        for (int i = 0; i < n; i++) {
            System.out.println("[" + (i) + "]" + array[i].dados());
        }
        ;
    }
}

