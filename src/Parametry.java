import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import java.io.File;

//POMYSL ZE SLOWNIKIEM
//STATYCZNA METODA GET SLOWNIK
public class Parametry {
//    private static double prPowielania;
//    private static int limPowielania;
//    private static double prDodaniaInstr;
//    private static double prUsunieciaInstr;
//    private static double prZmianyInstr;
//    private static double ulamekEnergiiRodzica;
//    private static int ileRosnieJedzenie;
//    private static int ileDajeJedzenie;
//    private static int kosztTury;
//    private static int poczEnergia;
//    private static int poczIleRobow;
//    private static int ileTur;
//    private static int coIleWypisz;
//    private static String inputProgr;
//    private static String spisInstr;
    private static ArrayList<String> poczInstr;
    private static ArrayList<String> poczProgr;
    private static final int liczbaParametrow = 15;
    private final static List<String> poprawneInstr = Arrays.asList("l", "p", "i", "w", "j");
    private final List<String> poprIntParam = Arrays.asList("ile_tur", "pocz_ile_robów", "pocz_energia",
            "ile_daje_jedzenie",            "ile_rośnie_jedzenie", "koszt_tury", "limit_powielania", "co_ile_wypisz");
    private final List<String> poprDoubleParam = Arrays.asList("pr_powielenia", "ułamek_energii_rodzica",
            "pr_usunięcia_instr", "pr_dodania_instrukcji", "pr_zmiany_instr");
    private final List<String> poprStringParam = Arrays.asList("pocz_progr", "spis_instr");
    private final List<String> poprInstr = Arrays.asList("l", "p", "i", "w", "j");
    private static HashMap<String, Integer> intParam;
    private static HashMap<String, Double> doubleParam;
    private static HashMap<String, ArrayList<String>> stringParam;

    private boolean czySlowoZawarteWPuli(ArrayList<String> slowo, List pula) {
        boolean wyn = true;

        for (String s : slowo){
            wyn &= pula.contains(s);
        }
        return wyn;
    }

/*
    private boolean czyPoprawnyProgr() {
        String spis[] = spisInstr.split("");
        List<String> instrukcje = new ArrayList<String>();
        instrukcje = Arrays.asList(spis);
        return czySlowoZawarteWPuli(inputProgr, instrukcje);
    }

    private boolean czyPoprawneInstr() {
        return czySlowoZawarteWPuli(poczInstr, poprInstr);
    }
*/

    private void sprawdzPoprawnosc() {
//        assert this.czyPoprawneInstr() : "Niepoprawne dane wejściowe w parametry.txt";
        assert czySlowoZawarteWPuli(stringParam.get("spis_instr"), poprInstr) : "Niepoprawne dane wejściowe w parametry.txt";
        assert czySlowoZawarteWPuli(stringParam.get("pocz_progr"),stringParam.get("spis_instr")) : "Niepoprawne dane wejściowe w parametry.txt";
//        assert this.czyPoprawnyProgr() : "Niepoprawne dane wejściowe w parametry.txt";
//        assert (prPowielania <= 1 && prPowielania >= 0) : "Niepoprawne dane wejściowe w parametry.txt";
//        assert limPowielania > 0 : "Niepoprawne dane wejściowe w parametry.txt";
//        assert (prDodaniaInstr <= 1 && prDodaniaInstr >= 0) : "Niepoprawne dane wejściowe w parametry.txt";
//        assert (prUsunieciaInstr <= 1 && prUsunieciaInstr >= 0) : "Niepoprawne dane wejściowe w parametry.txt";
//        assert (prZmianyInstr <= 1 && prZmianyInstr >= 0) : "Niepoprawne dane wejściowe w parametry.txt";
//        assert (ulamekEnergiiRodzica <= 1 && ulamekEnergiiRodzica >= 0) : "Niepoprawne dane wejściowe w parametry.txt";
//        assert ileRosnieJedzenie > 0 : "Niepoprawne dane wejściowe w parametry.txt";
//        assert ileDajeJedzenie > 0 : "Niepoprawne dane wejściowe w parametry.txt";
//        assert kosztTury > 0 : "Niepoprawne dane wejściowe w parametry.txt";
//        assert poczEnergia > 0 : "Niepoprawne dane wejściowe w parametry.txt";
//        assert poczIleRobow > 0 : "Niepoprawne dane wejściowe w parametry.txt";
//        assert ileTur >= 0 : "Niepoprawne dane wejściowe w parametry.txt";
//        assert coIleWypisz >= 0 : "Niepoprawne dane wejściowe w parametry.txt";
    }

