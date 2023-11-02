import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

    public Jogador() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
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

public class InsercaoJava {

    static int contadorComparacoes = 0;

    public static void criarLog(long duracao) {
        String fileName = "/tmp/806482_insercao.txt";

        try {
            File logFile = new File(fileName);
            FileWriter writer = new FileWriter(logFile);
            writer.write("806482\t" + duracao + "\t" + contadorComparacoes);
            writer.close();
        } catch (IOException e) {
            System.out.println("Ocorreu um erro.");
            e.printStackTrace();
        }
    }

    public static void ordenar(int fim, Jogador[] jogadores) {
        for (int i = 0; i < fim; i++) {
            int menor = i;
            contadorComparacoes++;
            for (int j = i + 1; j < fim; j++) {

                if (jogadores[j].getAnoNascimento() < jogadores[menor].getAnoNascimento()) {
                    menor = j;
                    contadorComparacoes++;
                }

                if (jogadores[j].getAnoNascimento() == jogadores[menor].getAnoNascimento()) {
                    contadorComparacoes++;
                    if (jogadores[j].getNome().compareTo(jogadores[menor].getNome()) < 0) {
                        menor = j;
                        contadorComparacoes++;
                    }
                }
            }

            Jogador aux = jogadores[i];
            jogadores[i] = jogadores[menor];
            jogadores[menor] = aux;
        }
    }

    public static void main(String[] args) {

        long startTime = System.nanoTime();

        Scanner scan = new Scanner(System.in);
        File tabela = new File("/tmp/players.csv");

        Jogador[] jogadores = new Jogador[500];
        String pedido;
        int numeroJogadores = 0;

        do {
            pedido = scan.nextLine();

            if (!pedido.equalsIgnoreCase("FIM")) {
                Jogador jogador = new Jogador(pedido, tabela);
                jogadores[numeroJogadores] = jogador;
                numeroJogadores++;
            }
        } while (!pedido.equalsIgnoreCase("FIM"));
        scan.close();

        ordenar(numeroJogadores, jogadores);

        for (int i = 0; i < numeroJogadores; i++) {
            System.out.println(jogadores[i].dados());
        }

        long endTime = System.nanoTime();
        criarLog(endTime - startTime);
    }
}
