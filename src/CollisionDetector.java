import java.util.LinkedList;

public class CollisionDetector {
    public static boolean isSquareInFront(Kwadrat klient, LinkedList<Kwadrat> listaKwadratow) {
        int nextX = klient.getPosition().getWs_x();
        int nextY = klient.getPosition().getWs_y() - klient.getSize();

        for (Kwadrat kwadrat : listaKwadratow) {
            if (kwadrat.getPosition().getWs_x() == nextX && kwadrat.getPosition().getWs_y() == nextY) {
                return true;
            }
        }

        int nextXX = klient.getPosition().getWs_x() - klient.getSize();
        int nextYY = klient.getPosition().getWs_y();

        for (Kwadrat kwadrat : listaKwadratow) {
            if (kwadrat.getPosition().getWs_x() == nextXX && kwadrat.getPosition().getWs_y() == nextYY) {
                return true;
            }
        }




        return false;
    }

    public static boolean collision_prawo(Kwadrat klient, LinkedList<Kwadrat> listaKwadratow){
        int nextXXX = klient.getPosition().getWs_x() + klient.getSize();
        int nextYYY = klient.getPosition().getWs_y();

        for (Kwadrat kwadrat : listaKwadratow) {
            if (kwadrat.getPosition().getWs_x() == nextXXX && kwadrat.getPosition().getWs_y() == nextYYY) {
                return true;
            }
        }
        return false;
    }


    public static boolean kolejka_zapchana(Kwadrat klient, LinkedList<Kwadrat> listaKwadratow){
        for (Kwadrat kwadrat : listaKwadratow) {
            if (kwadrat.getPosition().getWs_x() == 560 && kwadrat.getPosition().getWs_y() == 400) {
                return true;
            }
        }
        return false;
    }

    public static boolean ktos_bedzie_wychodzic(Kwadrat klient, LinkedList<Kwadrat> listaKwadratow){
        for (Kwadrat kwadrat : listaKwadratow) {
            if (kwadrat.getPosition().getWs_y() == 550) {
                return true;
            }
        }
        return false;
    }


}
