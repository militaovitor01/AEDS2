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

      scan.close();
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

public class PesquisaSequencial {

  static int contador = 0;

  public static void criarLog(Long duracao) {
    String nomeArquivo = "/tmp/806482_sequencial.txt";

    try {
      File arquivoLog = new File(nomeArquivo);
      FileWriter escritor = new FileWriter(arquivoLog);
      escritor.write("806482" + "  " + duracao + "  " + contador);
      escritor.close();
    } catch (IOException e) {
      System.out.println("Ocorreu um erro.");
      e.printStackTrace();
    }
  }

  public static boolean pesquisa(int i, String pedido, Jogador[] jogadores, Integer[] IDs) {
    boolean achou = false;

    for (int j = 0; j < i; j++) {
      contador++;
      if (pedido.equals(jogadores[j].getNome())) {
        if (IDs[j] == jogadores[j].getId()) {
          achou = true;
          contador++;
          j = i;
        }
      }
    }

    return achou;
  }

  public static void main(String[] args) {

    long startTime = System.nanoTime();

    Scanner scan = new Scanner(System.in);
    File tabela = new File("/tmp/players.csv");

    Jogador[] jogadores = new Jogador[1000];
    String pedido;
    Integer[] IDs = new Integer[1000];
    int numeroJogador = 0;

    do {
      pedido = scan.nextLine();

      if (!pedido.equalsIgnoreCase("FIM")) {
        Jogador jogador = new Jogador(pedido, tabela);
        jogadores[numeroJogador] = jogador;
        IDs[numeroJogador] = Integer.parseInt(pedido);
        numeroJogador++;
      }
    } while (!pedido.equalsIgnoreCase("FIM"));

    String pedido2;

    do {
        pedido2 = scan.nextLine();
        System.out.println((pesquisa(numeroJogador, pedido2, jogadores, IDs)) ? "SIM" : "NAO");
    } while (!pedido2.equalsIgnoreCase("FIM"));

    scan.close();

    long endTime = System.nanoTime();
    criarLog(endTime - startTime);
  }
}
