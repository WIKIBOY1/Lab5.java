import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Класс, с помощью которого осуществляется перевод из xml в java object и обратно, и в котором хранятся данные.
 */
@XmlRootElement(name = "flats")
@XmlAccessorType(XmlAccessType.FIELD)
public class Flats {
    @XmlElement(name = "flat")
    private TreeSet<Flat> flats = new TreeSet<>(Comparator.comparing(Flat::getId));

    @XmlTransient
    private java.time.LocalDate creationDate;
    @XmlTransient
    private String creationDateString;

    protected static Map<String, String> dictionary = new HashMap<>();

    static {
        dictionary.put("info ", " выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.).");
        dictionary.put("show ", " выводит в стандартный поток вывода все элементы коллекции в строковом представлении.");
        dictionary.put("add {element} ", " добавляет новый элемент в коллекцию.");
        dictionary.put("update id {element} ", " обновляет значение элемента коллекции, id которого равен заданному.");
        dictionary.put("remove_by_id id ", " удаляет элемент из коллекции по его id.");
        dictionary.put("clear ", " очищает коллекцию.");
        dictionary.put("save ", " сохраняет коллекцию в файл.");
        dictionary.put("execute_script file_name ", " считывает и исполняет скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        dictionary.put("exit ", " завершает программу (без сохранения в файл).");
        dictionary.put("remove_greater {element} ", " удаляет из коллекции все элементы, превышающие заданный.");
        dictionary.put("remove_lower {element} ", " удаляет из коллекции все элементы, меньшие, чем заданный.");
        dictionary.put("history ", " выводит последние 9 команд (без их аргументов).");
        dictionary.put("remove_all_by_house house ", " удаляет из коллекции все элементы, значение поля house которого эквивалентно заданному.");
        dictionary.put("filter_contains_name name ", " выводит элементы, значение поля name которых содержит заданную подстроку.");
        dictionary.put("print_ascending ", " выводит элементы коллекции в порядке возрастания.");
    }

    public static Map<String, String> getDictionary() {
        return dictionary;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationDateString() {
        return creationDateString;
    }

    public  TreeSet<Flat>  getFlats(){
        return this.flats;
    }

    public Flats(){
        setCreationDate(LocalDate.now());
        setCreationDateString(creationDate.toString());
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDateString(String creationDateString) {
        this.creationDateString = creationDateString;
    }

    public void setFlats(TreeSet<Flat> flats){
        this.flats = flats;
    }

    @Override
    public String toString() {
        String flatsString = "";
        for (Flat flat : flats){
            flatsString += "\n" + flat.toString();
        }
        return flatsString;
    }
}