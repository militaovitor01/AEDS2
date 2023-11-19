#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>

#define MAXTAM 500
#define MAX_ATTRIBUTES 8
#define MAX_LEN 100

// Estrutura para representar um jogador
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

// Estrutura para dividir a string
typedef struct Split
{
    char linha[MAX_ATTRIBUTES][MAX_LEN];
} Split;

void splitString(char linha[], char substrings[8][100])
{
    int qtSubstrings = 0;
    int posSubstring = 0;
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
                    posLinha++; // Ignorar as quebras de linha
                else
                {
                    substrings[qtSubstrings][posSubstring] = linha[posLinha];
                    posLinha++;
                    posSubstring++;
                }
            }
            posSubstring = 0;
            qtSubstrings++;
        }
        else
        {
            // Condicional para caso o campo esteja vazio
            if (linha[posLinha + 1] == ',' || linha[posLinha + 1] == '\n' || linha[posLinha + 1] == '\0')
            {
                strcpy(substrings[qtSubstrings], "nao informado");
                qtSubstrings++;
            }
            posLinha++;
        }
    }
}

Split splitSpace()
{
    Split split;

    for (int i = 0; i < 3; i++)
    {
        scanf("%[^ \n]", split.linha[i]);
        if (getchar() == '\n')
            i = 3;
    }

    return split;
}

// Método para ler um arquivo e armazenar as informações em um array de jogadores
void lerArquivo(Jogador jogadores[], FILE *file)
{
    char linha[200];
    int qtJogadores = -1; // Inicialização negativa para que a primeira linha seja ignorada

    while (fgets(linha, sizeof(linha), file) != NULL)
    {
        char substrings[8][100];
        if (qtJogadores >= 0)
        {
            splitString(linha, substrings);

            int ID = atoi(substrings[0]);
            int altura = atoi(substrings[2]);
            int peso = atoi(substrings[3]);
            int anoNascimento = atoi(substrings[5]);

            jogadores[qtJogadores].id = ID;
            strcpy(jogadores[qtJogadores].nome, substrings[1]);
            jogadores[qtJogadores].altura = altura;
            jogadores[qtJogadores].peso = peso;
            strcpy(jogadores[qtJogadores].universidade, substrings[4]);
            jogadores[qtJogadores].anoNascimento = anoNascimento;
            strcpy(jogadores[qtJogadores].cidadeNascimento, substrings[6]);
            strcpy(jogadores[qtJogadores].estadoNascimento, substrings[7]);
            qtJogadores++;
        }
        else
        {
            qtJogadores++;
        }
    }
}

void mostrarDadosJogador(Jogador jogador)
{
    printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s]\n",
           jogador.id,
           jogador.nome,
           jogador.altura,
           jogador.peso,
           jogador.anoNascimento,
           jogador.universidade,
           jogador.cidadeNascimento,
           jogador.estadoNascimento);
}

// Estrutura de uma célula em uma lista duplamente encadeada
typedef struct CelulaDupla
{
    Jogador elemento;
    struct CelulaDupla *prox;
    struct CelulaDupla *ant;
} CelulaDupla;

CelulaDupla *novaCelulaDupla(Jogador elemento)
{
    CelulaDupla *nova = (CelulaDupla *)malloc(sizeof(CelulaDupla));
    nova->elemento = elemento;
    nova->ant = nova->prox = NULL;
    return nova;
}

// Estrutura de uma lista duplamente encadeada
typedef struct Lista
{
    CelulaDupla *primeiro, *ultimo;
    int tamanho;

    void (*inserirInicio)(struct Lista *, Jogador x);
    void (*inserirFim)(struct Lista *, Jogador x);
    void (*inserir)(struct Lista *, Jogador x, int pos);

    Jogador (*removerInicio)(struct Lista *);
    Jogador (*removerFim)(struct Lista *);
    Jogador (*remover)(struct Lista *, int pos);

    void (*mostrar)(struct Lista *);
    void (*fechar)(struct Lista *);

} Lista;

// Inserir no início da lista
void inserirInicioListaDupla(Lista *lista, Jogador x)
{
    if (lista->tamanho >= MAXTAM)
    {
        printf("Erro ao inserir no início!");
        exit(1);
    }

    lista->tamanho++;

    CelulaDupla *tmp = novaCelulaDupla(x);

    tmp->ant = lista->primeiro;
    tmp->prox = lista->primeiro->prox;
    lista->primeiro->prox = tmp;

    if (lista->primeiro == lista->ultimo)
    {
        lista->ultimo = tmp;
    }
    else
    {
        tmp->prox->ant = tmp;
    }

    tmp = NULL;
    free(tmp);
}

// Inserir no final da lista
void inserirFimListaDupla(Lista *lista, Jogador x)
{
    if (lista->tamanho >= MAXTAM)
    {
        printf("Erro ao inserir!");
        exit(1);
    }

    lista->ultimo->prox = novaCelulaDupla(x);
    lista->ultimo->prox->ant = lista->ultimo;
    lista->ultimo = lista->ultimo->prox;

    lista->tamanho++;
}

