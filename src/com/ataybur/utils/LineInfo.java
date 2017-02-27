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

	public ContextFiller getContextFiller(){
		return new ContextFiller(this);
	}
	
	public ContextHelper putParsedLineIntoContext(ContextHelper contextHelper) {
		if (parsedLine != null && lineTypes.isPresent()) {
			lineTypes //
					.get() //
					.getSetter() //
					.apply(contextHelper, parsedLine);
		}
		return contextHelper;
	}
}
