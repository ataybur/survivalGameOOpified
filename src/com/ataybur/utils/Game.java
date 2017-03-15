package com.ataybur.utils;

import java.util.Optional;
import java.util.SortedMap;

import com.ataybur.constants.MessageConstants;
import com.ataybur.pojo.Context;
import com.ataybur.pojo.Enemy;
import com.ataybur.pojo.Field;
import com.ataybur.pojo.Hero;

public class Game {
    private Optional<Context> context;

    public Game(Context context) {
	super();
	this.context = Optional.of(context);
    }

    public Optional<Context> startGame() throws InstantiationException, IllegalAccessException {
	if (context.isPresent()) {
	    Hero hero = context.get().getHero();
	    PlayingHero playingHero = new PlayingHero(hero);
	    Field field = context.get().getField();
	    SortedMap<Integer, Enemy> enemyMap = field.getEnemyMap();
	    new ConsolePrinter(context) //
		    .printLog(MessageConstants.MESSAGE_1, hero.getHp());
	    for (Integer i = 1; i <= field.getRange(); i++) {
		Enemy currentEnemy = enemyMap.get(i);
		PlayingCharacter<Enemy> playingEnemy = new PlayingCharacter<Enemy>(currentEnemy);
		if (currentEnemy != null) {
		    if (!playingHero.isAlive(playingEnemy)) {
			new ConsolePrinter(context) //
				.printLog(MessageConstants.MESSAGE_2, i);
			return context;
		    }
		}
	    }
	    new ConsolePrinter(context) //
		    .printLog(MessageConstants.MESSAGE_5);
	}
	return context;
    }
}
