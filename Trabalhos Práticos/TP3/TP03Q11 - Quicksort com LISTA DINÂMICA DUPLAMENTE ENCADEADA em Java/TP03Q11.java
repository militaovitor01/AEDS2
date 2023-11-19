import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TP03Q11 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File table = new File("/tmp/players.csv");
        DoubleList list = new DoubleList();

        // Entrada de dados dos jogadores na lista
        String query = scanner.nextLine();
        while (!query.equalsIgnoreCase("FIM")) {
            Player player = new Player(query, table);
            list.insertEnd(player);
            query = scanner.nextLine();
        }
        scanner.close();

        // Ordenação da lista e exibição dos jogadores
        list.quickSort();
        list.show();
    }
}

class Player {
    private int id;
    private String name;
    private int height;
    private int weight;
    private String university;
    private int birthYear;
    private String birthCity;
    private String birthState;

    public Player(String query, File table) {
        try {
            Scanner scanner = new Scanner(table);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elements = line.split(",", -1);

                for (int i = 0; i < elements.length; i++) {
                    if (elements[i].isEmpty()) {
                        elements[i] = "not provided";
                    }
                }

                if (query.equals(elements[0]) && elements.length == 8) {
                    setId(Integer.parseInt(elements[0]));
                    setName(elements[1]);
                    setHeight(Integer.parseInt(elements[2]));
                    setWeight(Integer.parseInt(elements[3]));
                    setUniversity(elements[4]);
                    setBirthYear(Integer.parseInt(elements[5]));
                    setBirthCity(elements[6]);
                    setBirthState(elements[7]);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found");
        }
    }

    public Player() {
    }

    public void setId(int id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public void setBirthState(String birthState) {
        this.birthState = birthState;
    }

    public int getId() {
        return this.id;
    }

    public int getHeight() {
        return this.height;
    }

    public String getName() {
        return this.name;
    }

    public int getWeight() {
        return this.weight;
    }

    public String getUniversity() {
        return this.university;
    }

    public int getBirthYear() {
        return this.birthYear;
    }

    public String getBirthCity() {
        return this.birthCity;
    }

    public String getBirthState() {
        return this.birthState;
    }

    public String data() {
        return ("[" +
                getId() +
                " ## " +
                getName() +
                " ## " +
                getHeight() +
                " ## " +
                getWeight() +
                " ## " +
                getBirthYear() +
                " ## " +
                getUniversity() +
                " ## " +
                getBirthCity() +
                " ## " +
                getBirthState() +
                "]");
    }
}

class DoubleCell {
    public Player element;
    public DoubleCell next, previous;

    public DoubleCell() {
        this(null);
    }

    public DoubleCell(Player element) {
        this.element = element;
        this.next = this.previous = null;
    }
}

class DoubleList {
    private DoubleCell first, last;

    public DoubleList() {
        first = new DoubleCell();
        last = first;
    }

    public void insertStart(Player player) {
        DoubleCell tmp = new DoubleCell(player);
        tmp.next = first.next;
        tmp.previous = first;
        first.next = tmp;

        if (first == last) {
            last = tmp;
        } else {
            tmp.next.previous = tmp;
        }
    }

    public void insertEnd(Player player) {
        last.next = new DoubleCell(player);
        last.next.previous = last;
        last = last.next;
    }

    public void insert(Player player, int pos) {
        int size = size();

        if (pos < 0 || pos > size) {
            System.out.println("Error inserting (invalid position)!");
        } else if (pos == 0) {
            insertStart(player);
        } else if (pos == size) {
            insertEnd(player);
        } else {
            DoubleCell i = first;
            for (int j = 0; j < pos; j++, i = i.next);

            DoubleCell tmp = new DoubleCell(player);

            tmp.previous = i;
            tmp.next = i.next;
            tmp.previous.next = tmp.next.previous = tmp;

            tmp = i = null;
        }
    }

    public Player removeStart() {
        if (first == last) {
            System.out.println("Error removing (start)!");
        }

        DoubleCell tmp = first;
        first = first.next;
        Player resp = first.element;

        tmp.next = first.previous = null;
        tmp = null;

        return resp;
    }

    public Player removeEnd() {
        if (first == last) {
            System.out.println("Error removing (end)!");
        }

        Player resp = last.element;
        last = last.previous;

        last.next.previous = null;
        last.next = null;

        return resp;
    }

    public Player remove(int pos) {
        int size = size();
        Player removed = null;

        if (pos < 0 || pos > size) {
            System.out.println("Error removing (invalid position)!");
        }

        if (pos == 0) {
            removeStart();
        }

        if (pos == size - 1) {
            removeEnd();
        } else {
            DoubleCell i = first;
            for (int j = 0; j <= pos; j++, i = i.next);

            removed = i.element;

            i.previous.next = i.next;
            i.next.previous = i.previous;

            i.previous = i.next = null;
            i = null;
        }
        return removed;
    }

    public void show() {
        for (DoubleCell i = first.next; i != null; i = i.next) {
            System.out.println(i.element.data());
        }
    }

    public int size() {
        int size = 0;
        for (DoubleCell i = first; i != last; i = i.next) {
            size++;
        }

        return size;
    }

    public static DoubleCell pivot(DoubleCell left, DoubleCell right) {
        Player pivot = right.element;
        DoubleCell i = left;

        for (DoubleCell j = left; j != right; j = j.next) {
            if (j.element.getBirthState().compareTo(pivot.getBirthState()) < 0) {
                Player tmp = i.element;
                i.element = j.element;
                j.element = tmp;
                i = i.next;
            }
            if (j.element.getBirthState().compareTo(pivot.getBirthState()) == 0) {
                if (j.element.getName().compareTo(pivot.getName()) < 0) {
                    Player tmp = i.element;
                    i.element = j.element;
                    j.element = tmp;
                    i = i.next;
                }
            }
        }

        Player tmp = i.element;
        i.element = right.element;
        right.element = tmp;

        return i;
    }

    public static void doQuick(DoubleCell left, DoubleCell right) {
        if (left != null && right != null && left != right && left.previous != right) {
            DoubleCell pivot = pivot(left, right);
            doQuick(left, pivot.previous);
            doQuick(pivot.next, right);
        }
    }

    public void quickSort() {
        doQuick(first.next, last);
    }
}

