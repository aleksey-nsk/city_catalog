package model;

public class City {

    private Long id;
    private String name;        // наименование города
    private String region;      // регион
    private String district;    // федеральный округ
    private Long population;    // количество жителей города
    private Integer foundation; // дата основания или первое упоминание

    public City() {
    }

    public City(Long id, String name, String region, String district, Long population, Integer foundation) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.district = district;
        this.population = population;
        this.foundation = foundation;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Integer getFoundation() {
        return foundation;
    }

    public void setFoundation(Integer foundation) {
        this.foundation = foundation;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", district='" + district + '\'' +
                ", population=" + population +
                ", foundation=" + foundation +
                '}';
    }
}
