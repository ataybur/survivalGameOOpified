package com.ataybur.utils;

import java.util.Optional;

import com.ataybur.pojo.Context;

public class ConsoleWriterToFile extends WriterToFile {
	private Optional<Context> context;

	public ConsoleWriterToFile() {
		super();
	}

	public ConsoleWriterToFile setContext(Optional<Context> context) {
		this.context = context;
		return this;
	}

	@Override
	public ConsoleWriterToFile prepareFile() {
		if (context.isPresent()) {
			this.messages = context.get().getConsole();
		}
		return this;
	}

}
