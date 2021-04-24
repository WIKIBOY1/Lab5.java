import java.time.LocalDateTime;
import java.util.Objects;
import javax.xml.bind.annotation.*;

/**
 * Объекты класса Flat, которые хранятся в коллекции
 */
@XmlRootElement(name =  "flat")
@XmlAccessorType(XmlAccessType.FIELD)
public class Flat {

    @XmlTransient
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null

    @XmlElement(name = "creationDate")
    private String dateTimeString;
    @XmlTransient
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private Float area; //Значение поля должно быть больше 0
    private Long numberOfRooms; //Значение поля должно быть больше 0
    private Furnish furnish; //Поле не может быть null
    private View view; //Поле не может быть null
    private Transport transport; //Поле может быть null
    private House house; //Поле может быть null

    private static int statId = 1;

    public Flat(){
        this.id = statId;
        this.setCreationDate(LocalDateTime.now());
        this.setDateTimeString(creationDate.toString());
        statId++;
    }

    public Flat (int id){
        this.id = id;
    }

    public static int getStatId() {
        return statId;
    }

    public Flat(String name, Coordinates coordinates, Float area, Long numberOfRooms, Furnish furnish, View view, Transport transport, House house){
        this.setName(name);
        this.id = statId;
        this.setCoordinates(coordinates);
        this.setCreationDate(LocalDateTime.now());
        this.setArea(area);
        this.setNumberOfRooms(numberOfRooms);
        this.setFurnish(furnish);
        this.setView(view);
        this.setTransport(transport);
        this.setHouse(house);
        this.setDateTimeString(creationDate.toString());
        statId++;
    }

    public void setDateTimeString(String dateTimeString) {
        this.dateTimeString = dateTimeString;
    }

    public int getId(){
        return id;
    }

    public House getHouse() {
        return this.house;
    }

    public String getName() {
        return this.name;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public Float getArea() {
        return this.area;
    }

    public Furnish getFurnish() {
        return this.furnish;
    }

    public Long getNumberOfRooms() {
        return this.numberOfRooms;
    }

    public Transport getTransport() {
        return this.transport;
    }

    public View getView() {
        return this.view;
    }

    @Override
    public String toString() {
        String house;
        if(getHouse() == null){
            house = ", дома нет(";
        }else {
            house = ", имя дома = " + getHouse().getName() + ", год постройки = " + getHouse().getYear()
                    + ", количество квартир на этаже = " + getHouse().getNumberOfFlatsOnFloor();
        }
        return "id = " + getId() + ", имя = " + getName() + ", координата x = " + getCoordinates().getX()
                + ", координата y = " + getCoordinates().getY() + ", время создания = " + getCreationDate()
                + ", площадь = " + getArea() + ", число комнат = " + getNumberOfRooms() + ", состояние мебели = "
                + getFurnish() + ", вид = " + getView() + ", количество транспорта = " + getTransport()
                + house;
    }

    public void setName(String name) {
        if(name!= null) {
            this.name = name;
        }
        else{
            System.out.println("Имя дома должно иметь знаение");
        }
    }

    public void setId(int id) {
        this.id = id;
        statId--;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        this.dateTimeString = creationDate.toString();
    }

    public void setArea(Float area) {
        if(area > 0) {
            this.area = area;
        }
        else{
            System.out.println("Имя дома должно иметь знаение");
        }
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setFurnish(Furnish furnish) {
        this.furnish = furnish;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public void setNumberOfRooms(Long numberOfRooms) {
        if(numberOfRooms > 0) {
            this.numberOfRooms = numberOfRooms;
        }
        else{
            System.out.println("Имя дома должно иметь знаение");
        }
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flat flat = (Flat) o;
        return id == flat.id &&
                Objects.equals(name, flat.name) &&
                Objects.equals(coordinates, flat.coordinates) &&
                Objects.equals(dateTimeString, flat.dateTimeString) &&
                Objects.equals(creationDate, flat.creationDate) &&
                Objects.equals(area, flat.area) &&
                Objects.equals(numberOfRooms, flat.numberOfRooms) &&
                furnish == flat.furnish &&
                view == flat.view &&
                transport == flat.transport &&
                Objects.equals(house, flat.house);
    }

    @Override
    public int hashCode() {
        return (int) (this.area + this.numberOfRooms + this.getHouse().getYear() + this.getHouse().getNumberOfFlatsOnFloor());
    }
}
