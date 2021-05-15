import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Plansza {
    private ArrayList<Rob> roby;
    private static Pole[][] plansza;
    private static int dlugoscPlanszy;
    private static int szerokoscPlanszy;
    private static ArrayList<Rob> noweRoby;

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
        this.dlugoscPlanszy = liczbaWierszy;
        this.szerokoscPlanszy = dlugoscWiersza;
        sc.close();
        return true;
    }

    public Plansza(File input) throws FileNotFoundException {

        assert czyPoprawneWejscie(input) : "Niepoprawne dane wejściowe w plansza.txt";
        this.plansza = new Pole[this.szerokoscPlanszy][this.dlugoscPlanszy];
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

        this.roby = new ArrayList<Rob>();
        for (int i = 0; i < Parametry.getIntParam().get("pocz_ile_robów"); i++) {
            roby.add(new Rob(plansza[new Random().nextInt(szerokoscPlanszy)][new Random().nextInt(dlugoscPlanszy)]));
        }
        this.noweRoby = new ArrayList<Rob>();
    }

    @Override
    public String toString() {
        for (int i = 0; i < dlugoscPlanszy; i++) {
            for (int j = 0; j < szerokoscPlanszy; j++) {
                if (plansza[j][i].getClass().getSimpleName() == "PoleZywieniowe") {
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

        roby.forEach((rob) -> rob.wykonajProgram());
        roby.forEach((rob) -> rob.zmniejszEnergie());
        roby.forEach((rob) -> rob.powiel());
        roby.addAll(0, noweRoby);
        noweRoby.clear();
        roby.removeAll(roby.stream().filter((rob) -> !rob.czyZywy()).collect(Collectors.toList()));
        roby.forEach((rob) -> rob.zwiekszWiek());

        for (Pole[] pola : plansza) {
            for (Pole pole : pola) {
                if (pole.getClass().getSimpleName() == "PoleZywieniowe") {
                    ((PoleZywieniowe) pole).zmniejszCzasOdnowienia();
                }
            }
        }
    }

    private void wypiszStatRobow() {

        int energie[] = new int[roby.size()], wiek[] = new int[roby.size()], dlPrg[] = new int[roby.size()];

        if (roby.size() == 0) {
            System.out.print(", prg: " + 0 + "/" + 0 + "/" + 0);
            System.out.print(", energ: " + 0 + "/" + 0 + "/" + 0);
            System.out.print(", wiek: " + 0 + "/" + 0 + "/" + 0);
            return;
        }

        for (int i = 0; i < roby.size(); i++) {
            energie[i] = roby.get(i).getEnergia();
            wiek[i] = roby.get(i).getWiek();
            dlPrg[i] = roby.get(i).getProgram().size();
        }

        int sumaEnerg = 0, minEnerg = energie[0], maxEnerg = 0;
        int sumaWiek = 0, minWiek = wiek[0], maxWiek = 0;
        int sumaDlPrg = 0, minDlPrg = dlPrg[0], maxDlPrg = 0;

        for (int i = 0; i < roby.size(); i++) {
            sumaEnerg += energie[i];
            sumaWiek += wiek[i];
            sumaDlPrg += dlPrg[i];

            if (energie[i] < minEnerg) minEnerg = energie[i];
            if (energie[i] > maxEnerg) maxEnerg = energie[i];

            if (wiek[i] < minWiek) minWiek = wiek[i];
            if (wiek[i] > maxWiek) maxWiek = wiek[i];

            if (dlPrg[i] < minDlPrg) minDlPrg = dlPrg[i];
            if (dlPrg[i] > maxDlPrg) maxDlPrg = dlPrg[i];
        }

        double srEnerg = (double) sumaEnerg / (double) roby.size();
        double srWiek = (double) sumaWiek / (double) roby.size();
        double srDlPrg = (double) sumaDlPrg / (double) roby.size();

        System.out.print(", prg: " + minDlPrg + "/" + String.format("%.2f", srDlPrg) + "/" + maxDlPrg);
        System.out.print(", energ: " + minEnerg + "/" + String.format("%.2f", srEnerg) + "/" + maxEnerg);
        System.out.print(", wiek: " + minWiek + "/" + String.format("%.2f", srWiek) + "/" + maxWiek);
    }

    public void wypiszStan() {
        System.out.print("rob: " + roby.size());

        int zyw = 0;
        for (Pole[] pola : plansza) {
            for (Pole pole : pola) {
                if (pole.getClass().getSimpleName() == "PoleZywieniowe") {
                    if (((PoleZywieniowe) pole).getCzyJestPozywienie()) zyw++;
                }
            }
        }

        System.out.print(", żyw: " + zyw);
        this.wypiszStatRobow();
        System.out.println();
    }

    public static ArrayList<Pole> daj8Sasiadow(Pole pole) {
        ArrayList<Pole> sasiedzi = new ArrayList<Pole>();
        int x = pole.getWspolrzedne().get("x"), y = pole.getWspolrzedne().get("y");
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 && j != 0) {
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
