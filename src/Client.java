import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.*;

/**
 * Класс, реализующий взаимодействие с пользователем
 */
public class Client {

    Presenter presenter;
    private String last_command;
    private InputStream inputStream;
    private boolean standartStream;
    private String last_string;
    private Commands_history commands_history = new Commands_history();
    private boolean scriptFlag;

    public Client(Presenter presenter, InputStream inputStream, boolean scriptFlag){
        this.presenter = presenter;
        this.scriptFlag = scriptFlag;
        last_command = "";
        this.standartStream = this.inputStream == System.in;
        if (!this.standartStream) {
            this.presenter.turnOffLogging();
        }
        this.inputStream = inputStream;
    }

    /**
     * Находит путь по введённому значению переменной окружения
     */
    private String findPath(){
        String path = "";
        boolean m = false;
        Scanner input = new Scanner(System.in);
        String inputString = "";
        Scanner support;
        do {
            if (!this.presenter.isLogging()) {
                System.out.println("Введите имя файла");
            }
            try{
                if (!input.hasNextLine()) {
                    input.close();
                    try {
                        presenter.safe_exit();
                    }catch (NullPointerException e){
                        System.err.println("Нельзя сохранять пустую коллекцию");
                    }
                }
                inputString = input.nextLine();
            }catch (IllegalStateException e){
                System.err.println("Не сегодня");
                System.exit(0);
            }
            if (!inputString.isEmpty()) {
                support = new Scanner(inputString);
                if (support.hasNext()) {
                    path = support.next();
                    m = true;
                }
            }
        } while (!m);
        return path;
    }

