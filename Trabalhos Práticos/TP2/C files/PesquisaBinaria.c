#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>

int numeroComparacoes, numeroMovimentos;
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

void selecao(Jogador jogadores[], int tamanho)
{
    for (int i = 0; i < tamanho - 1; i++)
    {
        int menorIndice = i;
        for (int j = i + 1; j < tamanho; j++)
        {
            numeroComparacoes++;
            if (jogadores[j].nome[0] < jogadores[menorIndice].nome[0])
                menorIndice = j;
            else
            {
                numeroComparacoes++;
                if (jogadores[j].nome[0] == jogadores[menorIndice].nome[0])
                {
                    int posCaractere = 1;
                    while (jogadores[j].nome[posCaractere] == jogadores[menorIndice].nome[posCaractere])
                    {
                        posCaractere++;
                    }
                    numeroComparacoes++;
                    if (jogadores[j].nome[posCaractere] < jogadores[menorIndice].nome[posCaractere])
                        menorIndice = j;
                }
            }
        }
        numeroMovimentos++;
        Jogador auxiliar = jogadores[i];
        jogadores[i] = jogadores[menorIndice];
        jogadores[menorIndice] = auxiliar;
    }
}

bool pesquisaBinaria(Jogador jogadores[], char chave[50], int tamanho)
{
    bool encontrado = false;
    int direita = (tamanho - 1), esquerda = 0, meio;

    while (esquerda <= direita)
    {
        meio = (esquerda + direita) / 2;
        int resultado = strcmp(chave, jogadores[meio].nome);
        if (resultado == 0)
        {
            encontrado = true;
            esquerda = direita + 1;
        }
        else
        {
            int c = 0;
            while (chave[c] == jogadores[meio].nome[c])
            {
                c++;
            }
            if (chave[c] > jogadores[meio].nome[c])
            {
                esquerda = meio + 1;
            }
            else
                direita = meio - 1;
        }
    }
    return encontrado;
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
    FILE *arquivoLog = fopen("806482_binaria.txt", "w");
    fprintf(arquivoLog, "806482\t%d\t%d\t%lf", numeroComparacoes, numeroMovimentos * 3, tempoExecucao);
    fclose(arquivoLog);
}

int main()
{
    clock_t inicio, fim;
    
    char idInput[500];
    
    Jogador jogadores[3922];
    Jogador subJogadores[500];
    int numeroJogador = 0;
    FILE *arquivo = fopen("/tmp/players.csv", "r");
    do
    {
        scanf("%s", idInput);
       
        if (strcmp(idInput, "FIM") != 0 && strcmp(idInput, "fim") != 0    )
        {
            int identificador = atoi(idInput);
            lerArquivo(jogadores, arquivo);
            subJogadores[numeroJogador] = jogadores[identificador];
            numeroJogador++;
        }
    } while (strcmp(idInput, "FIM") != 0 && strcmp(idInput, "fim") != 0   );
    fclose(arquivo);
    selecao(subJogadores, numeroJogador);
    char nomeInput[500];
    do
    {
        scanf(" %[^\r\n]%*c", nomeInput);
       
        if (strcmp(nomeInput, "FIM") != 0 && strcmp(nomeInput, "fim") != 0    )
        {
            if (pesquisaBinaria(subJogadores, nomeInput, numeroJogador))
                printf("SIM\n");
            else
                printf("NAO\n");
        }
    } while (strcmp(nomeInput, "FIM") != 0 && strcmp(nomeInput, "fim") != 0  );
    fim = clock();
    tempoExecucao = fim - inicio;
    criarLog();
}
