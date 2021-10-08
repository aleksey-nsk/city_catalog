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

    private static final CityDao cityDao = new CityDao();

    public void uploadData() {
        City city = new City();

        try {
            FileReader fileReader = new FileReader("./my_file/cities.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("Одна строка из файла: " + line);
                String tmp[] = line.split(";");
                city.setName(tmp[0]);
                city.setRegion(tmp[1]);
                city.setDistrict(tmp[2]);
                city.setPopulation(Long.parseLong(tmp[3]));
                city.setFoundation(Integer.parseInt(tmp[4]));
                System.out.println("Один объект типа City: " + city);
                if (cityDao.isCityExistByNameAndRegion(city)) {
                    System.out.println("В базе уже существует город с таким именем и регионом!\n");
                } else {
                    cityDao.save(city);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printAll() {
        List<City> all = cityDao.findAll();

        System.out.println("All cities:");
        for (City city : all) {
            System.out.println("  " + city);
        }
    }

    public void cleanTable() {
        cityDao.deleteAll();
        System.out.println("Table was cleaned");
    }

    public void sortByNameDesc() {
        List<City> all = cityDao.findAll();

        Comparator<City> nameComparatorDescIgnoreCase = (o1, o2) -> o2.getName().compareToIgnoreCase(o1.getName());

        List<City> sortedList = all.stream()
                .sorted(nameComparatorDescIgnoreCase)
                .collect(Collectors.toList());

        System.out.println("All cities sorted by name desc:");
        for (City city : sortedList) {
            System.out.println("  " + city);
        }
    }

    public void sortByDistrictAndNameDesc() {
        List<City> all = cityDao.findAll();

        Comparator<City> districtComparatorDesc = (o1, o2) -> o2.getDistrict().compareTo(o1.getDistrict());
        Comparator<City> nameComparatorDesc = (o1, o2) -> o2.getName().compareTo(o1.getName());

        List<City> sortedList = all.stream()
                .sorted(districtComparatorDesc.thenComparing(nameComparatorDesc))
                .collect(Collectors.toList());

        System.out.println("All cities sorted by district and name desc:");
        for (City city : sortedList) {
            System.out.println("  " + city);
        }
    }

    public void printCityWithMaxPopulation() {
        List<City> all = cityDao.findAll();

        Comparator<City> populationComparatorDesc = (o1, o2) -> o2.getPopulation().compareTo(o1.getPopulation());

        List<City> sortedList = all.stream()
                .sorted(populationComparatorDesc)
                .collect(Collectors.toList());

        System.out.println("City with max population:");

        if (sortedList.size() != 0) {
            City cityWithMaxPopulation = sortedList.get(0);
            System.out.println("  " + cityWithMaxPopulation);
            System.out.printf("  [%d] = %d %n", cityWithMaxPopulation.getId(), cityWithMaxPopulation.getPopulation());
        }
    }

    public void printCitiesByRegion() {
        List<City> all = cityDao.findAll();

        Map<String, Long> citiesByRegion = all.stream().collect(
                Collectors.groupingBy(City::getRegion, Collectors.counting())
        );

        System.out.println("Количество городов в разрезе регионов:");
        for (Map.Entry<String, Long> group : citiesByRegion.entrySet()) {
            System.out.println("  " + group.getKey() + " - " + group.getValue());
        }
    }
}
