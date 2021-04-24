/**
 * @author Ivanov Georgii R3140
 */
public class Main {
    public static void main(String[] args){
        Client client = new Client(new Presenter(), System.in, false);
        client.analyzePath();
        client.init();
    }
}