    /**
     * Анализирует на коректность введённого значения переменной окружения
     */
    public void analyzePath(){
        String path = findPath();
        if (path == null){
            System.out.println("Неверный путь.");
            System.exit(1);
        }
        try {
            File file = new File(System.getenv(path));
            presenter.setZeroCollection(file);
            if (!presenter.getZeroCollection().exists()) {
                System.out.println("Файла по указанному пути не существует.");
                System.out.println(System.getenv(path));
                System.exit(1);
            }
            this.fillUp();
            this.presenter.setLogging(true);
        }catch (NullPointerException e){
            System.err.println("Неверная переменная окружения");
            tryAgain();
        }catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Даёт шанс пользователю снова ввести значение переменной окружния
     */
    private void tryAgain(){
        System.out.println("Хотите попытаться ввести ещё раз(1 - Да, 2 - Нет)?");
        Scanner newScanner = new Scanner(System.in);
        if(presenter.addSomething(check(newScanner))){
            analyzePath();
        }
        else{
            System.out.println("Было приятно с вами поработать");
            System.exit(0);
        }
    }
    /**
     * Десериализует коллекцию из файла xml
     */
    public void fillUp() throws JAXBException{
        try {
            if (!presenter.getZeroCollection().canRead() || !presenter.getZeroCollection().canWrite()) throw new SecurityException();
        } catch (SecurityException ex) {
            if (!this.presenter.isLogging()) {
                System.out.println("Не хватает прав доступа для работы с файлом.");
            }
            System.exit(1);
        }
        try{
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(presenter.getZeroCollection()));
            JAXBContext context = JAXBContext.newInstance(Flats.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Flats newFlats = (Flats) unmarshaller.unmarshal(bufferedInputStream);
            presenter.setFlats(newFlats);
            Iterator iterator = presenter.getFlats().iterator();
            System.out.println("Программа готова к работе");
        } catch (UnmarshalException e){
            System.err.println("Файл должен быть формата xml");
            tryAgain();
        }catch ( NullPointerException | FileNotFoundException e){
            System.err.println("В файле не было коллекции");
            tryAgain();
        }
    }

    /**
     * Проверка на конец потока вывода(Ctrl + D)
     */
    private String check(Scanner in){
        try{
            return last_string = in.nextLine().trim();
        } catch (NoSuchElementException ex) {
            in.close();
            System.err.println("Хорошая попытка. Попоробуйте что-нибудь посложнее");
            presenter.safe_exit();
            return "";
        }
    }

    /**
     * Добавление квартиры
     */
    public Flat addFlat(Scanner in){
        String nameFlat=null;
        double X = 0;
        float Y = 0;
        int i=0;
        float area1 = 0;
        Furnish furnish= null;
        View view = null;
        Transport transport = null;
        long numberOfRooms = 0;
        boolean houseAnswer = false;
        House house1 = null;
        boolean m = false;
        do {
            try {
                if(i==0){
                    System.out.println("Введите название квартиры: ");
                    nameFlat = presenter.addName(check(in));
                    i++;
                }
                if(i==1) {
                    System.out.println("Введите координату x: ");
                    X = presenter.addX(check(in));
                    i++;
                }
                if(i==2){
                    System.out.println("Введите координату y(больше -850): ");
                    Y = presenter.addY(check(in));
                    i++;
                }
                if(i==3){
                    System.out.println("Введите площадь(больше 0): ");
                    area1 = presenter.addArea(check(in));
                    i++;
                }
                if(i==4){
                    System.out.println("Введите количество комнат(больше 0): ");
                    numberOfRooms = presenter.addLong(check(in));
                    i++;
                }
                if(i==5){
                    System.out.println("Выберете вид 1-3 (" + Arrays.toString(View.values()) + "): ");
                    view = presenter.addView(check(in));
                    i++;
                }
                if(i==6){
                    System.out.println("Выберете транспорт 1-5(" + Arrays.toString(Transport.values()) + ") или 0, если ничего не знаете о транспорте: ");
                    transport = presenter.addTransport(check(in));
                    i++;
                }
                if(i==7){
                    System.out.println("Выберете мебель 1-5(" + Arrays.toString(Furnish.values()) + "): ");
                    furnish = presenter.addFurnish(check(in));
                    i++;
                }
                if(i==8){
                    System.out.println("Хотите ввести дом[Да - 1, Нет - 2]?");
                    houseAnswer = presenter.addSomething(check(in));
                    i++;
                    m = true;
                }
                if(houseAnswer) {
                    house1 = addHouse(in);
                }
            }catch (IncorrectInputException e) {
                System.err.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Вы ввели неверные данные");
            }
        }while (!m);
        return new Flat(nameFlat,new Coordinates(X, Y), area1, numberOfRooms, furnish, view, transport, house1);
    }

    /**
     * Добовление дома
     */
    public House addHouse(Scanner in){
        long year1 = 0;
        long numberOfFlatsOnFloor1;
        int i = 0;
        String nameHouse1=null;
        House house1 = null;
        boolean m = false;
        do {
            try {
                if (i == 0) {
                    System.out.println("Введите имя дома: ");
                    nameHouse1 = presenter.addName(check(in));
                    i++;
                }
                if (i == 1) {
                    System.out.println("Введите год постройки дома(больше 0): ");
                    year1 = presenter.addLong(check(in));
                    i++;
                }
                if (i == 2) {
                    System.out.println("Введите количество квартир на этаже(больше 0): ");
                    numberOfFlatsOnFloor1 = presenter.addLong(check(in));
                    i++;
                    house1 = new House(nameHouse1, year1, numberOfFlatsOnFloor1);
                    m = true;
                }
            } catch (IncorrectInputException e) {
                System.err.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Вы ввели неверные данные");
            }
        }while (!m);
        return house1;
    }

    /**
     * Счиывание id из командной строки
     */
    public int readId(Scanner in, Scanner stringScanner){
        int id = 0;
        boolean control = stringScanner.hasNextInt();
        boolean m1 = false;
        if (control) {
            id = stringScanner.nextInt();
        } else {
            do {
                System.out.println("Введите id элемента: ");
                try {
                    String idString = check(in);
                    id = Integer.parseInt(idString);
                    m1 = true;
                } catch (IncorrectInputException e) {
                    System.err.println(e.getMessage());
                } catch (NumberFormatException e) {
                    System.err.println("Вы ввели неверные данные");
                }
            }while (!m1);
        }
        return id;
    }

    /**
     * Реализация командной строки
     */
    public void init(){
        Scanner in = new Scanner(this.inputStream);
        do {
            try {
                last_string = in.nextLine().trim();
            } catch (NoSuchElementException ex) {
                in.close();
                try {
                    presenter.safe_exit();
                }catch (NullPointerException e){
                System.err.println("Нельзя сохранять пустую коллекцию");
                }
            }

            if (last_string.isEmpty()) {
                System.out.println("Введите команду");
                last_command = "";
                continue;
            }
            Scanner stringScanner = new Scanner(last_string);
            last_command = stringScanner.next();
            boolean control = stringScanner.hasNextInt();
            switch (last_command) {
                case "help":
                    for (Map.Entry entry : presenter.help().entrySet()) {
                        System.out.println(entry);
                    }
                    commands_history.addPlus("help");
                    break;
                case "add":
                    presenter.add(addFlat(in));
                    System.out.println("Элемент успешно добавлен");
                    commands_history.addPlus("add");
                    break;
                case "exit":
                    break;
                case "show":
                    try {
                        System.out.println(presenter.show());
                    }catch (NullPointerException e){
                        System.err.println("В введённом файле отсутствовала коллекция");
                    }
                    commands_history.addPlus("show");
                    break;
                case "update":
                    try {
                        presenter.updateId(readId(in, stringScanner), addFlat(in));
                    }catch (IdNotFoundException e){
                        System.err.println(e.getMessage());
                    }
                    System.out.println("Элемент успешно обновлён");
                    commands_history.addPlus("update");
                    break;
                case "remove_by_id":
                    try {
                        presenter.remove_by_id(readId(in, stringScanner));
                        System.out.println("Элемент успешно удалён");
                    }catch (IdNotFoundException e){
                        System.err.println(e.getMessage());
                    }
                    commands_history.addPlus("remove_by_id");
                    break;
                case "save":
                    try {
                        presenter.save();
                        System.out.println("Коллекция успешно сохранена");
                    }catch (NullPointerException e){
                        System.err.println("Нельзя сохранять пустую коллекцию");
                    }
                    commands_history.addPlus("save");
                    break;
                case "remove_lower":
                    try {
                        presenter.remove_lower(addFlat(in));
                        System.out.println("Квартиры меньше введённой успешно удалены");
                    }catch (NoneFlat e){
                        System.err.println(e.getMessage());
                    }
                    commands_history.addPlus("remove_lower");
                    break;
                case "print_ascending":
                    System.out.println(presenter.print_ascending());
                    commands_history.addPlus("print_ascending");
                    break;
                case "filter_contains_name":
                    control = stringScanner.hasNext();
                    boolean m3 = false;
                    if (control) {
                        String name = stringScanner.next();
                        try {
                            System.out.println(presenter.filter_contains_name(name));
                        }
                        catch (NoneFlat e){
                            System.out.println(e.getMessage());
                        }
                    } else {
                        do{
                          System.out.println("Введите имя элемента: ");
                        try {
                            String name = presenter.addName(check(in));
                            System.out.println(presenter.filter_contains_name(name));
                            m3=true;
                        } catch (NoneFlat e) {
                            System.err.println(e.getMessage());
                            m3=true;
                        } catch (NumberFormatException e) {
                            System.err.println("Вы ввели неверные данные");
                        }
                        } while (!m3);
                    }
                    commands_history.addPlus("filter_contains_name");
                    break;
                case "remove_all_by_house":
                    try {
                        presenter.remove_all_by_house(addHouse(in));
                        System.out.println("Все квартиры из введёного дома удалены");
                    }catch (NullPointerException e){
                        System.err.println(e.getMessage());
                    }
                    commands_history.addPlus("remove_all_by_house");
                    break;
                case "history":
                    commands_history.getCommands_history();
                    commands_history.addPlus("history");
                    break;
                case "remove_greater":
                    try {
                        presenter.remove_greater(addFlat(in));
                        System.out.println("Квартиры больше введённой успешно удалены");
                    }catch (NoneFlat e){
                        System.err.println(e.getMessage());
                    }
                    commands_history.addPlus("remove_greater");
                    break;
                case "clear":
                    presenter.clear();
                    System.out.println("Коллекция успешно очищена");
                    commands_history.addPlus("clear");
                    break;
                case "info":
                    if (!this.standartStream) {
                        System.out.println(presenter.info());
                    }
                    commands_history.addPlus("info");
                    break;
                case "execute_script":
                    if(!this.scriptFlag) {
                        control = stringScanner.hasNext();
                        boolean m4 = false;
                        if (control) {
                            String filename = stringScanner.next();
                            try {
                                presenter.execute_script(filename);
                            } catch (FileNotFoundException e) {
                                System.err.println("Файла по указанному пути не существует");
                            } catch (NumberFormatException e) {
                                System.err.println("Вы ввели неверные данные");
                            }catch (IncorrectInputException e){
                                System.err.println(e.getMessage());
                            }
                        } else {
                            do {
                                System.out.println("Введите имя файла: ");
                                try {
                                    String filename = presenter.addName(check(in));
                                    presenter.execute_script(filename);
                                    m4=true;
                                } catch (FileNotFoundException e) {
                                    System.err.println("Файла по указанному пути не существует");
                                    m4=true;
                                } catch (NumberFormatException e) {
                                    System.err.println("Вы ввели неверные данные");
                                }catch (IncorrectInputException e){
                                    System.err.println(e.getMessage());
                                }
                            }while (!m4);
                        }
                    }
                    else{
                            System.out.println("Нельзя использовать execute_script в файле execute_script");
                        }
                    commands_history.addPlus("execute_script");
                    break;
                default:
                    if (!this.standartStream) {
                        System.out.println("Неизвестная команда. Вы можете посмотреть список команд с помощью 'help'\n");
                    }
            }
        } while (!last_command.equals("exit")) ;
    }
}
