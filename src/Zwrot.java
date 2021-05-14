public enum Zwrot {
    Polnoc (1),
    Poludnie (2),
    Wschod (3),
    Zachod (4);

    private final int value;
    private Zwrot(int value){
        this.value = value;
    }

    public Zwrot dajPrzeciwny(){
        return Poludnie;
    }
}
