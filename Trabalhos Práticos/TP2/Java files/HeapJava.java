import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Jogadores {

    private int id;
    private String nome;
    private int altura;
    private int peso;
    private String universidade;
    private int anoNascimento;
    private String cidadeNascimento;
    private String estadoNascimento;

    public Jogadores(String pedido, File tabela) {
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

    public Jogadores() {
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
        return ("[" + getId() + " ## " + getNome() + " ## " + getAltura() + " ## " + getPeso() + " ## " + getAnoNascimento()
                + " ## " + getUniversidade() + " ## " + getCidadeNascimento() + " ## " + getEstadoNascimento() + "]");
    }
}

public class HeapJava {

    static int contador = 0, moves = 0;
    static long duration;

    public static void criarLog() {
        String fileName = "/tmp/806482_heapsort.txt.";

        try {
            File logFile = new File(fileName);
            FileWriter writer = new FileWriter(logFile);
            writer.write("806482" + "\t" + duration + "\t" + contador + "\t" + moves);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void doHeap(Jogadores jogadores[], int i, int fim) {

        int raiz = i;
        int esquerda = 2 * i + 1;
        int direita = 2 * i + 2;

        if (esquerda < fim && jogadores[esquerda].getAltura() > jogadores[raiz].getAltura()) {
            raiz = esquerda;
        }

        if (direita < fim && jogadores[direita].getAltura() > jogadores[raiz].getAltura()) {
            raiz = direita;
        }

        if (raiz != i) {
            Jogadores aux = jogadores[i];
            jogadores[i] = jogadores[raiz];
            jogadores[raiz] = aux;
            moves++;
            doHeap(jogadores, raiz, fim);
        }
    }

    public static void heapsort(int fim, Jogadores jogadores[]) {
        for (int i = ((fim / 2) - 1); i >=0; i--) {
            doHeap(jogadores, i, fim);
        }

        for (int i = fim - 1; i >= 0; i--) {
            Jogadores aux = jogadores[0];
            jogadores[0] = jogadores[i];
            jogadores[i] = aux;
            moves++;
            doHeap(jogadores, 0, i);
        }

        for (int i = 0; i < fim; i++) {
            for (int j = i + 1; j < fim; j++) {
                if (jogadores[i].getAltura() == jogadores[j].getAltura()) {
                    if (jogadores[i].getNome().compareToIgnoreCase(jogadores[j].getNome()) > 0) {
                        Jogadores aux = jogadores[j];
                        jogadores[j] = jogadores[i];
                        jogadores[i] = aux;
                        moves++;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        File tabela = new File("/tmp/players.csv");
        Jogadores[] jocker = new Jogadores[3000];
        String pedido;
        int numeroJogadores = 0;

        do {
            pedido = scan.nextLine();
            if (!pedido.equalsIgnoreCase("FIM")) {
                Jogadores player = new Jogadores(pedido, tabela);
                jocker[numeroJogadores] = player;
                numeroJogadores++;
            }
        } while (!pedido.equalsIgnoreCase("FIM"));
        scan.close();

        heapsort(numeroJogadores, jocker);

        for (int i = 0; i < numeroJogadores; i++) {
            System.out.println(jocker[i].dados());
        }

        criarLog();
    }
}
