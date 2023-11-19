import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Jogador {

    // Variáveis globais
    public static final String CAMINHO_ARQUIVO = "/tmp/jogadores.csv";
    public static ArrayList<Jogador> todosJogadores = new ArrayList<Jogador>();

    // Atributos
    private int id;
    private String nome;
    private int altura;
    private int peso;
    private String faculdade;
    private int anoNascimento;
    private String cidadeNascimento;
    private String estadoNascimento;

    // Construtor vazio
    public Jogador() {

        this.id = 0;
        this.nome = "";
        this.altura = 0;
        this.peso = 0;
        this.faculdade = "";
        this.anoNascimento = 0;
        this.cidadeNascimento = "";
        this.estadoNascimento = "";
    }

    // Construtor
    public Jogador(int id, String nome, int altura, int peso, String faculdade, int anoNascimento, String cidadeNascimento, String estadoNascimento) {

        this.id = id;
        this.nome = nome;
        this.altura = altura;
        this.peso = peso;
        this.faculdade = faculdade;
        this.anoNascimento = anoNascimento;
        this.cidadeNascimento = cidadeNascimento;
        this.estadoNascimento = estadoNascimento;
    }

    // Métodos de acesso (Gets)
    public int getId() { return this.id; }
    public String getNome() { return this.nome; }
    public int getAltura() { return this.altura; }
    public int getPeso() { return this.peso; }
    public String getFaculdade() { return this.faculdade; }
    public int getAnoNascimento() { return this.anoNascimento; }
    public String getCidadeNascimento() { return this.cidadeNascimento; }
    public String getEstadoNascimento() { return this.estadoNascimento; }

    // Métodos de modificação (Sets)
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setAltura(int altura) { this.altura = altura; }
    public void setPeso(int peso) { this.peso = peso; }
    public void setFaculdade(String faculdade) { this.faculdade = faculdade; }
    public void setAnoNascimento(int anoNascimento) { this.anoNascimento = anoNascimento; }
    public void setCidadeNascimento(String cidadeNascimento) { this.cidadeNascimento = cidadeNascimento; }
    public void setEstadoNascimento(String estadoNascimento) { this.estadoNascimento = estadoNascimento; }

    // Clonar
    public Jogador clonar() { return new Jogador(this.id, this.nome, this.altura, this.peso, this.faculdade, this.anoNascimento, this.cidadeNascimento, this.estadoNascimento); }

    // Imprimir
    public void imprimir() {

        System.out.printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s]\n",
            this.id, this.nome, this.altura, this.peso, this.anoNascimento, this.faculdade, this.cidadeNascimento, this.estadoNascimento);
    }

    // Ler
    public void ler(String linha) {

        // Dividir a linha por ","
        String[] partes = linha.split(",", -1);

        // Preencher atributos vazios
        for(int i = 0; i < partes.length; i++) {

            if(partes[i].equals("")) partes[i] = "não informado";
        }

        // Definir atributos
        this.id = Integer.parseInt(partes[0]);
        this.nome = partes[1];
        this.altura = Integer.parseInt(partes[2]);
        this.peso = Integer.parseInt(partes[3]);
        this.faculdade = partes[4];
        this.anoNascimento = Integer.parseInt(partes[5]);
        this.cidadeNascimento = partes[6];
        this.estadoNascimento = partes[7];
    }

    // Função para ler todos os jogadores
    public static void iniciarJogadores() {

        // Inicializar variáveis
        try {

            FileInputStream fstream = new FileInputStream(CAMINHO_ARQUIVO);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String linha = br.readLine();
  
            while((linha = br.readLine()) != null) {

                // Inicializar jogador
                Jogador jogador = new Jogador();

                // Ler a linha
                jogador.ler(linha);

                // Adicionar jogador ao array
                todosJogadores.add(jogador);
            }

            fstream.close();
        }
        catch(IOException e) { e.printStackTrace(); }
    }

    // Função para buscar por ID
    public static Jogador buscarPorId(int id, ArrayList<Jogador> jogadores) {

        // Buscar o jogador
        for(int i = 0; i < jogadores.size(); i++) {

            if(jogadores.get(i).getId() == id) return jogadores.get(i);
        }
        return null;
    }

    // ---------------------------------------------------------------------------------------------------- //

    public static void main(String[] args) {
        
        // ----------------------------------------------------------------- //

        // #1 - Iniciar - Ler todos os jogadores no arquivo CSV
        iniciarJogadores();

        // ----------------------------------------------------------------- //

        // #2 - Ler entrada e imprimir jogadores com base nas entradas de ID

        // Inicializar scanner
        Scanner inScanner = new Scanner(System.in);

        // Inicializar jogador
        Jogador jogador = new Jogador();

        // Ler primeira linha
        String linha = inScanner.nextLine();

        // Enquanto a linha não for "FIM"
        while(!linha.equals("FIM")) {

            // Obter ID
            int id = Integer.parseInt(linha);

            // Buscar o jogador
            jogador = buscarPorId(id, todosJogadores);

            // Imprimir o jogador
            if(jogador != null) jogador.imprimir();
            else System.out.println("Jogador não encontrado!");

            // Ler a linha
            linha = inScanner.nextLine();
        }

        // Fechar o scanner
        inScanner.close();
    }
}
