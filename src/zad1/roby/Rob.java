package zad1.roby;

import java.util.ArrayList;

import zad1.wczytywanie.*;
import zad1.swiat.*;

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
    private Instrukcje instrukcje;

    public Rob(Pole polozenie) {
        this.wiek = 0;
        this.program = Parametry.getStringParam().get("pocz_progr");
        this.energia = Parametry.getIntParam().get("pocz_energia");
        this.zwrot = Zwrot.dajLosowyZwrot();
        this.polozenie = polozenie;
        this.mutacja = new Mutacja();
        this.instrukcje = new Instrukcje();
    }

    public Rob(int energia, Zwrot zwrot, ArrayList<String> program, Pole polozenie) {
        this.wiek = -1;
        this.energia = energia;
        this.zwrot = zwrot;
        this.polozenie = polozenie;
        this.program = program;
        this.mutacja = new Mutacja();
        this.instrukcje = new Instrukcje();
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
    public boolean zjedzJesliMozliwe(Pole pole) {
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
                    instrukcje.obrotWLewo(this);
                    break;
                case "p":
                    instrukcje.obrotWPrawo(this);
                    break;
                case "i":
                    instrukcje.idz(this);
                    break;
                case "w":
                    instrukcje.wachaj(this);
                    break;
                case "j":
                    instrukcje.jedz(this);
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

    public Pole getPolozenie() {
        return polozenie;
    }

    public void setZwrot(Zwrot zwrot) {
        this.zwrot = zwrot;
    }

    public void setPolozenie(Pole polozenie) {
        this.polozenie = polozenie;
    }
}
