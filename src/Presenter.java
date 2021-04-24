import javax.xml.bind.*;
import java.io.*;
import java.util.*;
import java.io.FileOutputStream;

/**
 * Класс реализующий команды и первод xml to java object и наоборот
 */
public class Presenter {
    private Flats flats;
    private boolean scriptFlag = false;
    private boolean logging;

    private File zeroCollection;

    public Presenter(){}

    public boolean isLogging() {
        return logging;
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }

    public File getZeroCollection() {
        return zeroCollection;
    }

    public void setZeroCollection(File zeroCollection) {
        this.zeroCollection = zeroCollection;
    }

    public void turnOffLogging() {
        this.logging = false;
    }

    public void setScriptFlag(boolean scriptFlag) {
        this.scriptFlag = scriptFlag;
    }

    public boolean isScriptFlag() {
        return scriptFlag;
    }

    public TreeSet<Flat>  getFlats() {
        return flats.getFlats();
    }

    public void setFlats(Flats flats) {
        this.flats = flats;
    }

    /**
     * Выводит информацию о коллекции
     */
    public String info(){
        return "Количество элементов : " + flats.getFlats().size() + "\n" + "Дата инициализации : " + flats.getCreationDateString()
        + "\n" + "Тип коллекции : " + flats.getClass();
    }

