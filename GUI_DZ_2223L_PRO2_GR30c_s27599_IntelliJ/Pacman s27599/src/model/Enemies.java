package model;

import controler.Enemy;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Enemies {

    private static final Set<Enemy> enemySet = new HashSet<>();



    public static void add(Enemy enemy) {
        if (enemy.getClass() == enemy.getClass())
            enemySet.add(enemy);
    }

    public static Set<Enemy> getEnemySet() {
        return enemySet;
    }


}
