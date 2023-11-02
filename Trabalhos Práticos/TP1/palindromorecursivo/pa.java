import java.util.*;

class pa {
    
    static boolean isPalRec(String str, int s, int e){
        if (s >= e)
            return true;
        if ((str.charAt(s)) != (str.charAt(e)))
            return false;
        return isPalRec(str, s + 1, e - 1);
    }

    static boolean isPalindrome(String str){
        int n = str.length();
        if (n == 0) return true;

        return isPalRec(str, 0, n - 1);
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        String str;

        do {
            str = scanner.nextLine();
            if (!str.equals("FIM")) {
                System.out.println(isPalindrome(str) ? "SIM" : "NAO");
            }
        } while (!str.equals("FIM"));
        
        scanner.close();
    }        
}
