#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>

int numComparacoes, numMovimentos;
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

void ordenarShell(Jogador jogadores[], int tamanho, int passo, int inicio)
{
    for (int i = inicio + passo; i < tamanho; i += passo)
    {
        numComparacoes++;
        Jogador tmp = jogadores[i];
        int j = i - passo;
        while ((j >= 0) && (jogadores[j].peso > tmp.peso))
        {
            numComparacoes++;
            jogadores[j + passo] = jogadores[j];
            j -= passo;
            numMovimentos++;
        }

        while ((j >= 0) && (strcmp(jogadores[j].nome, tmp.nome) > 0) && (jogadores[j].peso == tmp.peso))
        {
            numComparacoes++;
            jogadores[j + passo] = jogadores[j];
            j -= passo;
            numMovimentos++;
        }

        jogadores[j + passo] = tmp;
    }
}

void ordenarPorPesoShell(Jogador jogadores[], int tamanho)
{
    int passo = 1;

    do
    {
        passo = (passo * 3) + 1;
    } while (passo < tamanho);

    do
    {
        passo /= 3;
        for (int inicio = 0; inicio < passo; inicio++)
        {
            numComparacoes++;
            ordenarShell(jogadores, tamanho, passo, inicio);
        }
    } while (passo != 1);
}

void dividirString(char linha[], char substrings[8][100])
{
    int qtdeSubstrings = 0;
    int posSubstr = 0;
    int posLinha = 0;

    for (int i = 0; i < 8; i++)
    {
        for (int j = 0; j < 100; j++)
        {
            substrings[i][j] = '\0';
        }
    }

    while (linha[posLinha] != '\0')
    {
        if (linha[posLinha] != ',')
        {
            while (linha[posLinha] != ',' && linha[posLinha] != '\0')
            {
                if (linha[posLinha] == '\n')
                    posLinha++;
                else
                {
                    substrings[qtdeSubstrings][posSubstr] = linha[posLinha];
                    posLinha++;
                    posSubstr++;
                }
            }
            posSubstr = 0;
            qtdeSubstrings++;
        }
        else
        {
            if (linha[posLinha + 1] == ',' || linha[posLinha + 1] == '\n' || linha[posLinha + 1] == '\0')
            {
                strcpy(substrings[qtdeSubstrings], "nao informado");
                qtdeSubstrings++;
            }
            posLinha++;
        }
    }
}

void lerArquivo(Jogador jogadores[], FILE *arquivo)
{
    char linha[200];
    int qtdeJogadores = -1;

    while (fgets(linha, sizeof(linha), arquivo) != NULL)
    {
        char substrings[8][100];
        if (qtdeJogadores >= 0)
        {
            dividirString(linha, substrings);
            int ID = atoi(substrings[0]);
            int altura = atoi(substrings[2]);
            int peso = atoi(substrings[3]);
            int anoNascimento = atoi(substrings[5]);

            jogadores[qtdeJogadores].id = ID;
            strcpy(jogadores[qtdeJogadores].nome, substrings[1]);
            jogadores[qtdeJogadores].altura = altura;
            jogadores[qtdeJogadores].peso = peso;
            strcpy(jogadores[qtdeJogadores].universidade, substrings[4]);
            jogadores[qtdeJogadores].anoNascimento = anoNascimento;
            strcpy(jogadores[qtdeJogadores].cidadeNascimento, substrings[6]);
            strcpy(jogadores[qtdeJogadores].estadoNascimento, substrings[7]);
            qtdeJogadores++;
        }
        else
            qtdeJogadores++;
    }
}

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

void criarLog()
{
    FILE *arquivoLog = fopen("/tmp/806482_quicksort.txt", "w");
    fprintf(arquivoLog, "806482\t%d\t%d\t%lf", numComparacoes, numMovimentos * 3, tempoExecucao);
    fclose(arquivoLog);
}

int main()
{
    clock_t inicio, fim;

    char idInput[50];
    Jogador jogadores[3922];
    Jogador subJogadores[500];
    int numeroJogador = 0;
    FILE *arquivo = fopen("/tmp/players.csv", "r");
    do
    {
        scanf("%s", idInput);
        if ((strcmp(idInput, "FIM") != 0) && (strcmp(idInput, "fim") != 0))
        {
            int identificador = atoi(idInput);
            lerArquivo(jogadores, arquivo);
            subJogadores[numeroJogador] = jogadores[identificador];
            numeroJogador++;
        }
    } while ((strcmp(idInput, "FIM") != 0) && (strcmp(idInput, "fim") != 0));
    fclose(arquivo);

    inicio = clock();
    ordenarPorPesoShell(subJogadores, numeroJogador);
    fim = clock();

    for (int i = 0; i < numeroJogador; i++)
    {
        imprimirJogador(i, subJogadores);
    }

    tempoExecucao = (double)(fim - inicio) / CLOCKS_PER_SEC;
    criarLog();
}
