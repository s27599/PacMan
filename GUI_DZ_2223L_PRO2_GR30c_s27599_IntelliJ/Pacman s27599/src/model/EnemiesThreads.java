package model;

import controler.Enemy;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class EnemiesThreads {

    private static final Set<Thread> enemySet = new HashSet<>();



    public static void  add(Thread enemy){
        if(enemy.getClass() == Thread.class)
            enemySet.add(enemy);
    }

    public static Set<Thread> getEnemySet() {
        return enemySet;
    }



    public static void start() {
        enemySet.forEach((Thread thread)->{
            if(!thread.isAlive()){
                thread.start();
            }
        });


    }

    public static void interrupt(){
        enemySet.forEach(Thread::interrupt);
        enemySet.clear();

    }


}