    /**
     * Сохраняет коллекцию в xml файл
     */
    public void save(){
        try {
            Flats flats1 = new Flats();
            flats1.setFlats(flats.getFlats());
            JAXBContext jaxbContext = JAXBContext.newInstance(Flats.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(System.getenv("Collection")));
            jaxbMarshaller.marshal(flats1, fileOutputStream);
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Выводит список доступных команд для работы с коллекцией
     */
    public  Map<String, String> help() {
       return Flats.getDictionary();
    }

    /**
     * Выводит элементы коллекции flats
     */
    public String show(){
        return flats.toString();
    }

    /**
     * Очищает коллекцию flats
     */
    public void clear(){
        flats.getFlats().clear();
    }

    /**
     *Выводит все элементы коллекции flats, которые содержат в имени вводимую подстроку name
     * @param name - подстрока, которую должно содержать имя
     */
    public TreeSet<Flat> filter_contains_name(String name) throws NoneFlat{
        TreeSet<Flat> newFlats = new TreeSet<>(Comparator.comparing(Flat::getId));
        int i = 0;
        for(Flat flat: flats.getFlats()){
           if (flat.getName().contains(name)){
               i++;
               newFlats.add(flat);
           }
        }
        if(i == 0) {
            throw new NoneFlat("Ничего не найдено");
        }
        return newFlats;
    }

    /**
     * Безопасный выход. Сохраняет коллекцию в xml файл и выходит из программы.
     */
    public void safe_exit(){
        save();
        System.exit(0);
    }

    /**
     * Добавляет элемент в коллекцию
     */
    public void add(Flat flat){
        flats.getFlats().add(flat);
    }

    /**
     * Добавляет координату X
     */
    public double addX(String inputString){
        if (!inputString.isEmpty()) {
            return Double.parseDouble(inputString);
        }else{
            throw new IncorrectInputException("Введите что-нибудь");
        }
    }

    /**
     * Добавляет координату Y
     */
    public float addY(String inputString){
        if (!inputString.isEmpty()) {
            float y = Float.parseFloat(inputString);
            if (y > -850) {
                return y;
            } else {
                throw new IncorrectInputException("Введёные данные не коректны");
            }
        }
        else{
            throw new IncorrectInputException("Введите что-нибудь");
        }
    }

    /**
     * Добавляет имя
     */
    public String addName(String inputString){
        if (!inputString.isEmpty()) {
            return inputString;//.split(" ")[0];
        }
        else {
            throw new IncorrectInputException("Введите что-нибудь");
        }
    }

    /**
     * Добавляет площадь
     */
    public float addArea(String inputString){
        if (!inputString.isEmpty()) {
            float AREA = Float.parseFloat(inputString);
            if (AREA > 0) {
               return AREA;
            }else{
                throw new IncorrectInputException("Введёные данные не коректны");
            }
        }
        else{
            throw new IncorrectInputException("Введите что-нибудь");
        }
    }

    /**
     * Добавляет переменную типа Long(numberOfRooms, year, numberOfFlatsOnFloor)
     */
    public long addLong(String inputString){
        if (!inputString.isEmpty()) {
            long longNumber = Long.parseLong(inputString);
            if (longNumber > 0) {
                return longNumber;
            }else{
                throw new IncorrectInputException("Вы ввели неверные данные, значение должно быть больше 0");
            }
        }
        else{
            throw new IncorrectInputException("Введите что-нибудь");
        }
    }

    /**
     * Добавляет вид
     */
    public View addView(String inputString){
        if (!inputString.isEmpty()) {
            int intNumber = Integer.parseInt(inputString);
            if (intNumber >= 1 && intNumber <= 3) {
                return View.values()[intNumber - 1];
            }else{
                throw new IncorrectInputException("Вы ввели неверные данные, аргумент должен принимать значения от 1 до 3");
            }
        }
        else {
            throw new IncorrectInputException("Введите что-нибудь");
        }
    }

    /**
     * Добавляет количество транспарта
     */
    public Transport addTransport(String inputString){
        if (!inputString.isEmpty()) {
            int intNumber = Integer.parseInt(inputString);
            if (intNumber >= 0 && intNumber <= 5) {
                if(intNumber > 0) {
                    return Transport.values()[intNumber - 1];
                }
                else{
                    return null;
                }
            }
            else{
                throw new IncorrectInputException("Вы ввели неверные данные, аргумент должен принимать значения от 0 до 5");
            }
        }
        else {
            throw new IncorrectInputException("Введите что-нибудь");
        }
    }

    /**
     * Добавляет состояние мебели
     */
    public Furnish addFurnish(String inputString){
        if (!inputString.isEmpty()) {
            int intNumber = Integer.parseInt(inputString);
            if (intNumber >= 1 && intNumber <= 5) {
                return Furnish.values()[intNumber - 1];
            }else{
                throw new IncorrectInputException("Вы ввели неверные данные, аргумент должен принимать значения от 1 до 5");
            }
        }
        else {
            throw new IncorrectInputException("Введите что-нибудь");
        }
    }

    /**
     * Добавляет что-то(квартиру или попытку ввести данные заново)
     */
    public boolean addSomething(String inputString){
        if (!inputString.isEmpty()) {
            int answer = Integer.parseInt(inputString);
            if (answer == 1) {
                return true;
            } else if (answer == 2) {
                return false;
            } else {
                throw new IncorrectInputException("Неверный ответ. Попробуйте ещё раз");
            }
        }else {
            throw new IncorrectInputException("Введите что-нибудь");
        }
    }

    /**
     * Обновляет элемент с вводимым id
     * @param id - id элемента
     */
    public void updateId(int id, Flat flat){
        int i = 0;
        TreeSet<Flat> flats1 = new TreeSet<>(flats.getFlats());
        for (Flat flat1 : flats1){
            if (flat1.getId() == id){
                flats.getFlats().remove(flat1);
                flat.setId(id);
                flats.getFlats().add(flat);
                i++;
            }
        }
        if(i==0){
            throw new IdNotFoundException("Записи с таким id не существует");
        }
    }

    /**
     * Удаляет элемент из коллекции с вводимым id
     * @param identify - id элемента
     */
    public void remove_by_id (int identify){
        int i = 0;
        TreeSet<Flat> flats1 = new TreeSet<>(flats.getFlats());
        for (Flat flat : flats1) {
            if (flat.getId() == identify) {
                flats.getFlats().remove(flat);
                i++;
            }
        }
        if(i==0){
            throw new IdNotFoundException("Записи с таким id не существует");
        }
    }

    /**
     * Выводит все элементы коллекции flats, у которых совпал объект House с вводимым house
     * @param house - дом, в котором находится квартира
     */
    public void remove_all_by_house(House house){
        int i =0;
        TreeSet<Flat> newFlats = new TreeSet<>(flats.getFlats());
        for (Flat flat: newFlats){
            if(flat.getHouse().equals(house)){
                flats.getFlats().remove(flat);
                i++;
            }
        }
        if (i==0) {
           throw new IdNotFoundException("Дома с такими данными нет");
        }
    }

    /**
     * Удаляет все элементы коллекции, у которых hashcode больше заданного
     */
    public void remove_greater(Flat flat){
        remove(true, flat);
    }

    /**
     * Внутренний метод, который занимается удалением элемента коллекции с большим или меньшим hashcode, чем у заданного
     * @param greatest - переменная отвечаящая за удаление больших или меньших элементов
     */
    private void remove(boolean greatest, Flat flat){
        int i = 0;
        ArrayList<Flat> newFlats = new ArrayList<>(flats.getFlats());
        if(greatest) {
            for (Flat flat1 : newFlats) {
                if (flat1.hashCode() > flat.hashCode()) {
                    flats.getFlats().remove(flat1);
                    i++;
                }
            }
            if (i == 0) {
                throw new NoneFlat("Больше квартир нет");
            }
        }
        else{
            for(Flat flat1 : newFlats){
                if(flat1.hashCode() < flat.hashCode()){
                    flats.getFlats().remove(flat1);
                    i++;
                }
            }
            if(i == 0) {
                throw new NoneFlat("Меньше квартир нет");
            }
        }
    }

    /**
     * Выполняет скрипт программ, содержащихся в файле
     * @param filename - имя файла
     */
    public void execute_script(String filename) throws FileNotFoundException {
        if(!isScriptFlag()) {
                InputStream inputStream = new FileInputStream(filename);
                setScriptFlag(true);
                Client my = new Client(new Presenter(), inputStream, scriptFlag);
                my.analyzePath();
                my.init();
        }
        setScriptFlag(false);
    }

    /**
     * Удаляет все элементы коллекции, у которых hashcode меньше заданного
     */
    public void remove_lower(Flat flat){
        remove(false, flat);
    }

     /**
     * Выводит элементы коллекции в порядке возрастания
     */
    public ArrayList<Flat> print_ascending(){
        ArrayList<Flat> flatsExample = new ArrayList<>(flats.getFlats());
        ArrayList<Flat> newFlats = new ArrayList<>();
        while(flatsExample.size() > 0) {
            Flat flat = flatsExample.get(0);
            for (Flat flat1 : flatsExample) {
                if (flat1.hashCode() <= flat.hashCode()) {
                    flat = flat1;
                }
            }
            newFlats.add(flat);
            flatsExample.remove(flat);
        }
        return newFlats;
    }

    @Override
    public String toString() {
        return flats.getClass() + "";
    }
}
