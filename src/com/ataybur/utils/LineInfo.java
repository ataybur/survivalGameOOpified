package com.ataybur.utils;

import java.util.Optional;

import com.ataybur.enums.LineTypes;

public class LineInfo {
	private String[] parsedLine;
	private Optional<LineTypes> lineTypes;

	public LineInfo(String[] parsedLine, Optional<LineTypes> lineTypes) {
		super();
		this.parsedLine = parsedLine;
		this.lineTypes = lineTypes;
	}

	public void putParsedLineIntoContext() throws InstantiationException, IllegalAccessException {
		ContextHelper contextHelper = new ContextHelper();
		if (parsedLine != null && lineTypes.isPresent()) {
			lineTypes //
					.get() //
					.getSetter() //
					.apply(contextHelper, parsedLine);
		}
	}
}
