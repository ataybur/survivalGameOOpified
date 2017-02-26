package com.ataybur.utils;

import java.util.Optional;

import com.ataybur.enums.LineTypes;
import com.ataybur.pojo.base.Character;

public class LineInfo {
	private String[] parsedLine;
	private Optional<LineTypes> lineTypes;

	public LineInfo(String[] parsedLine, Optional<LineTypes> lineTypes) {
		super();
		this.parsedLine = parsedLine;
		this.lineTypes = lineTypes;
	}

	public void putParsedLineIntoContext() throws InstantiationException, IllegalAccessException {
		if (parsedLine != null && lineTypes.isPresent()) {
			switch (lineTypes.get()) {
			case RANGE:
				if (parsedLine.length == 1) {
					ContextUtils.setRange(parsedLine[0]);
				}
				break;
			case HP:
				if (parsedLine.length == 2) {
					ContextUtils.setCharacterPoint(parsedLine, Character::setHp);
				}
				break;
			case ATTACK_POINT:
				if (parsedLine.length == 2) {
					ContextUtils.setCharacterPoint(parsedLine, Character::setAttackPoint);
				}
				break;
			case ENEMY:
				if (parsedLine.length == 1) {
					ContextUtils.setEnemy(parsedLine[0]);
				}
				break;
			case ENEMY_POSITION:
				if (parsedLine.length == 2) {
					ContextUtils.setEnemyPosition(parsedLine);
				}
				break;
			}
		}
	}
}
