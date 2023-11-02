public class isrecursivo {

    public static boolean isVowel(String word, int index) {
        if (index == word.length()) {
            return true;
        }

        char character = Character.toLowerCase(word.charAt(index));
        return (character == 'a' || character == 'e' || character == 'i' || character == 'o' || character == 'u')
                && isVowel(word, index + 1);
    }

    public static boolean isConsonant(String word, int index) {
        if (index == word.length()) {
            return true;
        }

        char character = Character.toLowerCase(word.charAt(index));
        return !isVowel(word, 0) && (character >= 'b' && character <= 'z') && isConsonant(word, index + 1);
    }

    public static boolean isInteger(String word, int index) {
        if (index == word.length()) {
            return true;
        }

        char character = word.charAt(index);
        return (character >= '0' && character <= '9') && isInteger(word, index + 1);
    }

    public static boolean isRealNumber(String word, int index, boolean foundDot) {
        if (index == word.length()) {
            return foundDot;
        }

        char character = word.charAt(index);

        if (Character.isDigit(character)) {
            return isRealNumber(word, index + 1, foundDot);
        } else if (character == '.' && !foundDot) {
            return isRealNumber(word, index + 1, true);
        } else if (character == '-' && index == 0) {
            return isRealNumber(word, index + 1, foundDot);
        }

        return false;
    }

    public static String getResponse(boolean result) {
        return result ? "SIM" : "NAO";
    }

    public static boolean isEnd(String word) {
        return (word.length() == 3 && word.equalsIgnoreCase("FIM"));
    }

    public static void main(String[] args) {
        String word = "teste";
        while (!isEnd(word)) {
            word = MyIO.readLine();
            System.out.println(getResponse(isVowel(word, 0)) + " " + getResponse(isConsonant(word, 0)) + " " +
                    getResponse(isInteger(word, 0)) + " " + getResponse(isRealNumber(word, 0, false)));
        }
    }
}
