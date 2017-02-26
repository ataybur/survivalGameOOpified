package com.ataybur.utils;

import com.ataybur.pojo.Context;

public class ConsoleWriterToFile extends WriterToFile {
	private Context context;
	
	public ConsoleWriterToFile() {
		super();
	}
	
	public ConsoleWriterToFile setContext(Context context){
		this.context = context;
		return this;
	}
	
	@Override
	public ConsoleWriterToFile prepareFile() {
		this.messages = context.getConsole();
		return this;
	}

}
