/**
 * Исключение выбрасывающиеся, при некоректных данных
 */
public class IncorrectInputException extends RuntimeException{
    public IncorrectInputException(String message){super(message);}
}
