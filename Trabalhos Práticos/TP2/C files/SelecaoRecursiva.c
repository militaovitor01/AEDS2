#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>
// Variáveis globais para definição do número de comparações, movimentações e do tempo de execução do algoritmo
static int numeroComparacoes, numeroMovimentacoes;
double tempoExecucao;

typedef struct Jogador
{
    int id;
    char nome[50];
    int altura;
    int peso;
    char universidade[100];
    int anoNascimento;
    char cidadeNascimento[50];
    char estadoNascimento[50];
} Jogador;

// Função para ordenar os jogadores com base na ordem alfabética dos nomes, para posteriormente serem pesquisados
void ordenarSelecaoRecursiva(Jogador jogadores[], int tamanho, int i)
{

    if (i < tamanho)
    {
        for (int j = tamanho - 1; j > 0; j--)
        {
            numeroComparacoes++;
            if (strcmp(jogadores[j].nome, jogadores[j - 1].nome) < 0)
            {

                Jogador aux = jogadores[j];
                jogadores[j] = jogadores[j - 1];
                jogadores[j - 1] = aux;
                numeroMovimentacoes++;
            }
        }
        ordenarSelecaoRecursiva(jogadores, tamanho, i + 1);
    }
}

// Método para dividir a string linha em substrings entre as vírgulas
void dividirString(char linha[], char substrings[8][100])
{
    int quantidadeSubstrings = 0;
    int posicaoSubstring = 0;
    int posicaoLinha = 0;
    // Inicialização da matriz substrings para fins de controle
    for (int i = 0; i < 8; i++)
    {
        for (int j = 0; j < 100; j++)
        {
            substrings[i][j] = '\0';
        }
    }
    // Loop que repete até que a string linha seja totalmente percorrida
    while (linha[posicaoLinha] != '\0')
    {
        if (linha[posicaoLinha] != ',')
        {
            while (linha[posicaoLinha] != ',' && linha[posicaoLinha] != '\0')
            {
                if (linha[posicaoLinha] == '\n')
                    posicaoLinha++; // Ignorar as quebras de linha
                else
                {
                    substrings[quantidadeSubstrings][posicaoSubstring] = linha[posicaoLinha];
                    posicaoLinha++;
                    posicaoSubstring++;
                }
            }
            posicaoSubstring = 0;
            quantidadeSubstrings++;
        }
        else
        {
            // Condicional para caso o campo esteja vazio
            if (linha[posicaoLinha + 1] == ',' || linha[posicaoLinha + 1] == '\n' || linha[posicaoLinha + 1] == '\0')
            {
                strcpy(substrings[quantidadeSubstrings], "não informado");
                quantidadeSubstrings++;
            }
            posicaoLinha++;
        }
    }
}

// Método para realizar a leitura de um arquivo e guardar as informações em um array de jogadores
void lerArquivo(Jogador jogadores[], FILE *arquivo)
{

    char linha[3921];
    int quantidadeJogadores = -1; // Inicialização negativa para que a primeira linha seja ignorada

    while (fgets(linha, sizeof(linha), arquivo) != NULL)
    {
        char substrings[8][100];
        if (quantidadeJogadores >= 0)
        {
            dividirString(linha, substrings);
            // Conversão de strings para inteiros
            int ID = atoi(substrings[0]);
            int altura = atoi(substrings[2]);
            int peso = atoi(substrings[3]);
            int anoNascimento = atoi(substrings[5]);

            jogadores[quantidadeJogadores].id = ID;
            strcpy(jogadores[quantidadeJogadores].nome, substrings[1]);
            jogadores[quantidadeJogadores].altura = altura;
            jogadores[quantidadeJogadores].peso = peso;
            strcpy(jogadores[quantidadeJogadores].universidade, substrings[4]);
            jogadores[quantidadeJogadores].anoNascimento = anoNascimento;
            strcpy(jogadores[quantidadeJogadores].cidadeNascimento, substrings[6]);
            strcpy(jogadores[quantidadeJogadores].estadoNascimento, substrings[7]);
            quantidadeJogadores++;
        }
        else
            quantidadeJogadores++;
    }
}

// Método para imprimir os atributos do jogador com o ID recebido
void imprimirJogador(int identificador, Jogador jogadores[])
{
    printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s]\n",
           jogadores[identificador].id,
           jogadores[identificador].nome,
           jogadores[identificador].altura,
           jogadores[identificador].peso,
           jogadores[identificador].anoNascimento,
           jogadores[identificador].universidade,
           jogadores[identificador].cidadeNascimento,
           jogadores[identificador].estadoNascimento);
}

// Cria um arquivo de log
void criarLog()
{
    FILE *arquivoLog = fopen("/tmp/806482_selecaoRecursiva.txt", "w");
    fprintf(arquivoLog, "806482\t%d\t%d\t%lf", numeroComparacoes, numeroMovimentacoes * 3, tempoExecucao);
    fclose(arquivoLog);
}

int main()
{
    clock_t inicio, fim;

    char id[500];
    Jogador jogadores[3921];
    Jogador subJogadores[500];
    int numeroJogador = 0;
    FILE *arquivo = fopen("/tmp/players.csv", "r");
    do
    {
        scanf("%s", id);
        if (strcmp(id, "FIM") != 0 && strcmp(id, "fim") != 0)
        {
            int identificador = atoi(id);
            lerArquivo(jogadores, arquivo);

            subJogadores[numeroJogador] = jogadores[identificador];
            numeroJogador++;
        }
    } while ((strcmp(id, "FIM") != 0) && (strcmp(id, "fim") != 0));
    fclose(arquivo);
    inicio = clock();
    ordenarSelecaoRecursiva(subJogadores, numeroJogador, 0);
    fim = clock();

    for (int i = 0; i < numeroJogador; i++)
    {
        imprimirJogador(i, subJogadores);
    }

    tempoExecucao = (double)(fim - inicio) / CLOCKS_PER_SEC;
    criarLog();
}