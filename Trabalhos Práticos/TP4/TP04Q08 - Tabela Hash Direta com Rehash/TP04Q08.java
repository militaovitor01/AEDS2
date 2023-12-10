import java.io.File;
import java.util.Scanner;

// Player class
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
        } catch (Exception e) {
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
            " ##"
        );
    }

    public int getMod() {
        return (this.altura % 21);
    }
}

// Hash Class
class Hash {
    Jogador[] tabela;
    int tt;

    public Hash() {
        this(21);
    }

    public Hash(int tt) {
        this.tt = tt;
        this.tabela = new Jogador[tt];
    }

    private int reHash(Jogador jogador) {
        return (jogador.getAltura() + 1) % 21;
    }

    public boolean inserir(Jogador jogador) {
        boolean resp = false;

        if (jogador != null) {
            int pos = jogador.getMod();

            if (tabela[pos] == null) {
                tabela[pos] = jogador;
                resp = true;
            } else {
                pos = reHash(jogador);
                if (tabela[pos] == null) {
                    tabela[pos] = jogador;
                    resp = true;
                }
            }
        }

        return resp;
    }

    public void pesquisar(String nome) {
        if (pesquisar2(nome)) {
            System.out.println(nome + " SIM");
        } else {
            System.out.println(nome + " NAO");
        }
    }

    private boolean pesquisar2(String nome) {
        boolean resp = false;

        for (int i = 0; i < tt; i++) {
            if (tabela[i] != null) {
                if (tabela[i].getNome().equals(nome)) {
                    resp = true;
                }
            }
        }

        return resp;
    }
}

// Main Class
public class TP04Q08 {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        File tabela = new File("/tmp/players.csv");
        Hash hash = new Hash();

        String pedido = scan.nextLine();
        while (!pedido.equalsIgnoreCase("FIM")) {
            Jogador player = new Jogador(pedido, tabela);
            hash.inserir(player);
            pedido = scan.nextLine();
        }

        pedido = scan.nextLine();
        while (!pedido.equalsIgnoreCase("FIM")) {
            hash.pesquisar(pedido);
            pedido = scan.nextLine();
        }

        scan.close();
    }
}
