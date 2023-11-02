import java.util.Scanner;
import java.io.File;
import java.util.Scanner;

class Jogador {
    private int id;
    private int altura;
    private int peso;
    private int anoDeNascimento;
    private String nome;
    private String universidade;
    private String cidadeNascimento;
    private String estadoNascimento;


    public Jogador(int id, String nome, int altura, int peso, int anoDeNascimento, String universidade, String cidadeNascimento, String estadoNascimento){
        this.id = id;
        //this.nome = nome;
        this.setNome(nome);
        this.altura = altura;
        this.peso = peso;
        this.anoDeNascimento = anoDeNascimento;
        this.universidade = universidade.equals("") ? "nao informado" : universidade;
        this.cidadeNascimento = cidadeNascimento.equals("") ? "nao informado" : cidadeNascimento;
        this.estadoNascimento = estadoNascimento.equals("") ? "nao informado" : estadoNascimento;
    }

    public Jogador(int id, String nome){
        this.id = id;
        //this.nome = nome;
        this.setNome(nome);

        this.altura = 0;
        this.peso = 0;
        this.anoDeNascimento = 0;
        this.universidade = "nao informado";
        this.cidadeNascimento = "nao informado";
        this.estadoNascimento = "nao informado";
    }
    public void setID(int id){
        this.id = id;
    }
    public void setAltura(int altura){
        this.altura = altura;
    }
    public void setPeso(int peso){
        this.peso = peso;
    }
    public void setAnoDeNascimento(int anoDeNascimento){
        this.anoDeNascimento = anoDeNascimento;
    }
    public void setNome(String nome){
        if(nome.charAt(nome.length() - 1) == '*')
            nome = nome.substring(0, nome.length() - 1);
        this.nome = nome;
    }

    public void setUniversidade(String universidade){
        if(universidade.equals(""))
            this.universidade = "nao informado";
        else
            this.universidade = universidade;
    }

    public void setCidadeNascimento(String cidadeNascimento){
        if(cidadeNascimento.equals(""))
            this.cidadeNascimento = "nao informado";
        else
            this.cidadeNascimento = cidadeNascimento;
    }
    public void setEstadoNascimento(String estadoNascimento){
        if(estadoNascimento.equals(""))
            this.estadoNascimento = "nao informado";
        else
            this.estadoNascimento = estadoNascimento;    }

    public int getID(){
        return this.id;
    }
    public int getAltura(){
        return this.altura;
    }
    public int getPeso(){
        return this.peso;
    }
    public int getAnoDeNascimento(){
        return this.anoDeNascimento;
    }
    public String getNome(){
        return this.nome;
    }
    public String getUniversidade(){
        return this.universidade;
    }
    public String getCidadeNascimento(){
        return this.cidadeNascimento;
    }
    public String getEstadoNascimento(){
        return this.estadoNascimento;
    }
    public Jogador clone(){
        return new Jogador(this.id, this.nome, this.altura, this.peso, this.anoDeNascimento, this.universidade, this.cidadeNascimento, this.estadoNascimento);
    }

    // modelo: [106 ## Walt Lautenbach ## 188 ## 83 ## 1922 ## University of Wisconsin ## nao informado ## nao informado]
    public void imprimir(){
        System.out.print("[");
        System.out.print(this.id + " ## ");
        System.out.print(this.nome + " ## ");
        System.out.print(this.altura + " ## ");
        System.out.print(this.peso + " ## ");
        System.out.print(this.anoDeNascimento + " ## ");
        System.out.print(this.universidade + " ## ");
        System.out.print(this.cidadeNascimento + " ## ");
        System.out.print(this.estadoNascimento);
        System.out.println("]");
    }

    public void imprimirBonito(){
        System.out.print("ID: " + this.id);
        System.out.print(" Nome: " + this.nome);
        System.out.print(" Altura: " + this.altura);
        System.out.print(" Peso: " + this.peso);
        System.out.print(" Ano de Nascimento: " + this.anoDeNascimento);
        System.out.print(" Universidade: " + this.universidade);
        System.out.print(" Cidade de Nascimento: " + this.cidadeNascimento);
        System.out.print(" Estado de Nascimento: " + this.estadoNascimento);
        System.out.println();
    }

    // método do classe, leitura da entrada padrão feita da seguinte maneira:
    // Jogador teste = Jogador.ler();
    public static Jogador ler(){
        Scanner sc = new Scanner(System.in);
        int n;
        String tmp;

        System.out.print("ID: ");
        n = sc.nextInt();
        System.out.print("Nome: ");
        tmp = sc.next();
        Jogador jogador = new Jogador(n, tmp);

        System.out.print("Altura: ");
        n = sc.nextInt();
        jogador.setAltura(n);

        System.out.print("Peso: ");
        n = sc.nextInt();
        jogador.setPeso(n);

        System.out.print("Ano de Nascimento: ");
        n = sc.nextInt();
        jogador.setAnoDeNascimento(n);

        System.out.print("Universidade: ");
        tmp = sc.next();
        jogador.setUniversidade(tmp);

        System.out.print("Cidade de Nascimento: ");
        tmp = sc.next();
        jogador.setCidadeNascimento(tmp);

        System.out.print("Estado de Nascimento: ");
        tmp = sc.next();
        jogador.setEstadoNascimento(tmp);

        sc.close();
        return jogador;
    }

