package app;

import service.CityService;
import service.impl.CityServiceImpl;

import java.util.Scanner;

public class Application {

    private final CityService cityService = new CityServiceImpl();

    public void run() {
        // Класс Scanner нужен для чтения данных из входящих потоков. Это может быть консоль приложения, файл и т.д.
        // Внутри скобок передаём (System.in) так как необходимо, чтобы сканер считывал данные из консоли
        Scanner scanner = new Scanner(System.in);

        while (true) {
            showMenu();

            // Метод nextLine() обращается к источнику данных, и считывает строку
            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    cityService.uploadData();
                    break;
                case "2":
                    cityService.printAll();
                    break;
                case "3":
                    cityService.cleanTable();
                    break;
                case "4":
                    cityService.sortByNameDesc();
                    break;
                case "5":
                    cityService.sortByDistrictAndNameDesc();
                    break;
                case "6":
                    cityService.printCityWithMaxPopulation();
                    break;
                case "7":
                    cityService.printCitiesByRegion();
                    break;
                case "exit":
                    System.out.println("Выход");
                    return;
                default:
                    System.out.println("Неизвестная команда");
                    break;
            }
        }
    }

    private void showMenu() {
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
