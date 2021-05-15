public enum Zwrot {
    Polnoc(1),
    Wschod(2),
    Poludnie(3),
    Zachod(4);

    private final int wart;

    private Zwrot(int wart) {
        this.wart = wart;
    }

    private int getWart() {
        return this.wart;
    }

    private static Zwrot dajZwrotOInd(int ind) {
        Zwrot wyn = null;
        for (Zwrot zwrot : Zwrot.values()) {
            if (zwrot.getWart() == ind) {
                wyn = zwrot;
            }
        }
        return wyn;
    }

    public static Zwrot dajPrzeciwny(Zwrot zwrotWejsciowy) {
        int ind = (zwrotWejsciowy.wart + 2) % 4;
        return dajZwrotOInd(ind);
    }

    public static Zwrot obrocWLewo(Zwrot zwrotWejsciowy) {
        int ind = (zwrotWejsciowy.wart - 1) % 4;
        return dajZwrotOInd(ind);
    }

    public static Zwrot obrocWPrawo(Zwrot zwrotWejsciowy) {
        int ind = (zwrotWejsciowy.wart + 1) % 4;
        return dajZwrotOInd(ind);
    }
}
