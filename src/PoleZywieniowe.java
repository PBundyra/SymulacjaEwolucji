public class PoleZywieniowe extends Pole{
    private int ileDoNowegoPozywienia;
    private boolean czyJestPozywienie;

    public PoleZywieniowe(){
        this.ileDoNowegoPozywienia = 0;
        this.czyJestPozywienie = true;
    }

    public boolean czyJestPozywienie() {
        return czyJestPozywienie;
    }

    public void zjedzMnie(){
        this.czyJestPozywienie = false;
        this.ileDoNowegoPozywienia = Parametry.getIntParam().get("ile_rośnie_jedzenie");
    }

    public void zmniejszCzasOdnowienia(){
        if (ileDoNowegoPozywienia > 0) {ileDoNowegoPozywienia--; return;}
        czyJestPozywienie = true;
    }
}
