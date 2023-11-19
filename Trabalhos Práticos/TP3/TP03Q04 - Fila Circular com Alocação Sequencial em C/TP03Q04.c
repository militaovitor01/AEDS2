#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>

#define MAX_ATTRIBUTES 8
#define MAX_LEN 100

// Estrutura representando um jogador
typedef struct Jogador {
    int id;
    char nome[50];
    int altura;
    int peso;
    char universidade[100];
    int anoNascimento;
    char cidadeNascimento[50];
    char estadoNascimento[50];
} Jogador;

// Função para imprimir informações do jogador
void imprime(Jogador jogadores) {
    printf(" ## %s ## %d ## %d ## %d ## %s ## %s ## %s ##\n",
           jogadores.nome,
           jogadores.altura,
           jogadores.peso,
           jogadores.anoNascimento,
           jogadores.universidade,
           jogadores.cidadeNascimento,
           jogadores.estadoNascimento);
}

// Estrutura para armazenar substrings após dividir uma linha
typedef struct Split {
    char linha[MAX_ATTRIBUTES][MAX_LEN];
} Split;

// Função para dividir uma linha em substrings
void split(char linha[], char substrings[8][100]) {
    int qtSubstrings = 0;
    int cS = 0;
    int c = 0;

    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 100; j++) {
            substrings[i][j] = '\0';
        }
    }

    while (linha[c] != '\0') {
        if (linha[c] != ',') {
            while (linha[c] != ',' && linha[c] != '\0') {
                if (linha[c] == '\n') {
                    c++;
                } else {
                    substrings[qtSubstrings][cS] = linha[c];
                    c++;
                    cS++;
                }
            }
            cS = 0;
            qtSubstrings++;
        } else {
            if (linha[c + 1] == ',' || linha[c + 1] == '\n' || linha[c + 1] == '\0') {
                strcpy(substrings[qtSubstrings], "nao informado");
                qtSubstrings++;
            }
            c++;
        }
    }
}

// Função para ler dados do jogador de um arquivo
void ler(Jogador jogadores[], FILE *file) {
    char linha[200];
    int qtJogadores = -1;

    while (fgets(linha, sizeof(linha), file) != NULL) {
        char substrings[8][100];
        if (qtJogadores >= 0) {
            split(linha, substrings);
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
        } else {
            qtJogadores++;
        }
    }
}

// Função para dividir entrada por espaços
Split SplitSpace() {
    Split Split;

    for (int i = 0; i < 3; i++) {
        scanf("%[^ \n]", Split.linha[i]);
        if (getchar() == '\n') {
            i = 3;
        }
    }

    return Split;
}

// Estrutura representando uma célula na lista ligada
typedef struct Celula {
    Jogador jogador;
    struct Celula *prox;
} Celula;

// Função para criar uma nova célula
Celula *novaCelula(Jogador elemento) {
    Celula *nova = (Celula *)malloc(sizeof(Celula));
    nova->jogador = elemento;
    nova->prox = NULL;

    return nova;
}

// Estrutura representando uma fila circular
typedef struct Fila {
    Celula *primeiro, *ultimo;
    int size, maxSize;

    void (*Inserir)(struct Fila *, Jogador x);
    Jogador (*Remover)(struct Fila *);
    void (*Mostrar)(struct Fila *);
    float (*getMediaAlturas)(struct Fila);
    void (*Close)(struct Fila *);

} Fila;

// Função para calcular a altura média dos jogadores na fila circular
float GetMediaAlturasFilaCircular(Fila fila) {
    float media = 0;
    for (Celula *i = fila.primeiro->prox; i != NULL; i = i->prox) {
        media += i->jogador.altura;
    }

    return media / fila.size;
}

// Função para inserir um elemento no final da fila circular
void InserirFimFilaCircular(Fila *fila, Jogador x) {
    if (fila->size == 0) {
        fila->primeiro = fila->ultimo = novaCelula(x);
    } else if (fila->size == fila->maxSize + 1) {
        fila->Remover(fila);
    }

    fila->ultimo->prox = novaCelula(x);
    fila->ultimo = fila->ultimo->prox;

    fila->size++;

    printf("%.f\n", fila->getMediaAlturas(*fila));
}

// Função para remover um elemento do início da fila circular
Jogador RemoverInicioFilaCircular(Fila *fila) {
    if (fila->primeiro == fila->ultimo) {
        printf("Erro ao remover!");
        exit(1);
    }

    Celula *tmp = fila->primeiro;
    fila->primeiro = fila->primeiro->prox;
    Jogador removido = fila->primeiro->jogador;

    tmp->prox = NULL;
    free(tmp);
    tmp = NULL;

    fila->size--;

    return removido;
}

// Função para mostrar elementos na fila circular
void MostrarFilaCircular(Fila *fila) {
    Celula *i;
    int j = 0;
    for (i = fila->primeiro->prox; i != NULL; i = i->prox, j++) {
        printf("[%d]", j);
        imprime(i->jogador);
    }
}

// Função para fechar a fila circular
void CloseFilaCircular(Fila *fila) {
    Celula *i = fila->primeiro;
    while (i != NULL) {
        i = i->prox;
    }
}

// Função para criar uma nova fila circular
Fila newFila() {
    Fila fila;

    fila.size = 0;
    fila.primeiro = fila.ultimo = NULL;
    fila.maxSize = 4;

    fila.getMediaAlturas = GetMediaAlturasFilaCircular;

    fila.Inserir = InserirFimFilaCircular;

    fila.Remover = RemoverInicioFilaCircular;

    fila.Mostrar = MostrarFilaCircular;
    fila.Close = CloseFilaCircular;

    return fila;
}

// Função principal
void doComando(Fila *fila, Jogador jogadores[]) {
    Split split = SplitSpace();

    // Inserir
    if (strcmp(split.linha[0], "I") == 0) {
        int valor = atoi(split.linha[1]);
        fila->Inserir(fila, jogadores[valor]);
    }

    // Remover
    if (strcmp(split.linha[0], "R") == 0) {
        Jogador jogador = fila->Remover(fila);
        printf("(R) %s\n", jogador.nome);
    }
}

int main() {
    char id[50];
    Jogador jogadores[3922];
    Fila fila = newFila();

    FILE *file = fopen("/tmp/players.csv", "r");

    // Ler dados do arquivo e inserir na fila
    do {
        scanf("%s", id);
        if (strcmp(id, "FIM") != 0 && strcmp(id, "fim") != 0) {
            int identificador = atoi(id);

            ler(jogadores, file);

            fila.Inserir(&fila, jogadores[identificador]);
        }
    } while ((strcmp(id, "FIM") != 0) && (strcmp(id, "fim") != 0));

    fclose(file);

    // Executar comandos
    int action;
    scanf("%i", &action);

    for (int i = 0; i <= action; i++) {
        doComando(&fila, jogadores);
    }

    // Imprimir resultados
    fila.Mostrar(&fila);
    fila.Close(&fila);
}
