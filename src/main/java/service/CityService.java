package service;

import dao.CityDao;
import model.City;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CityService {

    private final CityDao cityDao = new CityDao();

    public void uploadData() {
        try {
            FileReader fileReader = new FileReader("./files/cities.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println("Одна строка из файла: " + line);
                String[] temp = line.split(";");
                City city = new City();
                city.setName(temp[0]);
                city.setRegion(temp[1]);
                city.setDistrict(temp[2]);
                city.setPopulation(Long.parseLong(temp[3]));
                city.setFoundation(Integer.parseInt(temp[4]));
//                System.out.println("Один объект типа City: " + city);
                if (!cityDao.isCityExistByNameAndRegion(city)) {
                    cityDao.save(city);
                } else {
//                    System.out.println("В базе уже существует город с таким именем и регионом!\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Города были загружены из файла в БД");
    }

    public void printAll() {
        List<City> all = cityDao.findAll();

        System.out.println("Список всех городов:");
        for (City city : all) {
            System.out.println("  " + city);
        }
    }

    public void cleanTable() {
        cityDao.deleteAll();
        System.out.println("Таблица очищена");
    }

    public void sortByNameDesc() {
        List<City> all = cityDao.findAll();

        Comparator<City> nameComparator = (o1, o2) -> o2.getName().compareToIgnoreCase(o1.getName());

        List<City> sortedList = all.stream()
                .sorted(nameComparator)
                .collect(Collectors.toList());

        System.out.println("Список городов, отсортированный по наименованию:");
        for (City city : sortedList) {
            System.out.println("  " + city);
        }
    }

    public void sortByDistrictAndNameDesc() {
        List<City> all = cityDao.findAll();

        Comparator<City> districtComparator = (o1, o2) -> o2.getDistrict().compareTo(o1.getDistrict());
        Comparator<City> nameComparator = (o1, o2) -> o2.getName().compareTo(o1.getName());

        List<City> sortedList = all.stream()
                .sorted(districtComparator.thenComparing(nameComparator))
                .collect(Collectors.toList());

        System.out.println("Список городов, отсортированный по фед. округу и наименованию:");
        for (City city : sortedList) {
            System.out.println("  " + city);
        }
    }

    public void printCityWithMaxPopulation() {
        List<City> all = cityDao.findAll();

        Comparator<City> populationComparator = (o1, o2) -> o2.getPopulation().compareTo(o1.getPopulation());

        List<City> sortedList = all.stream()
                .sorted(populationComparator)
                .collect(Collectors.toList());

        System.out.println("Город с наибольшим количеством жителей:");
        if (sortedList.size() != 0) {
            City cityWithMaxPopulation = sortedList.get(0);
//            System.out.println("  " + cityWithMaxPopulation);
            System.out.printf("  [%d] = %d %n", cityWithMaxPopulation.getId(), cityWithMaxPopulation.getPopulation());
        }
    }

    public void printCitiesByRegion() {
        List<City> all = cityDao.findAll();

        Map<String, Long> citiesByRegion = all.stream().collect(
                Collectors.groupingBy(City::getRegion, Collectors.counting())
        );

        System.out.println("Количество городов в разрезе регионов:");
        for (String region : citiesByRegion.keySet()) {
            System.out.println("  " + region + " - " + citiesByRegion.get(region));
        }
    }
}
