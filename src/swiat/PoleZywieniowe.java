package swiat;

import wczytywanie.*;

public class PoleZywieniowe extends Pole{
    private int ileDoNowegoPozywienia;
    private boolean czyJestPozywienie;

    public PoleZywieniowe(int x, int y){
        super(x, y);
        this.ileDoNowegoPozywienia = 0;
        this.czyJestPozywienie = true;
    }

    public boolean getCzyJestPozywienie() {
        return czyJestPozywienie;
    }

    public void zjedzPozywienie(){
        this.czyJestPozywienie = false;
        this.ileDoNowegoPozywienia = Parametry.getIntParam().get("ile_rośnie_jedzenie");
    }

    public void zmniejszCzasOdnowienia(){
        if (ileDoNowegoPozywienia > 0) {ileDoNowegoPozywienia--; return;}
        czyJestPozywienie = true;
    }
}
