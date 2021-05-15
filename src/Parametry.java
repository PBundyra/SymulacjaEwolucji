import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Parametry {
    private static final int liczbaParametrow = 15;
    private final List<String> poprIntParam = Arrays.asList("ile_tur", "pocz_ile_robów", "pocz_energia",
            "ile_daje_jedzenie", "ile_rośnie_jedzenie", "koszt_tury", "limit_powielania", "co_ile_wypisz");
    private final List<String> poprDoubleParam = Arrays.asList("pr_powielenia", "ułamek_energii_rodzica",
            "pr_usunięcia_instr", "pr_dodania_instr", "pr_zmiany_instr");
    private final List<String> poprStringParam = Arrays.asList("pocz_progr", "spis_instr");
    private final List<String> poprInstr = Arrays.asList("l", "p", "i", "w", "j");
    private static HashMap<String, Integer> intParam;
    private static HashMap<String, Double> doubleParam;
    private static HashMap<String, ArrayList<String>> stringParam;

    private boolean czySlowoZawarteWPuli(ArrayList<String> slowo, List pula) {
        boolean wyn = true;

        for (String s : slowo) {
            wyn &= pula.contains(s);
        }
        return wyn;
    }

    private void sprawdzPoprawnosc() {
        assert czySlowoZawarteWPuli(stringParam.get("spis_instr"),
                poprInstr) : "Niepoprawne dane wejściowe w parametry.txt";
        assert czySlowoZawarteWPuli(stringParam.get("pocz_progr"),
                stringParam.get("spis_instr")) : "Niepoprawne dane wejściowe w parametry.txt";
    }

    public Parametry(File input) throws FileNotFoundException {
        Scanner sc = new Scanner(input).useLocale(Locale.US);
        String i;
        int licznik = 0;

        while (sc.hasNextLine()) {
            i = sc.next();
            licznik++;

            if (poprIntParam.contains(i)) {
                assert intParam.containsKey(i) : "Niepoprawne dane wejściowe w parametry.txt";
                Integer wart = sc.nextInt();
                assert wart >= 0 : "Niepoprawne dane wejściowe w parametry.txt";
                intParam.put(i, wart);
            } else if (poprDoubleParam.contains(i)) {
                assert doubleParam.containsKey(i) : "Niepoprawne dane wejściowe w parametry.txt";
                Double wart = sc.nextDouble();
                assert (wart >= 0 && wart <= 1) : "Niepoprawne dane wejściowe w parametry.txt";
                doubleParam.put(i, sc.nextDouble());
            } else if (poprStringParam.contains(i)) {
                assert stringParam.containsKey(i) : "Niepoprawne dane wejściowe w parametry.txt";
                stringParam.put(i, new ArrayList<String>(Arrays.asList((sc.next().split("")))));
            } else
                assert true : "Niepoprawne dane wejściowe w parametry.txt";

        }

        sc.close();
        assert licznik == liczbaParametrow : "Niepoprawne dane wejściowe w parametry.txt";
        this.sprawdzPoprawnosc();
    }

    static HashMap<String, Integer> getIntParam() {
        return intParam;
    }

    static HashMap<String, Double> getDoubleParam() {
        return doubleParam;
    }

    static HashMap<String, ArrayList<String>> getStringParam() {
        return stringParam;
    }
}