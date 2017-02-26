package com.ataybur.utils;

import java.util.SortedMap;

import com.ataybur.constants.MessageConstants;
import com.ataybur.pojo.Context;
import com.ataybur.pojo.Enemy;
import com.ataybur.pojo.Field;
import com.ataybur.pojo.Hero;

public class Game {
	private Context context;
	
	public Game(Context context) {
		super();
		this.context = context;
	}
	
	public void startGame() throws InstantiationException, IllegalAccessException{
		Hero hero = context.getHero();
		PlayingHero playingHero = new PlayingHero(hero);
		Field field = context.getField();
		SortedMap<Integer, Enemy> enemyMap = field.getEnemyMap();
		ContextUtils.addToConsole(MessageConstants.MESSAGE_1, hero.getHp());
		for (Integer i = 1; i <= field.getRange(); i++) {
			Enemy currentEnemy = enemyMap.get(i);
			PlayingCharacter<Enemy> playingEnemy = new PlayingCharacter<Enemy>(currentEnemy);
			if (currentEnemy != null) {
				if (!playingHero.isAlive(playingEnemy)) {
					ContextUtils.addToConsole(MessageConstants.MESSAGE_2, i);
					return;
				}
			}
		}
		ContextUtils.addToConsole(MessageConstants.MESSAGE_5);
	}
}
