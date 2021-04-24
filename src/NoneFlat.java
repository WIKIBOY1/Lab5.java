/**
 * Исключение выбрасывающиеся, при несуществовании квартиры
 */
public class NoneFlat extends NullPointerException{
    public NoneFlat(String message){super(message);}
}
