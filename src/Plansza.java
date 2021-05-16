import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Plansza {
    private ArrayList<Rob> roby;
    private static Pole[][] plansza;
    private static int dlugoscPlanszy;
    private static int szerokoscPlanszy;
    private static ArrayList<Rob> noweRoby;
    public static int dod = 0;
    public static int usun = 0;

    public static void Dodaj() {
        dod++;
    }

    public static void Usun() {
        usun++;
    }

    private boolean czyPoprawnyWiersz(String wiersz) {
        char c;
        for (int i = 0; i < wiersz.length(); i++) {
            c = wiersz.charAt(i);
            if (c != ' ' && c != 'x') return false;
        }
        return true;
    }

    private boolean czyPoprawneWejscie(File input) throws FileNotFoundException {
        Scanner sc = new Scanner(input);

        if (!sc.hasNextLine()) {
            sc.close();
            return false;
        }

        String pierwszyWiersz = sc.nextLine();
        int dlugoscWiersza = pierwszyWiersz.length();

        if (!czyPoprawnyWiersz(pierwszyWiersz)) {
            sc.close();
            return false;
        }
        int liczbaWierszy = 1;

        while (sc.hasNextLine()) {
            liczbaWierszy++;
            String wiersz = sc.nextLine();
            if ((!czyPoprawnyWiersz(wiersz)) || wiersz.length() != dlugoscWiersza) {
                sc.close();
                return false;
            }
        }
        dlugoscPlanszy = liczbaWierszy;
        szerokoscPlanszy = dlugoscWiersza;
        sc.close();
        return true;
    }

    public Plansza(File input) throws FileNotFoundException {

        assert czyPoprawneWejscie(input) : "Niepoprawne dane wejściowe w plansza.txt";
        plansza = new Pole[szerokoscPlanszy][dlugoscPlanszy];
        Scanner sc = new Scanner(input);
        String wiersz;

        for (int y = 0; y < dlugoscPlanszy; y++) {
            wiersz = sc.nextLine();
            for (int x = 0; x < szerokoscPlanszy; x++) {
                if (wiersz.charAt(x) == 'x') {
                    plansza[x][y] = new PoleZywieniowe(x, y);
                } else plansza[x][y] = new Pole(x, y);
            }
        }

        sc.close();

        this.roby = new ArrayList<>();
        for (int i = 0; i < Parametry.getIntParam().get("pocz_ile_robów"); i++) {
            roby.add(new Rob(plansza[new Random().nextInt(szerokoscPlanszy)][new Random().nextInt(dlugoscPlanszy)]));
        }
        noweRoby = new ArrayList<>();
    }

    @Override
    public String toString() {
        for (int i = 0; i < dlugoscPlanszy; i++) {
            for (int j = 0; j < szerokoscPlanszy; j++) {
                if (plansza[j][i].getClass().getSimpleName().equals("PoleZywieniowe")) {
                    System.out.print("x ");
                } else System.out.print("O ");
            }
            System.out.println();
        }
        return "";
    }

    public static void dodajRoba(Rob rob) {
        noweRoby.add(0, rob);
    }

    public void wykonajTure() {

        roby.forEach(Rob::wykonajProgram);
        roby.forEach(Rob::zmniejszEnergie);
        roby.forEach(Rob::powiel);
        roby.addAll(0, noweRoby);
        noweRoby.clear();
        roby.removeIf((rob) -> !rob.czyZywy());
        roby.forEach(Rob::zwiekszWiek);

        for (Pole[] pola : plansza) {
            for (Pole pole : pola) {
                if (pole.getClass().getSimpleName().equals("PoleZywieniowe")) {
                    ((PoleZywieniowe) pole).zmniejszCzasOdnowienia();
                }
            }
        }
    }

    private void wypiszZera(String s) {
        System.out.print(", " + s + ": " + 0 + "/" + 0 + "/" + 0);
    }

    private void wypiszStatParametru(String s, int[] parametr) {
        int suma = 0, min = parametr[0], max = 0;
        for (int i : parametr) {
            min = Math.min(min, i);
            max = Math.max(max, i);
            suma += i;
        }
        double sr = (double) suma / (double) parametr.length;
        System.out.print(", " + s + ": " + min + "/" + String.format("%.2f", sr) + "/" + max);
    }

    public void wypiszRoby() {
        for (Rob rob : roby) {
            System.out.println("energia: " + rob.getEnergia() + " program[" + rob.getProgram().size() + "]:" +
                    " wiek:" + rob.getWiek() + " zwrot: " + rob.getZwrot());
        }
    }

    private void wypiszStatRobow() {

        int[] energie = new int[roby.size()];
        int[] wiek = new int[roby.size()];
        int[] dlPrg = new int[roby.size()];

        if (roby.size() == 0) {
            this.wypiszZera("prg");
            this.wypiszZera("energ");
            this.wypiszZera("wiek");
            return;
        }

        for (int i = 0; i < roby.size(); i++) {
            energie[i] = roby.get(i).getEnergia();
            wiek[i] = roby.get(i).getWiek();
            dlPrg[i] = roby.get(i).getProgram().size();
        }
        wypiszStatParametru("energ", energie);
        wypiszStatParametru("wiek", wiek);
        wypiszStatParametru("prg", dlPrg);
    }

    public void wypiszStan() {
        System.out.print("rob: " + roby.size());

        int zyw = 0;
        for (Pole[] pola : plansza) {
            for (Pole pole : pola) {
                if (pole.getClass().getSimpleName().equals("PoleZywieniowe")
                        && ((PoleZywieniowe) pole).getCzyJestPozywienie()) zyw++;
            }
        }

        System.out.print(", żyw: " + zyw);
        this.wypiszStatRobow();
        System.out.println();
    }

    public static ArrayList<Pole> daj8Sasiadow(Pole pole) {
        ArrayList<Pole> sasiedzi = new ArrayList<>();
        int x = pole.getWspolrzedne().get("x"), y = pole.getWspolrzedne().get("y");
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0)) {
                    sasiedzi.add(plansza[Math.floorMod(x + i, szerokoscPlanszy)][Math.floorMod(y + j, szerokoscPlanszy)]);
                }
            }
        }
        return sasiedzi;
    }

    public static Pole[][] getPlansza() {
        return plansza;
    }

    public static int dajDlPlanszy() {
        return dlugoscPlanszy;
    }

    public static int dajSzerPlanszy() {
        return szerokoscPlanszy;
    }
}
