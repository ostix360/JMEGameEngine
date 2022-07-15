package fr.ostix.game.main;

public class MainMaths {

    public static void main(String[] args) {
        float dx = (float) (15 * Math.sin(Math.toRadians(45)));
        System.out.println(dx);
        System.out.println(Math.toDegrees(Math.asin(dx / 15)));
    }
}
