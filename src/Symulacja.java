import java.io.File;
import java.io.FileNotFoundException;

public class Symulacja {
    public static void main(String[] args) throws FileNotFoundException {

        Parametry parametry = new Parametry(new File(args[1]));
        System.out.println(parametry);
        Plansza plansza = new Plansza(new File(args[0]));
        System.out.print(plansza);

        System.out.print("STAN WEJSCIOWY: ");
        plansza.wypiszStan();

        Parametry.getIntParam().get("ile_tur");

        for (int i = 0; i < Parametry.getIntParam().get("ile_tur"); i++) {
            plansza.wykonajTure();
            if ((i + 1) % Parametry.getIntParam().get("co_ile_wypisz") == 0) {
                System.out.print((i +1) +", ");
                plansza.wypiszStan();
            }
        }
    }
}
