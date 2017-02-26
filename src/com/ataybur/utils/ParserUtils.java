package com.ataybur.utils;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ataybur.enums.LineTypes;
import com.ataybur.pojo.base.Character;

public class ParserUtils {
	public static void parseIntoContext(String line) throws InstantiationException, IllegalAccessException {
		new LineChecker(line) //
		.parseForLineType() //
		.parseLineToInfo() //
		.putParsedLineIntoContext();
//		Optional<LineTypes> lineTypes = parseForLineType(line);
//		String[] parsedLine = parseLine(line, lineTypes);
//		putParsedLineIntoContext(parsedLine, lineTypes);
	}

	private static boolean isFound(String line, String regex) {
		return Pattern //
				.compile(regex) //
				.matcher(line) //
				.find();
	}

	private static Predicate<LineTypes> isFound(String line) {
		return lineTypes -> Pattern //
				.compile(lineTypes.getRegex()) //
				.matcher(line) //
				.find();
	}

	private static Optional<LineTypes> parseForLineType(String line) {
		return Arrays //
				.asList(LineTypes.values()) //
				.stream() //
				.filter(lineTypes -> Pattern //
						.compile(lineTypes.getRegex()) //
						.matcher(line) //
						.find()) //
				.findFirst();
	}

	private static String[] parseLine(String line, Optional<LineTypes> lineTypes) {
		String[] result = null;
		if (lineTypes.isPresent()) {
			Matcher matcher = Pattern //
					.compile(lineTypes.get().getRegex()) //
					.matcher(line);
			if (matcher.find()) {
				int size = matcher.groupCount();
				result = new String[size];
				for (int i = 1; i <= size; i++) {
					result[i - 1] = matcher.group(i);
				}
			}
		}
		return result;
	}

	private static void putParsedLineIntoContext(String[] parsedLine, Optional<LineTypes> lineTypes)
			throws InstantiationException, IllegalAccessException {
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
