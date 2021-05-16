package swiat;

import java.util.ArrayList;
import java.util.Random;

import wczytywanie.*;

/*
 * ArrayList'a program składa się ze danych typu String zamiast Character z uwagi na potencjalne rozszerzanie programu
 * o wieloznakowe instrukcje
 */
public class Rob {
    private Zwrot zwrot;
    private Pole polozenie;
    private final ArrayList<String> program;
    private int wiek;
    private int energia;
    private Mutacja mutacja;

    public Rob(Pole polozenie) {
        this.wiek = 0;
        this.program = Parametry.getStringParam().get("pocz_progr");
        this.energia = Parametry.getIntParam().get("pocz_energia");
        this.zwrot = Zwrot.dajLosowyZwrot();
        this.polozenie = polozenie;
        this.mutacja = new Mutacja();
    }

    public Rob(int energia, Zwrot zwrot, ArrayList<String> program, Pole polozenie) {
        this.wiek = -1;
        this.energia = energia;
        this.zwrot = zwrot;
        this.polozenie = polozenie;
        this.program = program;
        this.mutacja = new Mutacja();

    }

    private void zmutuj() {
        mutacja.usunInstr(this);
        mutacja.dodajInstr(this);
        mutacja.zamienInstr(this);
    }

    public void powiel() {
        if (this.energia >= Parametry.getIntParam().get("limit_powielania")) {
            if (Math.random() <= Parametry.getDoubleParam().get("pr_powielenia")) {
                int energiaPotomka = (int) ((Parametry.getDoubleParam().get("ułamek_energii_rodzica") * (double) energia));
                this.energia -= energiaPotomka;
                Zwrot zwrotPotomka = Zwrot.dajPrzeciwny(this.zwrot);
                ArrayList<String> kopiaProgramu = new ArrayList<>(this.program);
                Rob potomek = new Rob(energiaPotomka, zwrotPotomka, kopiaProgramu, polozenie);
                potomek.zmutuj();
                Plansza.dodajRoba(potomek);
            }
        }
    }

    /*
     * Funkcja sprawdza czy na przekazanym w argumencie Polu znajduje się pożywienie.
     * Jeśli tak to je zjada i zwraca true, jeśli nie to zwaraca false
     */
    private boolean zjedzJesliMozliwe(Pole pole) {
        if (pole.getClass().getSimpleName().equals("PoleZywieniowe") &&
                ((PoleZywieniowe) pole).getCzyJestPozywienie()) {
            ((PoleZywieniowe) pole).zjedzPozywienie();
            this.energia += Parametry.getIntParam().get("ile_daje_jedzenie");
            return true;
        }
        return false;
    }

    public void wykonajProgram() {
        for (String instrukcja : this.program) {

            if (energia <= 0) break;
            energia--;

            switch (instrukcja) {
                case "l":
                    this.zwrot = Zwrot.obrocWLewo(this.zwrot);
                    break;
                case "p":
                    this.zwrot = Zwrot.obrocWPrawo(this.zwrot);
                    break;
                case "i":
                    this.polozenie = Plansza.getPlansza()[Math.floorMod(this.polozenie.getWspolrzedne().get("x")
                            + zwrot.dajX() - 1, Plansza.dajSzerPlanszy())]
                            [Math.floorMod(this.polozenie.getWspolrzedne().get("y")
                            + zwrot.dajY() - 1, Plansza.dajDlPlanszy())];
                    this.zjedzJesliMozliwe(polozenie);
                    break;
                case "w":
                    for (int i = 0; i < 4; i++) {
                        Zwrot pomZwrot = Zwrot.obrocWLewo(this.zwrot);
                        int pomX = Math.floorMod(polozenie.getWspolrzedne().get("x") + pomZwrot.dajX(), Plansza.dajSzerPlanszy());
                        int pomY = Math.floorMod(polozenie.getWspolrzedne().get("y") + pomZwrot.dajY(), Plansza.dajDlPlanszy());
                        Pole pomPole = Plansza.getPlansza()[pomX][pomY];
                        if (pomPole.getClass().getSimpleName().equals("PoleZywieniowe")
                                && ((PoleZywieniowe) pomPole).getCzyJestPozywienie()) {
                            this.zwrot = pomZwrot;
                            break;
                        }
                    }
                    break;
                case "j":
                    ArrayList<Pole> sasiedzi = Plansza.daj8Sasiadow(this.polozenie);
                    for (Pole sasiad : sasiedzi) {
                        if (zjedzJesliMozliwe(sasiad)) {
                            polozenie = sasiad;
                            break;
                        }
                    }
                    break;
            }
        }
    }

    public void zwiekszWiek() {
        this.wiek++;
    }

    public void zmniejszEnergie() {
        this.energia -= Parametry.getIntParam().get("koszt_tury");
    }

    public boolean czyZywy() {
        return energia > 0;
    }

    public ArrayList<String> getProgram() {
        return program;
    }

    public int getWiek() {
        return wiek;
    }

    public int getEnergia() {
        return energia;
    }

    public Zwrot getZwrot() {
        return zwrot;
    }
}
