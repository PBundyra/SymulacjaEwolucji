package zad1.roby;

import zad1.swiat.*;

import java.util.ArrayList;

public class Instrukcje {

    public void obrotWLewo(Rob rob){
        rob.setZwrot(Zwrot.obrocWLewo(rob.getZwrot()));
    }

    public void obrotWPrawo(Rob rob){
        rob.setZwrot(Zwrot.obrocWPrawo(rob.getZwrot()));
    }

    public void wachaj(Rob rob){
        for (int i = 0; i < 4; i++) {
            Zwrot pomZwrot = Zwrot.obrocWLewo(rob.getZwrot());
            int pomX = Math.floorMod(rob.getPolozenie().getWspolrzedne().get("x") + pomZwrot.dajX(), Plansza.dajSzerPlanszy());
            int pomY = Math.floorMod(rob.getPolozenie().getWspolrzedne().get("y") + pomZwrot.dajY(), Plansza.dajDlPlanszy());
            Pole pomPole = Plansza.getPlansza()[pomX][pomY];
            if (pomPole.getClass().getSimpleName().equals("PoleZywieniowe")
                    && ((PoleZywieniowe) pomPole).getCzyJestPozywienie()) {
                rob.setZwrot(pomZwrot);
                break;
            }
    }}

    public void idz(Rob rob){
        rob.setPolozenie(Plansza.getPlansza()[Math.floorMod(rob.getPolozenie().getWspolrzedne().get("x")
                + rob.getZwrot().dajX() - 1, Plansza.dajSzerPlanszy())]
                [Math.floorMod(rob.getPolozenie().getWspolrzedne().get("y")
                + rob.getZwrot().dajY() - 1, Plansza.dajDlPlanszy())]);
        rob.zjedzJesliMozliwe(rob.getPolozenie());
    }

    public void jedz(Rob rob){
            ArrayList<Pole> sasiedzi = Plansza.daj8Sasiadow(rob.getPolozenie());
            for (Pole sasiad : sasiedzi) {
                if (rob.zjedzJesliMozliwe(sasiad)) {
                    rob.setPolozenie(sasiad);
                    break;
                }
            }
    }
}
