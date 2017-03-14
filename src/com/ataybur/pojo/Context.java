package com.ataybur.pojo;

import java.util.List;

public class Context {
    private static Context context = new Context();

    private String outputFileName;
    private Hero hero;
    private Field field;
    private List<Enemy> enemyList;
    private List<String> console;

    protected Context() {
    }

    public static Context getInstance() {
	return context;
    }

    public Hero getHero() {
	return hero;
    }

    public void setHero(Hero hero) {
	this.hero = hero;
    }

    public Field getField() {
	return field;
    }

    public void setField(Field field) {
	this.field = field;
    }

    public List<Enemy> getEnemyList() {
	return enemyList;
    }

    public void setEnemyList(List<Enemy> enemyList) {
	this.enemyList = enemyList;
    }

    public List<String> getConsole() {
	return console;
    }

    public void setConsole(List<String> console) {
	this.console = console;
    }

    public String getOutputFileName() {
	return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
	this.outputFileName = outputFileName;
    }

    @Override
    public String toString() {
	return "Context [hero=" + hero + ", field=" + field + ", enemyList=" + enemyList + "]";
    }

}
