import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Pole {
    private final HashMap<String,Integer> wspolrzedne;
    //NA WYPADEK ROZSZERZEN

    public Pole(int x, int y) {
        wspolrzedne = new HashMap<String,Integer>(2);
        wspolrzedne.put("x", x);
        wspolrzedne.put("y", y);
    }

    public HashMap<String, Integer> getWspolrzedne() {
        return wspolrzedne;
    }
}
