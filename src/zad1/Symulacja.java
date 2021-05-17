package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import zad1.ewolucja.*;

public class Symulacja {
    public static void main(String[] args) throws FileNotFoundException {

        Ewolucja ewolucja = new Ewolucja(new File(args[0]), new File(args[1]));
        ewolucja.symulujEwolucje();
    }
}