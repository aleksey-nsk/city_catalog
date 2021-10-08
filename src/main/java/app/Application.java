package app;

import service.CityService;

import java.util.Scanner;

public class Application {

    private static final CityService cityService = new CityService();

    public static void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            showMenu();

            String command = scanner.nextLine();

            if (command.equals("1")) {
                cityService.uploadData();
            } else if (command.equals("2")) {
                cityService.printAll();
            } else if (command.equals("3")) {
                cityService.cleanTable();
            } else if (command.equals("4")) {
                cityService.sortByNameDesc();
            } else if (command.equals("5")) {
                cityService.sortByDistrictAndNameDesc();
            } else if (command.equals("6")) {
                cityService.printCityWithMaxPopulation();
            } else if (command.equals("7")) {
                cityService.printCitiesByRegion();
            } else if (command.equals("exit")) {
                System.out.println("Пока!");
                return;
            } else {
                System.out.println("Неизвестная команда");
            }
        }
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("********************************************************************");
        System.out.println("*               ВЫБЕРИТЕ ТРЕБУЕМОЕ ДЕЙСТВИЕ                        *");
        System.out.println("*                                                                  *");
        System.out.println("*  [1] - Загрузить города из файла в БД                            *");
        System.out.println("*                                                                  *");
        System.out.println("*  [2] - Вывести список городов                                    *");
        System.out.println("*                                                                  *");
        System.out.println("*  [3] - Очистить таблицу с городами                               *");
        System.out.println("*                                                                  *");
        System.out.println("*  [4] - Вывести отсортированный список городов, по наименованию,  *");
        System.out.println("*        в алфавитном порядке по убыванию, без учёта регистра      *");
        System.out.println("*                                                                  *");
        System.out.println("*  [5] - Вывести отсортированный список городов, по фед. округу,   *");
        System.out.println("*        и наименованию города внутри фед. округа, в алф. порядке  *");
        System.out.println("*        по убыванию, с учётом регистра                            *");
        System.out.println("*                                                                  *");
        System.out.println("*  [6] - Поиск города с наибольшим количеством жителей             *");
        System.out.println("*                                                                  *");
        System.out.println("*  [7] - Поиск количества городов в разрезе регионов               *");
        System.out.println("*                                                                  *");
        System.out.println("*  [exit] - Выход                                                  *");
        System.out.println("********************************************************************");
    }
}
