public class PoleZywieniowe extends Pole{
    private int ileDoNowegoPozywienia;
    private boolean czyJestPozywienie;
    private Parametry parametry;

    public PoleZywieniowe(Parametry parametry){
        this.ileDoNowegoPozywienia = 0;
        this.czyJestPozywienie = true;
        this.parametry = parametry;
    }

    public boolean czyJestPozywienie() {
        return czyJestPozywienie;
    }

    public void zjedzMnie(){
        this.czyJestPozywienie = false;
        this.ileDoNowegoPozywienia = parametry.getIleRosnieJedzenie();
    }

    public void zmniejszCzasOdnowienia(){
        if (ileDoNowegoPozywienia > 0) {ileDoNowegoPozywienia--; return;}
        czyJestPozywienie = true;
    }
}
