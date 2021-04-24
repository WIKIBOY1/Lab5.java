import javax.xml.bind.annotation.*;

/**
 * Класс для координат квартиры - объектов коллекции
 */
@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates {
    private double x;
    private float y; //Значение поля должно быть больше -850

    public Coordinates(double x, float y) {
        this.setX(x);
        this.setY(y);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Coordinates(){}

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
