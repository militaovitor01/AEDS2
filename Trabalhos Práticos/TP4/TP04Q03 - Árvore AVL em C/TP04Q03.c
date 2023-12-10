#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>
#include "math.h"
#define MAXTAM 500
#define MAX_ATTRIBUTES 8
#define MAX_LEN 100

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

typedef struct Split
{
    char linha[MAX_ATTRIBUTES][MAX_LEN];
} Split;

void split(char linha[], char substrings[8][100])
{
    int qtSubstrings = 0;
    int cS = 0; 
    int c = 0;  
    
    for (int i = 0; i < 8; i++)
    {
        for (int j = 0; j < 100; j++)
        {
            substrings[i][j] = '\0';
        }
    }
    
    while (linha[c] != '\0')
    {
        if (linha[c] != ',')
        {
            while (linha[c] != ',' && linha[c] != '\0')
            {
                if (linha[c] == '\n')
                    c++; 
                else
                {
                    substrings[qtSubstrings][cS] = linha[c];
                    c++;
                    cS++;
                }
            }
            cS = 0;
            qtSubstrings++;
        }
        else
        {
            
            if (linha[c + 1] == ',' || linha[c + 1] == '\n' || linha[c + 1] == '\0')
            {
                strcpy(substrings[qtSubstrings], "nao informado");
                qtSubstrings++;
            }
            c++;
        }
    }
}

Split SplitSpace()
{ 
    Split Split;

    for (int i = 0; i < 3; i++)
    {
        scanf("%[^ \n]", Split.linha[i]);
        if (getchar() == '\n')
            i = 3;
    }

    return Split;
}

void ler(Jogador jogadores[], FILE *file)
{

    char linha[200];
    int qtJogadores = -1;

    while (fgets(linha, sizeof(linha), file) != NULL)
    {
        char substrings[8][100];
        if (qtJogadores >= 0)
        {
            split(linha, substrings);
            // conversao de strings para inteiros
            int ID = atoi(substrings[0]);
            int h = atoi(substrings[2]);
            int p = atoi(substrings[3]);
            int ano = atoi(substrings[5]);

            jogadores[qtJogadores].id = ID;
            strcpy(jogadores[qtJogadores].nome, substrings[1]);
            jogadores[qtJogadores].altura = h;
            jogadores[qtJogadores].peso = p;
            strcpy(jogadores[qtJogadores].universidade, substrings[4]);
            jogadores[qtJogadores].anoNascimento = ano;
            strcpy(jogadores[qtJogadores].cidadeNascimento, substrings[6]);
            strcpy(jogadores[qtJogadores].estadoNascimento, substrings[7]);
            qtJogadores++;
        }
        else
            qtJogadores++;
    }
}

void dados(Jogador jogadores)
{
    printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s]\n",
           jogadores.id,
           jogadores.nome,
           jogadores.altura,
           jogadores.peso,
           jogadores.anoNascimento,
           jogadores.universidade,
           jogadores.cidadeNascimento,
           jogadores.estadoNascimento);
}

typedef struct No
{
    char *elemento;       
    struct No *dir, *esq; 
    int nivel;            

} No;

No *newNo(char *elemento)
{
    No *nova = (No *)malloc(sizeof(No));
    nova->elemento = elemento;
    nova->esq = nova->dir = NULL;
    nova->nivel = 1; 
    return nova;
}

int max(int a, int b)
{
    return (a > b) ? a : b;
}


int getNivel(No *no)
{
    return (no == NULL) ? 0 : no->nivel;
}


void setNivel(No *no)
{
    no->nivel = max(getNivel(no->esq), getNivel(no->dir)) + 1;
}


int getBalanceamento(No *no)
{
    return (no == NULL) ? 0 : getNivel(no->dir) - getNivel(no->esq);
}

// ROTAÇÕES//
No *rotacaoDireita(No *no)
{

    No *noEsq = no->esq;
    No *noEsqDir = noEsq->dir;

    noEsq->dir = no;

    no->esq = noEsqDir;

    setNivel(no);
    setNivel(noEsq);

    return noEsq;
}
No *rotacaoEsquerda(No *no)
{

    No *noDir = no->dir;
    No *noDirEsq = noDir->esq;

    noDir->esq = no;

    no->dir = noDirEsq;

    setNivel(no);
    setNivel(noDir);

    return noDir;
}

No *balancear(No *no)
{

    if (no != NULL)
    {
        int fator = getNivel(no->dir) - getNivel(no->esq);
        if (abs(fator) <= 1)
        {
            setNivel(no);
        }

        else if (fator == 2)
        {
            int nivelFilho = getNivel(no->dir->dir) - getNivel(no->dir->esq);

            if (nivelFilho == -1)
            {
                no->dir = rotacaoDireita(no->dir);
            }
            no = rotacaoEsquerda(no);
        }
        else if (fator == -2)
        {
            int nivelFilho = getNivel(no->esq->dir) - getNivel(no->esq->esq);

            if (nivelFilho == 1)
            {
                no->esq = rotacaoEsquerda(no->esq);
            }

            no = rotacaoDireita(no);
        }
        else
        {
            printf("!ERRO!, fator = %d", fator);
        }
    }
    return no;
}

// INSERIR NA ARVORE//
No *inserir(char *string, No *i)
{

    if (i == NULL)
    {
        i = newNo(string);
    }
    else if ((strcmp(string, (i)->elemento) < 0))
    {
        i->esq = inserir(string, i->esq);
    }
    else if ((strcmp(string, i->elemento) > 0))
    {
        i->dir = inserir(string, i->dir);
    }
    else
    {
        printf("Erro ao inserir!");
    }

    return balancear(i);
}

bool pesquisar(char nome[], No *i)
{
    bool resp = false;

    if (i == NULL)
    {
        resp = false;
    }
    else if (strcmp(nome, i->elemento) < 0)
    {
        printf(" esq");
        resp = pesquisar(nome, i->esq);
    }
    else if ((strcmp(nome, i->elemento) > 0))
    {
        printf(" dir");
        resp = pesquisar(nome, i->dir);
    }
    else
    {
        resp = true;
    }

    return resp;
}

//-------------------------CLASSE ARVORE------------------------//
typedef struct AVL
{
    No *raiz;

} AVL;

AVL *newAVL()
{
    AVL *arvore = (AVL *)malloc(sizeof(AVL));
    arvore->raiz = NULL;
    return arvore;
}

int main()
{

    char id[50];
    char nome[50];
    Jogador jogadores[3922];
    AVL *arvore = newAVL();

    FILE *file = fopen("/tmp/players.csv", "r");
    do
    {
        scanf("%s", id);
        if (strcmp(id, "FIM") != 0 && strcmp(id, "fim") != 0)
        {
            int identificador = atoi(id);
            ler(jogadores, file);
            arvore->raiz = inserir(jogadores[identificador].nome, arvore->raiz);
        }
    } while ((strcmp(id, "FIM") != 0) && (strcmp(id, "fim") != 0));

    fclose(file);

    do
    {

        scanf(" %[^\n]", nome);

        if (strcmp(nome, "FIM") != 0 && strcmp(nome, "fim") != 0)
        {

            printf("%s raiz", nome);
            if (pesquisar(nome, arvore->raiz))
            {
                printf(" SIM\n");
            }
            else
            {
                printf(" NAO\n");
            }
        }
    } while (strcmp(nome, "FIM") != 0 && strcmp(nome, "fim") != 0);

    return 0;
}