    // Recebe uma linha de um arquivo .csv e devolve o endereço de memória de um objeto
    public static Jogador parse(String linha){
        String[] tmp = linha.split(",");

        String[] csv = new String[8]; // uma linha completa tem 8 atributos

        // prepara o array para ser "ideal"
        for(int i = 0; i < csv.length; i++){
            if(i > (tmp.length - 1))
                csv[i] = "";
            else
                csv[i] = tmp[i];
        }

        Jogador jogador = new Jogador(Integer.parseInt(csv[0]), csv[1]); // únicos atributos que tenho a certeza que irão existir

         // número ideal de atributos para Jogador
        jogador.setAltura(Integer.parseInt(csv[2]));
        jogador.setPeso(Integer.parseInt(csv[3]));
        jogador.setUniversidade(csv[4]);
        jogador.setAnoDeNascimento(Integer.parseInt(csv[5]));
        jogador.setCidadeNascimento(csv[6]);
        jogador.setEstadoNascimento(csv[7]);

        return jogador;
    }

}


class Fila {
    private Jogador jogadores[]; 
    private int qtdElementosTotal; // armazena a quantidade de elementos que a fila terá
    private int n;

    public Fila(int n){
        jogadores = new Jogador[n]; 
        qtdElementosTotal = n;
        this.n = 0;
    }
    
    public void add(Jogador jogador){
        if(n == qtdElementosTotal){
            System.out.println("Erro! Fila cheia.");
        }
        else{
            jogadores[n] = jogador;
            n++;
        }
    }

    public Jogador getJogador(int i){
        return this.jogadores[i];
    }

    public void printJogador(int i){
        if(i >= n || i < 0){
            System.out.println("Elemento inexistente!");
        }
        else{
            jogadores[i].imprimir();
        }
    }   

    public int length(){
        return n;
    }

    public int maxFila(){
        return qtdElementosTotal;
    }

    // retorna o número de comparações
    public int pesquisaSequencial(String nome){
        int cmp = 0;
        for(int i = 0; i < n; i++){
            cmp += 1;
            if(jogadores[i].getNome().equals(nome)){
                System.out.println("SIM");
                return cmp;
            }            
        }
        System.out.println("NAO");
        
        return cmp;
    }

    // particionamento de lomuto para quicksort
    public int particionamento(int esquerda, int direita) {
        Jogador temp = null;
        Jogador pivot = jogadores[esquerda];

        int i = esquerda;
        for (int j = esquerda + 1; j <= direita; j++) {
            if (jogadores[j].getEstadoNascimento().compareTo(pivot.getEstadoNascimento()) < 0 ||
                (jogadores[j].getEstadoNascimento().compareTo(pivot.getEstadoNascimento()) == 0 &&
                jogadores[j].getNome().compareTo(pivot.getNome()) < 0)) {
                i++;
                temp = jogadores[i];
                jogadores[i] = jogadores[j];
                jogadores[j] = temp;
            }
        }   

        temp = jogadores[esquerda];
        jogadores[esquerda] = jogadores[i];
        jogadores[i] = temp;

        return i;
    }

    public void sortQuicksort(int esquerda, int direita, int k) {
        if (esquerda < direita) {
            if(k > 0){
                int indicePivot = particionamento(esquerda, direita);
                sortQuicksort(esquerda, indicePivot - 1, k - 1);
                sortQuicksort(indicePivot + 1, direita, k - 1);
            }           
        }
    }
}

public class QuicksortParcial {
    public static boolean isFim(String str){
        return str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M' && str.length() == 3;
    }

    public static void main(String[] args){
        File arquivo_csv = new File("/tmp/players.csv");
        Scanner sc;
        Fila jogadores = new Fila(5000); // cria uma fila com no máximo 5000 jogadores
        Fila novosJogadores = new Fila(500);

        // Carrega os objetos do arquivo /tmp/players.csv
        try{
            sc = new Scanner(arquivo_csv);
            sc.nextLine(); // remove a primeira linha do arquivo csv
            String linha = sc.nextLine();
            int i = 0;

            while(sc.hasNext()){
                jogadores.add(Jogador.parse(linha));
                linha = sc.nextLine();
                i++;
            }
            sc.close();
        }
        catch(Exception e ){

        }
        // Fila carregada


        // Faz certas inserções na Fila
        try{
            sc = new Scanner(System.in);
            String linha = sc.nextLine();
            int i = 0;

            while(!isFim(linha)){
                int id = Integer.parseInt(linha);
                novosJogadores.add(jogadores.getJogador(id));
                //novosJogadores.printJogador(i);
                linha = sc.nextLine();
                i++;
            }
            
            sc.close(); 

            int k = 10;

            long tempoInicial = System.currentTimeMillis();

            novosJogadores.sortQuicksort(0, novosJogadores.length() - 1, k);
            
            long tempoFinal = System.currentTimeMillis();
            long tempo = tempoFinal - tempoInicial;

            
            for(i = 0; i < k; i++){
                novosJogadores.printJogador(i);
            }
            
        }
        catch(Exception e){

        } 
    }
}