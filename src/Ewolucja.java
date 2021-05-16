import java.io.File;
import java.io.FileNotFoundException;

public class Ewolucja {
    private static Plansza plansza;

    public Ewolucja (File plikPlansza,File plikParametry) throws FileNotFoundException {
        new Parametry(plikParametry);
        this.plansza = new Plansza(plikPlansza);
    }

    public void symulujEwolucje() {
        System.out.println("STAN WEJSCIOWY ROBÓW: ");
        plansza.wypiszRoby();
        for (int i = 0; i < Parametry.getIntParam().get("ile_tur"); i++) {
            if (!plansza.wypiszStanSym(i + 1)) {
                break;
            }
            plansza.wykonajTure();
            if ((i + 1) % Parametry.getIntParam().get("co_ile_wypisz") == 0) plansza.wypiszRoby();
        }
        plansza.wypiszRoby();
    }

}
