import java.io.File;
import java.io.FileNotFoundException;

public class Symulacja {
    public static void main(String[] args) throws FileNotFoundException {

        new Parametry(new File(args[1]));
        Plansza plansza = new Plansza(new File(args[0]));
        System.out.print(plansza);

        System.out.print("STAN WEJSCIOWY: ");
        plansza.wypiszStan();

        for (int i = 0; i < Parametry.getIntParam().get("ile_tur"); i++) {
            plansza.wykonajTure();
            System.out.print((i + 1) + ", ");
            plansza.wypiszStan();
            //if (i % Parametry.getIntParam().get("co_ile_wypisz") == 0) plansza.wypiszRoby();
        }
        //plansza.wypiszRoby();
        System.out.println("dod: " + Plansza.dod + " usun: " + Plansza.usun);
    }
}