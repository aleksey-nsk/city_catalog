package service;

/**
 * @author Aleksey Zhdanov
 * @version 1
 */
public interface CityService {

    /**
     * <p>Загружает города из файла в БД</p>
     */
    void uploadData();

    /**
     * <p>Выводит список городов</p>
     */
    void printAll();

    /**
     * <p>Очищает таблицу с городами</p>
     */
    void cleanTable();

    /**
     * <p>Выводит список городов, отсортированный по наименованию</p>
     */
    void sortByNameDesc();

    /**
     * <p>Выводит список городов, отсортированный по фед. округу и наименованию</p>
     */
    void sortByDistrictAndNameDesc();

    /**
     * <p>Ищет город с наибольшим количеством жителей</p>
     */
    void printCityWithMaxPopulation();

    /**
     * <p>Определяет количество городов в разрезе регионов</p>
     */
    void printCitiesByRegion();
}
