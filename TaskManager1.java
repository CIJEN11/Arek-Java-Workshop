import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager1 {
    static final String FILE_NAME = "tasks.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;

    public static void main(String[] args) {
        tasks = loadDataToTab(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printOptions(OPTIONS);
            switch (scanner.nextLine()) {
                case "exit":
                    saveTabToFile(FILE_NAME, tasks);
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    return;
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(getTheNumber());
                    break;
                case "list":
                    printTab(tasks);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
        }
    }

    public static void printOptions(String[] tab) {
        System.out.println(ConsoleColors.BLUE + "Please select an option: " + ConsoleColors.RESET);
        for (String option : tab) System.out.println(option);
    }

    public static int getTheNumber() {
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println("Please select number to remove.");
        while (!isNumberGreaterEqualZero(input = scanner.nextLine())) {
            System.out.println("Incorrect argument passed. Please give number greater or equal 0");
        }
        return Integer.parseInt(input);
    }

    private static void removeTask(int index) {
        if (index < tasks.length) {
            tasks = ArrayUtils.remove(tasks, index);
            System.out.println("Value was successfully deleted.");
        } else {
            System.out.println("Element does not exist in tab");
        }
    }

    public static boolean isNumberGreaterEqualZero(String input) {
        return NumberUtils.isParsable(input) && Integer.parseInt(input) >= 0;
    }

    public static void printTab(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.println(i + " : " + String.join(" ", tab[i]));
        }
    }

    private static void addTask() {
        Scanner scanner = new Scanner(System.in);
        String[] newTask = new String[3];
        System.out.println("Please add task description");
        newTask[0] = scanner.nextLine();
        System.out.println("Please add task due date");
        newTask[1] = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        newTask[2] = scanner.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = newTask;
    }

    public static String[][] loadDataToTab(String fileName) {
        Path dir = Paths.get(fileName);
        if (!Files.exists(dir)) {
            System.out.println("File does not exist.");
            System.exit(0);
        }

        try {
            List<String> lines = Files.readAllLines(dir);
            String[][] tab = new String[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                tab[i] = lines.get(i).split(",");
            }
            return tab;
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0][0];
        }
    }

    public static void saveTabToFile(String fileName, String[][] tab) {
        Path dir = Paths.get(fileName);
        try {
            Files.write(dir, Arrays.asList(Arrays.stream(tab)
                    .map(row -> String.join(",", row))
                    .toArray(String[]::new)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
