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
	
	public Context toInstance() {
		return context;
	}
	
	public ContextHelper setRange(String[] info) throws InstantiationException, IllegalAccessException {
		Field field = Utils.getter(context.getField(), Field.class);
		String rangeString = info[0];
		Integer range = Utils.convertIntToString(rangeString);
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
	
	private <T extends com.ataybur.pojo.base.Character> ContextHelper setCharacterPoint(String[] characterInfo,
			BiConsumer<T, Integer> setter) throws InstantiationException, IllegalAccessException {
		List<Enemy> enemyList = Utils.getter(context.getEnemyList(), ArrayList.class);
		String characterType = characterInfo[0];
		Integer point = Utils.convertIntToString(characterInfo[1]);
		if (StringUtils.isNotEmpty(characterType)) {
			if (characterType.equalsIgnoreCase(Constants.HERO)) {
				Hero hero = Utils.getter(context.getHero(), Hero.class);
				setter.accept((T) hero, point);
				context.setHero(hero);
			} else {
				Optional<Enemy> optEnemy = enemyList //
						.stream() //
						.filter(enemy -> characterType.equalsIgnoreCase(enemy.getSpecies())) //
						.findFirst();
				boolean optEnemyIsPresent = optEnemy.isPresent();
				if (!optEnemyIsPresent) {
					optEnemy = Optional.of(new Enemy());
				}
				Enemy enemy = optEnemy.get();
				enemy.setSpecies(characterType);
				setter.accept((T) enemy, point);
				if (!optEnemyIsPresent) {
					enemyList.add(optEnemy.get());
				}
			}
		}
		context.setEnemyList(enemyList);
		return this;
	}

	public ContextHelper setEnemyPosition(String[] characterInfo) throws InstantiationException, IllegalAccessException {
		List<Enemy> enemyList = Utils.getter(context.getEnemyList(), ArrayList.class);
		Field field = Utils.getter(context.getField(), Field.class);
		SortedMap<Integer, Enemy> enemyMap = Utils.getter(field.getEnemyMap(), TreeMap.class);
		String characterType = characterInfo[0];
		Integer position = Utils.convertIntToString(characterInfo[1]);
		if (StringUtils.isNotEmpty(characterType)) {
			Optional<Enemy> optEnemy = enemyList //
					.stream() //
					.filter((enemy) -> characterType.equalsIgnoreCase(enemy.getSpecies())) //
					.findFirst();
			boolean optEnemyIsPresent = optEnemy.isPresent();
			if (!optEnemyIsPresent) {
				String formattedMessage = String.format(MessageConstants.MESSAGE_6, characterType);
				throw new RuntimeException(formattedMessage);
			}
			Enemy enemy = optEnemy.get();
			enemyMap.put(position, enemy);
		}
		field.setEnemyMap(enemyMap);
		return this;
	}

	public ContextHelper setEnemy(String[] info) throws InstantiationException, IllegalAccessException {
		List<Enemy> enemyList = Utils.getter(context.getEnemyList(), ArrayList.class);
		String characterType = info[0];
		if (StringUtils.isNotEmpty(characterType)) {
			Optional<Enemy> optEnemy = enemyList //
					.stream() //
					.filter((enemy) -> characterType.equalsIgnoreCase(enemy.getSpecies())) //
					.findFirst();
			boolean optEnemyIsPresent = optEnemy.isPresent();
			if (!optEnemyIsPresent) {
				optEnemy = Optional.of(new Enemy());
			}
			Enemy enemy = optEnemy.get();
			enemy.setSpecies(characterType);
			if (!optEnemyIsPresent) {
				enemyList.add(enemy);
			}
		}
		context.setEnemyList(enemyList);
		return this;
	}

	public ContextHelper addToConsole(String str, Object... args) throws InstantiationException, IllegalAccessException {
		List<String> console = Utils.getter(context.getConsole(), ArrayList.class);
		String formattedMessage = String.format(str, args);
		console.add(formattedMessage);
		context.setConsole(console);
		return this;
	}

	public ContextHelper printConsole() throws InstantiationException, IllegalAccessException {
		List<String> console = Utils.getter(context.getConsole(), ArrayList.class);
		console.forEach(System.out::println);
		return this;
	}
}
