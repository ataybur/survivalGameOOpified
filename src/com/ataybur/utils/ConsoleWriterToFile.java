package com.ataybur.utils;

import java.util.ArrayList;
import java.util.Optional;

import com.ataybur.pojo.Context;

public class ConsoleWriterToFile extends WriterToFile {
    private Optional<Context> context;

    public ConsoleWriterToFile(Optional<Context> context) {
	super();
	this.context = context;
    }

    @Override
    public ConsoleWriterToFile prepareFile() {
	this.messages = context.map(Context::getConsole) //
		.orElse(new ArrayList<String>());
	this.fileName = context.map(Context::getOutputFileName) //
		.orElse("output-file.txt");
	return this;
    }

}
