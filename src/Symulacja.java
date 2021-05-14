import java.io.File;
import java.io.FileNotFoundException;

public class Symulacja {
    public static void main(String[] args) throws FileNotFoundException {

        Parametry parametry = new Parametry(new File(args[1]));
        System.out.println(parametry);
        Plansza plansza = new Plansza(new File(args[0]), parametry);
        System.out.print(plansza);

        System.out.print("STAN WEJSCIOWY: ");
        plansza.wypiszStan();
        for (int i = 0; i < parametry.getIleTur(); i++) {
            plansza.wykonajTure();
            if ((i + 1) % parametry.getCoIleWypisz() == 0) {
                System.out.print((i +1) +", ");
                plansza.wypiszStan();
            }
        }
    }
}
