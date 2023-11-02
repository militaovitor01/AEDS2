import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Player {

    private int playerId;
    private String playerName;
    private int playerHeight;
    private int playerWeight;
    private String playerUniversity;
    private int playerBirthYear;
    private String playerBirthCity;
    private String playerBirthState;

    public Player(String query, File table) {
        try {
            Scanner scanner = new Scanner(table);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elements = line.split(",", -1);

                for (int i = 0; i < elements.length; i++) {
                    if (elements[i].isEmpty()) {
                        elements[i] = "nao informado";
                    }
                }

                if (query.equals(elements[0]) && elements.length == 8) {
                    setPlayerId(Integer.parseInt(elements[0]));
                    setPlayerName(elements[1]);
                    setPlayerHeight(Integer.parseInt(elements[2]));
                    setPlayerWeight(Integer.parseInt(elements[3]));
                    setPlayerUniversity(elements[4]);
                    setPlayerBirthYear(Integer.parseInt(elements[5]));
                    setPlayerBirthCity(elements[6]);
                    setPlayerBirthState(elements[7]);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Arquivo não encontrado");
        }
    }

    public Player() {
    }

    public void setPlayerId(int id) {
        this.playerId = id;
    }

    private void setPlayerName(String name) {
        this.playerName = name;
    }

    public void setPlayerHeight(int height) {
        this.playerHeight = height;
    }

    public void setPlayerWeight(int weight) {
        this.playerWeight = weight;
    }

    public void setPlayerUniversity(String university) {
        this.playerUniversity = university;
    }

    public void setPlayerBirthYear(int birthYear) {
        this.playerBirthYear = birthYear;
    }

    public void setPlayerBirthCity(String birthCity) {
        this.playerBirthCity = birthCity;
    }

    public void setPlayerBirthState(String birthState) {
        this.playerBirthState = birthState;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public int getPlayerHeight() {
        return this.playerHeight;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getPlayerWeight() {
        return this.playerWeight;
    }

    public String getPlayerUniversity() {
        return this.playerUniversity;
    }

    public int getPlayerBirthYear() {
        return this.playerBirthYear;
    }

    public String getPlayerBirthCity() {
        return this.playerBirthCity;
    }

    public String getPlayerBirthState() {
        return this.playerBirthState;
    }

    public String playerData() {
        return "[" +
            getPlayerId() +
            " ## " +
            getPlayerName() +
            " ## " +
            getPlayerHeight() +
            " ## " +
            getPlayerWeight() +
            " ## " +
            getPlayerBirthYear() +
            " ## " +
            getPlayerUniversity() +
            " ## " +
            getPlayerBirthCity() +
            " ## " +
            getPlayerBirthState() +
            "]";
    }
}

public class SelecaoParcial {

    static int comparisonCounter = 0;

    public static void sort(int end, Player[] players, Player min[], int k) {
        for (int i = 0; i < k; i++) {
            min[i] = players[i];
            comparisonCounter++;
        }

        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < end; j++) {
                if (players[j].getPlayerName().compareTo(min[i].getPlayerName()) < 0) {
                    Player temp = min[i];
                    min[i] = players[j];
                    players[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        File dataFile = new File("/tmp/players.csv");

        Player[] players = new Player[500];
        String query;
        int playerCount = 0;

        do {
            query = scanner.nextLine();

            if (!query.equalsIgnoreCase("FIM")) {
                Player player = new Player(query, dataFile);
                players[playerCount] = player;
                playerCount++;
            }
        } while (!query.equalsIgnoreCase("FIM"));
        scanner.close();

        int k = 10;
        Player[] minPlayers = new Player[k];

        sort(playerCount, players, minPlayers, k);

        for (int i = 0; i < minPlayers.length; i++) {
            System.out.println(minPlayers[i].playerData());
        }
    }
}
