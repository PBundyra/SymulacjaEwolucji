package swiat;

import java.util.Random;

public enum Zwrot {
    Polnoc(0, -1),
    Poludnie(0, 1),
    Wschod(1, 0),
    Zachod(-1, 0);

    private final int x, y;

    Zwrot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int dajX() {
        return this.x;
    }

    public int dajY() {
        return this.y;
    }

    public static Zwrot dajLosowyZwrot() {
        return Zwrot.values()[new Random().nextInt(Zwrot.values().length)];
    }

    private static Zwrot dajZwrotOWspol(int x, int y) {
        for (Zwrot zwrot : Zwrot.values()) {
            if (zwrot.dajX() == x && zwrot.dajY() == y) {
                return zwrot;
            }
        }
        return null;
    }

    public static Zwrot dajPrzeciwny(Zwrot zwrotWejsciowy) {
        return dajZwrotOWspol(-(zwrotWejsciowy.dajX()), -(zwrotWejsciowy.dajY()));
    }

    public static Zwrot obrocWLewo(Zwrot zwrotWejsciowy) {
        return dajZwrotOWspol(zwrotWejsciowy.dajY(), -zwrotWejsciowy.dajX());
    }

    public static Zwrot obrocWPrawo(Zwrot zwrotWejsciowy) {
        return dajZwrotOWspol(-zwrotWejsciowy.dajY(), zwrotWejsciowy.dajX());
    }
}