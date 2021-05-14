import java.util.ArrayList;
import java.util.Random;

public class Rob {
    private Zwrot zwrot;
    private Pole polozenie;
    private ArrayList<String> program;
    private int wiek;
    private int energia;
    private Parametry parametry;

    public Rob(Parametry parametry) {
        this.parametry = parametry;
        this.wiek = 0;
        this.program = new ArrayList<String>(parametry.getPoczProgr().size());
        program = parametry.getPoczProgr();
        this.energia = parametry.getPoczEnergia();
        Random r = new Random();
        switch (r.nextInt(4)) {
            case 0:
                this.zwrot = Zwrot.Polnoc;
                break;
            case 1:
                this.zwrot = Zwrot.Poludnie;
                break;
            case 2:
                this.zwrot = Zwrot.Wschod;
                break;
            case 3:
                this.zwrot = Zwrot.Zachod;
                break;
        }
    }

    private void zmutuj() {
        Random r = new Random();
        if (Math.random() <= parametry.getPrUsunieciaInstr()) {
            if (this.program.size() > 0) this.program.remove(this.program.size() - 1);
        }
        if (Math.random() <= parametry.getPrDodaniaInstr()) {
            int ind = r.nextInt(parametry.getSpisInstr().length());
            String s = String.valueOf(parametry.getSpisInstr().charAt(ind));
            this.program.add(0, s);
        }
        if (Math.random() <= parametry.getPrZmianyInstr()) {
            if (this.program.size() > 0) {
                int ind = r.nextInt(parametry.getSpisInstr().length());
                String s = String.valueOf(parametry.getSpisInstr().charAt(ind));
                ind = r.nextInt(this.program.size());
                this.program.set(ind, s);
            }
        }
    }

    public Rob(int energia, Zwrot zwrot, Parametry parametry, ArrayList<String> program) {
        this.wiek = 0;
        this.energia = energia;
        this.zwrot = zwrot;
        this.parametry = parametry;
        this.program = program;
    }

    public void powiel(Plansza plansza) {
        if (this.energia >= parametry.getLimPowielania()) {
            if (Math.random() <= parametry.getPrPowielania()) {
                int energiaPotomka = (int) (parametry.getUlamekEnergiiRodzica() * (double) energia);
                this.energia -= energiaPotomka;
                Zwrot zwrotPotomka;
                switch (this.zwrot) {
                    case Poludnie:
                        zwrotPotomka = Zwrot.Polnoc;
                        break;
                    case Polnoc:
                        zwrotPotomka = Zwrot.Poludnie;
                        break;
                    case Wschod:
                        zwrotPotomka = Zwrot.Zachod;
                        break;
                    default:
                        zwrotPotomka = Zwrot.Wschod;
                        break;
                }
                ArrayList<String> kopiaProgramu = new ArrayList<String>(this.program);
                Rob potomek = new Rob(energiaPotomka, zwrotPotomka, parametry, kopiaProgramu);
                potomek.zmutuj();
                plansza.dodajRoba(potomek);
            }
        }
    }

    public void zwiekszWiek() {
        this.wiek++;
    }

    public void zmniejszEnergie() {
        this.energia -= parametry.getKosztTury();
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
