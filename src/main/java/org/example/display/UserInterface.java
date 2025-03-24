package org.example.display;

import org.example.collection.CustomArrayList;
import org.example.model.Bus;
import org.example.model.Student;
import org.example.model.User;
import org.example.strategy.DataFillingStrategy;
import org.example.strategy.FileFillingStrategy;
import org.example.strategy.ManualFillingStrategy;
import org.example.strategy.RandomFillingStrategy;
import org.example.generator.DataGenerators;
import org.example.algorithms.SelectionSort;
import org.example.algorithms.BinarySearch;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;

public class UserInterface {
    private final Scanner scanner = new Scanner(System.in);
    private CustomArrayList<?> currentData;
    private int currentDataType; // 1 - Bus, 2 - Student, 3 - User

    public void start() {
        while (true) {
            printMainMenu();
            int choice = readIntInput("Выберите действие: ", 1, 5);

            switch (choice) {
                case 1 -> fillDataMenu();
                case 2 -> performSorting();
                case 3 -> performSearch();
                case 4 -> printCurrentData();
                case 5 -> {
                    System.out.println("Выход из программы...");
                    System.exit(0);
                }
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n=== Главное меню ===");
        System.out.println("1. Заполнить массив данных");
        System.out.println("2. Отсортировать данные");
        System.out.println("3. Найти элемент");
        System.out.println("4. Вывести текущие данные");
        System.out.println("5. Выйти из программы");
    }

    private void fillDataMenu() {
        System.out.println("\n=== Выбор типа данных ===");
        System.out.println("1. Автобусы (Bus)");
        System.out.println("2. Студенты (Student)");
        System.out.println("3. Пользователи (User)");
        System.out.println("4. Назад");

        currentDataType = readIntInput("Выберите тип данных: ", 1, 4);
        if (currentDataType == 4) return;

        System.out.println("\n=== Способ заполнения ===");
        System.out.println("1. Ввести данные вручную");
        System.out.println("2. Загрузить из файла");
        System.out.println("3. Сгенерировать случайные данные");
        System.out.println("4. Назад");

        int fillMethod = readIntInput("Выберите способ заполнения: ", 1, 4);
        if (fillMethod == 4) return;

        int size = readIntInput("Укажите количество элементов: ", 1, Integer.MAX_VALUE);

        String filePath = null;
        if (fillMethod == 2) {
            filePath = readFilePath();
        }

        switch (currentDataType) {
            case 1 -> currentData = fillBusData(fillMethod, size, filePath);
            case 2 -> currentData = fillStudentData(fillMethod, size, filePath);
            case 3 -> currentData = fillUserData(fillMethod, size, filePath);
        }

        System.out.println("\nДанные успешно загружены!");
        askToPrintData();
        askForNextAction();
    }

    private void askToPrintData() {
        if (askYesNo("Хотите вывести данные на экран? (да/нет): ")) {
            printCurrentData();
        }
    }

    private void askForNextAction() {
        System.out.println("\nЧто вы хотите сделать дальше?");
        System.out.println("1. Отсортировать данные");
        System.out.println("2. Найти элемент");
        System.out.println("3. Вернуться в главное меню");

        int choice = readIntInput("Выберите действие: ", 1, 3);
        switch (choice) {
            case 1 -> performSorting();
            case 2 -> performSearch();
            case 3 -> {}
        }
    }

    private void performSorting() {
        if (checkEmptyData()) return;

        System.out.println("\nВыполняется сортировка выбором...");
        sortCurrentData();
        System.out.println("Данные успешно отсортированы!");
        askToPrintData();
        askForNextAction();
    }

    private void performSearch() {
        if (checkEmptyData()) return;

        System.out.println("\n=== Поиск элемента ===");
        searchCurrentData();
        askForNextAction();
    }

    private void sortCurrentData() {
        switch (currentDataType) {
            case 1 -> SelectionSort.selectionSort((CustomArrayList<Bus>) currentData);
            case 2 -> SelectionSort.selectionSort((CustomArrayList<Student>) currentData);
            case 3 -> SelectionSort.selectionSort((CustomArrayList<User>) currentData);
        }
    }

    private void searchCurrentData() {
        switch (currentDataType) {
            case 1 -> {
                Bus bus = createBusManually();
                int index = BinarySearch.binarySearch((CustomArrayList<Bus>) currentData, bus);
                printSearchResult(index, bus);
            }
            case 2 -> {
                Student student = createStudentManually();
                int index = BinarySearch.binarySearch((CustomArrayList<Student>) currentData, student);
                printSearchResult(index, student);
            }
            case 3 -> {
                User user = createUserManually();
                int index = BinarySearch.binarySearch((CustomArrayList<User>) currentData, user);
                printSearchResult(index, user);
            }
        }
    }

    private <T> void printSearchResult(int index, T element) {
        if (index >= 0) {
            System.out.println("\nЭлемент найден на позиции: " + index);
            System.out.println("Найденный элемент: " + element);
        } else {
            System.out.println("\nЭлемент не найден в массиве");
        }
    }

    private boolean checkEmptyData() {
        if (currentData == null || currentData.isEmpty()) {
            System.out.println("Нет данных для операции. Сначала заполните массив.");
            return true;
        }
        return false;
    }

    private void printCurrentData() {
        System.out.println("\n=== Текущие данные ===");
        currentData.forEach(System.out::println);
    }

    private boolean askYesNo(String question) {
        while (true) {
            System.out.print(question);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("да")) {
                return true;
            } else if (input.equals("n") || input.equals("нет")) {
                return false;
            }
            System.out.println("Пожалуйста, введите 'y' или 'n'");
        }
    }

    private CustomArrayList<Bus> fillBusData(int method, int size, String filePath) {
        Map<Integer, DataFillingStrategy<Bus>> strategies = new HashMap<>();
        strategies.put(1, new ManualFillingStrategy<>(this::createBusManually));
        strategies.put(2, new FileFillingStrategy<>(filePath, parts ->
                new Bus.Builder()
                        .number(Integer.parseInt(parts[0]))
                        .model(parts[1])
                        .mileage(Double.parseDouble(parts[2]))
                        .build()));
        strategies.put(3, new RandomFillingStrategy<>(DataGenerators.BusGenerator::randomBus));

        return strategies.get(method).fillData(size);
    }

    private CustomArrayList<Student> fillStudentData(int method, int size, String filePath) {
        Map<Integer, DataFillingStrategy<Student>> strategies = new HashMap<>();
        strategies.put(1, new ManualFillingStrategy<>(this::createStudentManually));
        strategies.put(2, new FileFillingStrategy<>(filePath, parts ->
                new Student.Builder()
                        .groupNumber(Integer.parseInt(parts[0]))
                        .averageGrade(Double.parseDouble(parts[1]))
                        .recordBookNumber(Integer.parseInt(parts[2]))
                        .build()));
        strategies.put(3, new RandomFillingStrategy<>(DataGenerators.StudentGenerator::randomStudent));

        return strategies.get(method).fillData(size);
    }

    private CustomArrayList<User> fillUserData(int method, int size, String filePath) {
        Map<Integer, DataFillingStrategy<User>> strategies = new HashMap<>();
        strategies.put(1, new ManualFillingStrategy<>(this::createUserManually));
        strategies.put(2, new FileFillingStrategy<>(filePath, parts ->
                new User.Builder()
                        .name(parts[0])
                        .password(parts[1])
                        .email(parts[2])
                        .build()));
        strategies.put(3, new RandomFillingStrategy<>(DataGenerators.UserGenerator::randomUser));

        return strategies.get(method).fillData(size);
    }

    private Bus createBusManually() {
        int number = readIntInput("Введите номер автобуса (1-999): ", 1, 999);
        System.out.print("Введите модель: ");
        String model = scanner.nextLine();
        double mileage = readDoubleInput("Введите пробег (0-1000000): ", 0, 1000000);

        return new Bus.Builder()
                .number(number)
                .model(model)
                .mileage(mileage)
                .build();
    }

    private Student createStudentManually() {
        int groupNumber = readIntInput("Введите номер группы (1-100): ", 1, 100);
        double averageGrade = readDoubleInput("Введите средний балл (2-5): ", 2, 5);
        int recordBookNumber = readIntInput("Введите номер зачетки (100000-999999): ", 100000, 999999);

        return new Student.Builder()
                .groupNumber(groupNumber)
                .averageGrade(averageGrade)
                .recordBookNumber(recordBookNumber)
                .build();
    }

    private User createUserManually() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите пароль (мин. 6 символов): ");
        String password = scanner.nextLine();
        System.out.print("Введите email: ");
        String email = scanner.nextLine();

        return new User.Builder()
                .name(name)
                .password(password)
                .email(email)
                .build();
    }

    private int readIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Ошибка: введите число от " + min + " до " + max);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число");
            }
        }
    }

    private double readDoubleInput(String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Ошибка: введите число от " + min + " до " + max);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число");
            }
        }
    }

    private String readFilePath() {
        System.out.print("Введите путь к файлу: ");
        return scanner.nextLine();
    }
}