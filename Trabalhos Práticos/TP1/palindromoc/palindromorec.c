#include <stdio.h>
#include <stdbool.h>
#include <string.h>

#define TAM_MAX_STR 400
#define CARACTERE_ESPECIAL -61 // Caracteres especiais (e.g., ç, á, à, ã, etc)
#define UnicoCaractere(c) (c > 0)

bool ehPalindromoIterativo(char str[]) {
    int inicio = -1, fim = strlen(str);
    bool resultado = true;

    while (inicio++ <= fim--) {
        char caractereInicio = str[inicio], caractereFim = str[fim];

        // Verificando caracteres alfanuméricos.
        if (UnicoCaractere(caractereInicio) || UnicoCaractere(caractereFim)) {
            if (caractereInicio != caractereFim) {
                inicio = fim;
                resultado = false;
            }
        } else if (caractereInicio == CARACTERE_ESPECIAL) {
            // Verificando caracteres especiais (e.g., ç, á, à, ã, etc).
            if (str[inicio + 1] != caractereFim) {
                inicio = fim;
                resultado = false;
            }
            inicio++, fim--;
        }
    }

    if (resultado) {
        return true;
    } else {
        return false;
    }
}

bool ehPalindromoRecursivo(char palavra[], int inicio, int fim) {
    char caractereInicio = palavra[inicio], caractereFim = palavra[fim];

    if (UnicoCaractere(caractereInicio) || UnicoCaractere(caractereFim)) {
        if (caractereInicio != caractereFim) {
            return false;
        }
    } else if (caractereInicio == CARACTERE_ESPECIAL) {
        if (palavra[inicio + 1] != caractereFim) {
            return false;
        }
        inicio++, fim--;
    }

    if (inicio > fim) {
        return true;
    } else {
        return ehPalindromoRecursivo(palavra, inicio + 1, fim - 1);
    }
}

bool ehPalindromo(char palavra[]) {
    return ehPalindromoRecursivo(palavra, 0, strlen(palavra) - 1);
}

int main() {
    char str[TAM_MAX_STR];

    while (strcmp(fgets(str, TAM_MAX_STR, stdin), "FIM\n") != 0) {
        if (ehPalindromo(str)) {
            printf("SIM \n");
        } else {
            printf("NAO \n");
        }
    }

    return 0;
}
