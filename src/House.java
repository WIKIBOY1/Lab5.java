import javax.xml.bind.annotation.*;
import java.util.Objects;

/**
 * Объекты класса House являются домами, в которых находятся квартиры
 */
@XmlRootElement(name = "house")
@XmlAccessorType(XmlAccessType.FIELD)
public class House {

    @XmlElement(name = "nameHouse")
    private String name; //Поле может быть null

    private Long year; //Значение поля должно быть больше
    private Long numberOfFlatsOnFloor; //Значение поля должно быть больше 0

    public House(String name, Long year, Long numberOfFlatsOnFloor) {
        this.setName(name);
        this.setYear(year);
        this.setNumberOfFlatsOnFloor(numberOfFlatsOnFloor);
    }

    public String getName() {
        return name;
    }

    public Long getNumberOfFlatsOnFloor() {
        return numberOfFlatsOnFloor;
    }

    public Long getYear() {
        return year;
    }

    public House(){}

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
        else{
            System.out.println("Имя дома должно иметь знаение");
        }
    }

    public void setNumberOfFlatsOnFloor(Long numberOfFlatsOnFloor) {
        if(numberOfFlatsOnFloor > 0) {
            this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
        }
        else{
            System.out.println("Количество квартир на этаже должно быть больше 0");
        }
    }

    public void setYear(Long year) {
        if(year > 0) {
            this.year = year;
        }
        else{
            System.out.println("Год постройки дома должен быть больше 0");
        }
    }

    @Override
    public String toString() {
        return "House{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", numberOfFlatsOnFloor=" + numberOfFlatsOnFloor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return name.equals(house.name) &&
                year.equals(house.year) &&
                numberOfFlatsOnFloor.equals(house.numberOfFlatsOnFloor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, numberOfFlatsOnFloor);
    }
}
