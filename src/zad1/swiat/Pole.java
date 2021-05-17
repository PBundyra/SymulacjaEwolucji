package zad1.swiat;

import java.util.HashMap;

public class Pole {
    private final HashMap<String,Integer> wspolrzedne;

    public Pole(int x, int y) {
        wspolrzedne = new HashMap<>(2);
        wspolrzedne.put("x", x);
        wspolrzedne.put("y", y);
    }

    public HashMap<String, Integer> getWspolrzedne() {
        return wspolrzedne;
    }
}