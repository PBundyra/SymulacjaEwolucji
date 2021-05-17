package zad1.roby;

import zad1.wczytywanie.Parametry;

import java.util.Random;

public class Mutacja {
    private final Random r = new Random();

    public void usunInstr(Rob rob) {
        if (Math.random() <= Parametry.getDoubleParam().get("pr_usunięcia_instr") && rob.getProgram().size() > 0) {
            int ind = rob.getProgram().size() - 1;
            rob.getProgram().remove(ind);
        }
    }

    public void dodajInstr(Rob rob) {
        if (Math.random() <= Parametry.getDoubleParam().get("pr_dodania_instr")) {
            int instr = r.nextInt(Parametry.getStringParam().get("spis_instr").size());
            int ind = rob.getProgram().size();
            rob.getProgram().add(ind, Parametry.getStringParam().get("spis_instr").get(instr));

        }
    }

    public void zamienInstr(Rob rob) {
        if (Math.random() <= Parametry.getDoubleParam().get("pr_zmiany_instr") && rob.getProgram().size() > 0) {
            int ind = r.nextInt(Parametry.getStringParam().get("spis_instr").size());
            String s = Parametry.getStringParam().get("spis_instr").get(ind);
            ind = r.nextInt(rob.getProgram().size());
            rob.getProgram().set(ind, s);
        }
    }
}
