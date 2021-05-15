import java.util.ArrayList;
import java.util.Random;

//STRING BO MOZE BYC PRZYDATNE DO WIELO CHARAKETROWYCH INSTRUKCJI
public class Rob {
    private Zwrot zwrot;
    private Pole polozenie;
    private ArrayList<String> program;
    private int wiek;
    private int energia;

    public Rob() {
        this.wiek = 0;
        this.program = Parametry.getStringParam().get("pocz_progr");
        this.energia = Parametry.getIntParam().get("pocz_energia");
        this.zwrot = Zwrot.dajZwrotOInd(new Random().nextInt(5));
    }

    public Rob(int energia, Zwrot zwrot, ArrayList<String> program) {
        this.wiek = 0;
        this.energia = energia;
        this.zwrot = zwrot;
        this.program = program;
    }

    private void zmutuj() {
        Random r = new Random();
        if (Math.random() <= Parametry.getDoubleParam().get("pr_usunięcia_instr")) {
            if (this.program.size() > 0) this.program.remove(this.program.size() - 1);
        }
        if (Math.random() <= Parametry.getDoubleParam().get("pr_dodania_instr")) {
            int ind = r.nextInt(Parametry.getStringParam().get("spis_instr").size());
            this.program.add(0, Parametry.getStringParam().get("spis_instr").get(ind));
        }
        if (Math.random() <= Parametry.getDoubleParam().get("pr_zmiany_instr")) {
            if (this.program.size() > 0) {
                int ind = r.nextInt(Parametry.getStringParam().get("spis_instr").size());
                String s = Parametry.getStringParam().get("spis_instr").get(ind);
                ind = r.nextInt(this.program.size());
                this.program.set(ind, s);
            }
        }
    }

    public void powiel(Plansza plansza) {
        if (this.energia >= Parametry.getIntParam().get("limit_powielania")) {
            if (Math.random() <= Parametry.getDoubleParam().get("pr_powielenia")) {
                int energiaPotomka = (int) ((Parametry.getDoubleParam().get("ułamek_energii_rodzica") * (double) energia));
                this.energia -= energiaPotomka;
                Zwrot zwrotPotomka = Zwrot.dajPrzeciwny(this.zwrot);
                ArrayList<String> kopiaProgramu = new ArrayList<String>(this.program);
                Rob potomek = new Rob(energiaPotomka, zwrotPotomka, kopiaProgramu);
                potomek.zmutuj();
                plansza.dodajRoba(potomek);
            }
        }
    }

    public void zwiekszWiek() {
        this.wiek++;
    }

    public void zmniejszEnergie() {
        this.energia -= Parametry.getIntParam().get("kosz_tury");
    }

    public void wykonajProgram() {
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
}
