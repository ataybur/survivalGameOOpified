package com.ataybur.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiConsumer;

import com.ataybur.constants.Constants;
import com.ataybur.constants.MessageConstants;
import com.ataybur.pojo.Context;
import com.ataybur.pojo.Enemy;
import com.ataybur.pojo.Field;
import com.ataybur.pojo.Hero;
import com.ataybur.pojo.base.Character;

public class ContextHelper extends Context {
    private Context context;

    public ContextHelper() {
	this.context = super.getInstance();
    }

    public Optional<Context> toInstance() {
	return Optional.of(context);
    }

    public ContextHelper applyLineInfo(LineInfo lineInfo) {
	return lineInfo.putParsedLineIntoContext(this);
    }

    public ContextHelper setRange(String[] info) throws InstantiationException, IllegalAccessException {
	Field field = new PojoGetter<Field>(context.getField(), Field.class).get();
	String rangeString = info[0];
	Integer range = new IntegerConverter(rangeString).convert();
	field.setRange(range);
	context.setField(field);
	return this;
    }

    public <T extends com.ataybur.pojo.base.Character> ContextHelper setCharacterHP(String[] characterInfo) throws InstantiationException, IllegalAccessException {
	return setCharacterPoint(characterInfo, Character::setHp);
    }

    public <T extends com.ataybur.pojo.base.Character> ContextHelper setCharacterAttackPoint(String[] characterInfo) throws InstantiationException, IllegalAccessException {
	return setCharacterPoint(characterInfo, Character::setAttackPoint);
    }

    private <T extends com.ataybur.pojo.base.Character> ContextHelper setCharacterPoint(String[] characterInfo, BiConsumer<T, Integer> setter)
	    throws InstantiationException, IllegalAccessException {

	List<Enemy> enemyList = new PojoGetter<List>(context.getEnemyList(), ArrayList.class).get();
	String characterType = characterInfo[0];
	Integer point = new IntegerConverter(characterInfo[1]).convert();
	if (new StringChecker(characterType).isNotEmpty()) {
	    if (characterType.equalsIgnoreCase(Constants.HERO)) {
		Hero hero = new PojoGetter<Hero>(context.getHero(), Hero.class).get();
		setter.accept((T) hero, point);
		context.setHero(hero);
	    } else {
		Optional<Enemy> optEnemy = enemyList //
			.stream() //
			.filter(enemy -> characterType.equalsIgnoreCase(enemy.getSpecies())) //
			.findFirst();
		boolean optEnemyIsPresent = optEnemy.isPresent();
		Enemy enemy = optEnemy.orElse(new Enemy());
		enemy.setSpecies(characterType);
		setter.accept((T) enemy, point);
		if (!optEnemyIsPresent) {
		    enemyList.add(enemy);
		}
	    }
	}
	context.setEnemyList(enemyList);
	return this;
    }

    public ContextHelper setEnemyPosition(String[] characterInfo) throws InstantiationException, IllegalAccessException {

	List<Enemy> enemyList = new PojoGetter<List>(context.getEnemyList(), ArrayList.class).get();
	Field field = new PojoGetter<Field>(context.getField(), Field.class).get();
	SortedMap<Integer, Enemy> enemyMap = new PojoGetter<SortedMap>(field.getEnemyMap(), TreeMap.class).get();
	String characterType = characterInfo[0];
	Integer position = new IntegerConverter(characterInfo[1]).convert();
	if (new StringChecker(characterType).isNotEmpty()) {
	    Enemy enemy = enemyList //
		    .stream() //
		    .filter((e) -> characterType.equalsIgnoreCase(e.getSpecies())) //
		    .findFirst() //
		    .orElseThrow(() -> {
			String formattedMessage = String.format(MessageConstants.MESSAGE_6, characterType);
			return new RuntimeException(formattedMessage);
		    });
	    enemyMap.put(position, enemy);
	}
	field.setEnemyMap(enemyMap);
	return this;
    }

    public ContextHelper setEnemy(String[] info) throws InstantiationException, IllegalAccessException {
	List<Enemy> enemyList = new PojoGetter<List>(context.getEnemyList(), ArrayList.class).get();
	String characterType = info[0];
	if (new StringChecker(characterType).isNotEmpty()) {
	    Optional<Enemy> optEnemy = enemyList //
		    .stream() //
		    .filter((enemy) -> characterType.equalsIgnoreCase(enemy.getSpecies())) //
		    .findFirst();
	    boolean optEnemyIsPresent = optEnemy.isPresent();
	    Enemy enemy = optEnemy.orElse(new Enemy());
	    enemy.setSpecies(characterType);
	    if (!optEnemyIsPresent) {
		enemyList.add(enemy);
	    }
	}
	context.setEnemyList(enemyList);
	return this;
    }

}
