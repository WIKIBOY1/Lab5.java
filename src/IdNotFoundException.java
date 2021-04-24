/**
 * Исключение выбрасывающиеся, при ненахождении элемента с введёным id
 */
public class IdNotFoundException extends NullPointerException{
    public IdNotFoundException(String message){super(message);}
}