// Remover do início da lista
Jogador removerInicioListaDupla(Lista *lista)
{
    if (lista->tamanho == 0)
    {
        printf("Erro ao remover!");
        exit(1);
    }

    Jogador removido = lista->primeiro->prox->elemento;
    CelulaDupla *tmp = lista->primeiro;

    lista->primeiro = lista->primeiro->prox;

    lista->tamanho--;

    tmp->prox = lista->primeiro->ant = NULL;
    tmp = NULL;
    free(tmp);

    return removido;
}

// Remover do final da lista
Jogador removerFimListaDupla(Lista *lista)
{
    if (lista->tamanho == 0)
    {
        printf("Erro ao remover!");
        exit(1);
    }

    lista->tamanho--;
    Jogador removido = lista->ultimo->elemento;

    lista->ultimo = lista->ultimo->ant;

    lista->ultimo->prox->ant = NULL;
    free(lista->ultimo->prox);
    lista->ultimo->prox = NULL;

    return removido;
}

// Mostrar a lista
void mostrarListaDupla(Lista *lista)
{
    CelulaDupla *celula = lista->primeiro->prox;

    for (int i = 0; i < lista->tamanho; i++)
    {
        mostrarDadosJogador(celula->elemento);
        celula = celula->prox;
    }
}

// Fechar a lista
void fecharListaDupla(Lista *lista)
{
    free(lista->primeiro);
}

// Método para criar uma nova lista
Lista novaLista()
{
    Lista lista;

    lista.tamanho = 0;
    lista.primeiro = novaCelulaDupla((Jogador){0});
    lista.ultimo = lista.primeiro;

    lista.inserirInicio = inserirInicioListaDupla;
    lista.inserirFim = inserirFimListaDupla;

    lista.removerInicio = removerInicioListaDupla;
    lista.removerFim = removerFimListaDupla;

    lista.mostrar = mostrarListaDupla;
    lista.fechar = fecharListaDupla;

    return lista;
}

// Realizar os comandos na lista
void executarComando(Lista *lista, Jogador jogadores[], Lista *removidos)
{
    Split split = splitSpace();

    // Inserir
    if (strcmp(split.linha[0], "II") == 0)
    {
        int valor = atoi(split.linha[1]);
        lista->inserirInicio(lista, jogadores[valor]);
    }

    if (strcmp(split.linha[0], "IF") == 0)
    {
        int valor = atoi(split.linha[1]);
        lista->inserirFim(lista, jogadores[valor]);
    }

    if (strcmp(split.linha[0], "I*") == 0)
    {
        int valor = atoi(split.linha[2]);
        int id = atoi(split.linha[1]);
        lista->inserir(lista, jogadores[valor], id);
    }

    // Remover
    if (strcmp(split.linha[0], "RI") == 0)
    {
        removidos->inserirFim(removidos, lista->removerInicio(lista));
    }

    if (strcmp(split.linha[0], "RF") == 0)
    {
        removidos->inserirFim(removidos, lista->removerFim(lista));
    }

    if (strcmp(split.linha[0], "R*") == 0)
    {
        int id = atoi(split.linha[1]);
        removidos->inserirFim(removidos, lista->remover(lista, id));
    }
}

// Função para escolher o pivô para o algoritmo de ordenação QuickSort
CelulaDupla *escolherPivo(CelulaDupla *esq, CelulaDupla *dir)
{
    Jogador pivo = dir->elemento;
    CelulaDupla *i = esq;

    for (CelulaDupla *j = esq; j != dir; j = j->prox)
    {
        int compareResult = strcmp(j->elemento.estadoNascimento, pivo.estadoNascimento);

        if (compareResult < 0 || (compareResult == 0 && strcmp(j->elemento.nome, pivo.nome) < 0))
        {
            Jogador tmp = i->elemento;
            i->elemento = j->elemento;
            j->elemento = tmp;
            i = i->prox;
        }
    }

    Jogador tmp = i->elemento;
    i->elemento = dir->elemento;
    dir->elemento = tmp;

    return i;
}

// Função principal do algoritmo de ordenação QuickSort
void ordenarQuickSort(CelulaDupla *esq, CelulaDupla *dir)
{
    if (esq != NULL && dir != NULL && esq != dir && esq->ant != dir)
    {
        CelulaDupla *pivo = escolherPivo(esq, dir);
        ordenarQuickSort(pivo->prox, dir);
        ordenarQuickSort(esq, pivo->ant);
    }
}

// Função para ordenar a lista usando o algoritmo QuickSort
void ordenarListaQuickSort(Lista *lista)
{
    ordenarQuickSort(lista->primeiro->prox, lista->ultimo);
}

// Função principal
int main()
{
    char id[50];
    Jogador jogadores[3922];
    Lista lista = novaLista();

    FILE *file = fopen("/tmp/players.csv", "r");
    do
    {
        scanf("%s", id);
        if (strcmp(id, "FIM") != 0 && strcmp(id, "fim") != 0)
        {
            int identificador = atoi(id);
            lerArquivo(jogadores, file);
            lista.inserirFim(&lista, jogadores[identificador]);
        }
    } while ((strcmp(id, "FIM") != 0) && (strcmp(id, "fim") != 0));

    fclose(file);

    ordenarListaQuickSort(&lista);

    // Imprimir os resultados
    lista.mostrar(&lista);
    lista.fechar(&lista);
}
