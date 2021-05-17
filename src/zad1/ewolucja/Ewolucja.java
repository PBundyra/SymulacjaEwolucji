package zad1.ewolucja;

import java.io.File;
import java.io.FileNotFoundException;
import zad1.swiat.*;
import zad1.wczytywanie.*;

public class Ewolucja {
    private static Plansza plansza;

    public Ewolucja (File plikPlansza,File plikParametry) throws FileNotFoundException {
        new Parametry(plikParametry);
        plansza = new Plansza(plikPlansza);
    }

    public void symulujEwolucje() {
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
