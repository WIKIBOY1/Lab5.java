import java.util.ArrayList;
import java.util.List;

/**
 * Класс, хранящий последние 9 введённых команд
 */
public class Commands_history {
    private List<String> commands_history = new ArrayList<String>();

    public void getCommands_history() {
        System.out.println(commands_history);
    }

    public void addPlus(String command){
        if (commands_history.size() == 9){
            commands_history.remove(0);
        }
        commands_history.add(command);
    }

    @Override
    public String toString() {
        return commands_history.toString();
    }
}
