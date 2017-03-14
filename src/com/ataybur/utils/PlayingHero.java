package com.ataybur.utils;

import java.util.Optional;

import com.ataybur.constants.MessageConstants;
import com.ataybur.pojo.Context;
import com.ataybur.pojo.Enemy;
import com.ataybur.pojo.Hero;

public class PlayingHero extends PlayingCharacter<Hero> {
    public PlayingHero(Hero instance) {
	super(instance);
    }

    public boolean isAlive(PlayingCharacter<Enemy> currentEnemy) throws InstantiationException, IllegalAccessException {
	Double heroHpDouble = super.getCharacterRemainingHp(currentEnemy);
	boolean result;
	if (heroHpDouble > 0d) {
	    getInstance().setHp(heroHpDouble.intValue());
	    result = true;
	    new ConsolePrinter(Optional.ofNullable(Context.getInstance())) //
		    .printLog(MessageConstants.MESSAGE_3, currentEnemy.getInstance().getSpecies(), heroHpDouble.intValue());
	} else {
	    Double enemyHpDouble = currentEnemy.getCharacterRemainingHp(getInstance());
	    new ConsolePrinter(Optional.ofNullable(Context.getInstance())) //
		    .printLog(MessageConstants.MESSAGE_4, currentEnemy.getInstance().getSpecies(), enemyHpDouble.intValue());
	    result = false;
	}
	return result;
    }
}
