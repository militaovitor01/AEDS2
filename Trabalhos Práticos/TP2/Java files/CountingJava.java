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

    public Jogador(String pedido, File arquivoJogadores) {
        try {
            Scanner scanner = new Scanner(arquivoJogadores);

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] elementos = linha.split(",", -1);

                for (int i = 0; i < elementos.length; i++) {
                    if (elementos[i].isEmpty()) {
                        elementos[i] = "não informado";
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

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            System.out.println("Arquivo não encontrado");
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
        return ("[" +
                getId() +
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
                "]");
    }
}

public class CountingJava {

    static int contador = 0, moves = 0;
    static long duracao;

    public static void criarLog() {
        String nomeArquivo = "/tmp/806482.txt";

        try {
            File arquivoLog = new File(nomeArquivo);
            FileWriter escritor = new FileWriter(arquivoLog);
            escritor.write("806482" + "\t" + contador + "\t" + moves + "\t" + duracao);
            escritor.close();
        } catch (IOException e) {
            System.out.println("Ocorreu um erro.");
            e.printStackTrace();
        }
    }

    public static void ordenarAlfabeticamente(int fim, Jogador jogadores[]) {

        for (int i = 0; i < fim; i++) {
            contador++;
            for (int j = i + 1; j < fim; j++) {
                contador++;
                if (jogadores[i].getAltura() == jogadores[j].getAltura()) {
                    contador++;
                    if (jogadores[i].getNome().compareToIgnoreCase(jogadores[j].getNome()) > 0) {
                        contador++;
                        Jogador aux = jogadores[j];
                        jogadores[j] = jogadores[i];
                        jogadores[i] = aux;
                        moves++;
                    }
                }
            }
        }
    }

    public static void countSort(int fim, Jogador jogadores[]) {
        long startTime = System.nanoTime();

        int maior = jogadores[0].getAltura();
        Jogador[] jogadoresC = new Jogador[fim];

        for (int i = 0; i < fim; i++) {
            contador++;
            if (jogadores[i].getAltura() > maior) {
                maior = jogadores[i].getAltura();
            }
        }

        int[] aux = new int[maior + 1];

        for (int i = 0; i <= maior; i++) {
            contador++;
            aux[i] = 0;
        }

        for (int i = 0; i < fim; i++) {
            contador++;
            aux[jogadores[i].getAltura()]++;
        }

        for (int i = 1; i <= maior; i++) {
            contador++;
            aux[i] += aux[i - 1];
        }

        for (int i = 0; i < fim; i++) {
            contador++;
            jogadoresC[aux[jogadores[i].getAltura()] - 1] = jogadores[i];
            aux[jogadores[i].getAltura()]--;
            moves++;
        }

        for (int i = 0; i < fim; i++) {
            contador++;
            jogadores[i] = jogadoresC[i];
            moves++;
        }

        ordenarAlfabeticamente(fim, jogadores);

        long endTime = System.nanoTime();
        duracao = (endTime - startTime);
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        File tabela = new File("/tmp/players.csv");

        Jogador[] jogadores = new Jogador[3000];
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

        countSort(numeroJogadores, jogadores);

        for (int i = 0; i < numeroJogadores; i++) {
            System.out.println(jogadores[i].dados());
        }

        criarLog();
    }
}