    public Parametry(File input) throws FileNotFoundException {
        Scanner sc = new Scanner(input).useLocale(Locale.US);
        System.out.println(sc);
        String i;
        int licznik = 0;
        while (sc.hasNextLine()) {
            i = sc.next();
            licznik++;

            if (poprIntParam.contains(i)){
                assert intParam.containsKey(i): "Niepoprawne dane wejściowe w parametry.txt";
                Integer wart = sc.nextInt();
                assert wart >= 0 : "Niepoprawne dane wejściowe w parametry.txt";
                intParam.put(i, wart);
            }
            else if (poprDoubleParam.contains(i)){
                assert doubleParam.containsKey(i): "Niepoprawne dane wejściowe w parametry.txt";
                Double wart = sc.nextDouble();
                assert (wart >= 0 && wart <= 1) : "Niepoprawne dane wejściowe w parametry.txt";
                doubleParam.put(i, sc.nextDouble());
            }
            else if (poprStringParam.contains(i)){
                assert stringParam.containsKey(i): "Niepoprawne dane wejściowe w parametry.txt";
                stringParam.put(i, new ArrayList<String>(Arrays.asList((sc.next().split("")))));
            }
            else
                assert true : "Niepoprawne dane wejściowe w parametry.txt";

/*            switch (i) {
                case "pr_powielenia":
                    this.prPowielania = sc.nextDouble();
                    break;
                case "limit_powielania":
                    this.limPowielania = sc.nextInt();
                    break;
                case "pr_usunięcia_instr":
                    this.prUsunieciaInstr = sc.nextDouble();
                    break;
                case "pr_dodania_instr":
                    this.prDodaniaInstr = sc.nextDouble();
                    break;
                case "pr_zmiany_instr":
                    this.prZmianyInstr = sc.nextDouble();
                    break;
                case "ułamek_energii_rodzica":
                    this.ulamekEnergiiRodzica = sc.nextDouble();
                    break;
                case "koszt_tury":
                    this.kosztTury = sc.nextInt();
                    break;
                case "ile_daje_jedzenie":
                    this.ileDajeJedzenie = sc.nextInt();
                    break;
                case "pocz_energia":
                    this.poczEnergia = sc.nextInt();
                    break;
                case "ile_rośnie_jedzenie":
                    this.ileRosnieJedzenie = sc.nextInt();
                    break;
                case "pocz_ile_robów":
                    this.poczIleRobow = sc.nextInt();
                    break;
                case "co_ile_wypisz":
                    this.coIleWypisz = sc.nextInt();
                    break;
                case "ile_tur":
                    this.ileTur = sc.nextInt();
                    break;
                case "pocz_progr":
                    this.inputProgr = sc.next();
                    break;
                case "spis_instr":
                    this.spisInstr = sc.next();
                    break;
                default:
                    assert true : "Niepoprawne dane wejściowe w parametry.txt";
            }*/
        }
        sc.close();
        assert licznik == 15 : "Niepoprawne dane wejściowe w parametry.txt";
        this.poczInstr = new ArrayList<String>(stringParam.get("spis_instr"));
        this.sprawdzPoprawnosc();
//        this.poczProgr = this.stworzPoczProgr(inputProgr);
    }

//    static double getPrPowielania() {
//        return prPowielania;
//    }
//
//    static int getLimPowielania() {
//        return limPowielania;
//    }
//
//    static double getPrDodaniaInstr() {
//        return prDodaniaInstr;
//    }
//
//    static double getPrUsunieciaInstr() {
//        return prUsunieciaInstr;
//    }
//
//    static double getPrZmianyInstr() {
//        return prZmianyInstr;
//    }
//
//    static double getUlamekEnergiiRodzica() {
//        return ulamekEnergiiRodzica;
//    }
//
//    static int getIleRosnieJedzenie() {
//        return ileRosnieJedzenie;
//    }
//
//    static int getIleDajeJedzenie() {
//        return ileDajeJedzenie;
//    }
//
//    static int getKosztTury() {
//        return kosztTury;
//    }
//
//    public int getPoczEnergia() {
//        return poczEnergia;
//    }
//
//    public int getPoczIleRobow() {
//        return poczIleRobow;
//    }
//
//    public int getIleTur() {
//        return ileTur;
//    }
//
//    public int getCoIleWypisz() {
//        return coIleWypisz;
//    }
    static HashMap<String, Integer> getIntParam(){ return intParam; }
    static HashMap<String, Double> getDoubleParam(){ return doubleParam; }
    static HashMap<String, ArrayList<String>> getStringParam(){ return stringParam; }

    public ArrayList<String> getPoczProgr() {
        return poczProgr;
    }

//    public String getSpisInstr() {
//        return spisInstr;
//    }

    @Override
    public String toString() {
        return "ileTur = " + ileTur + " poczProg = " + poczProgr;
    }
}