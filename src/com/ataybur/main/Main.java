package com.ataybur.main;

import java.io.IOException;
import java.util.Optional;

import com.ataybur.constants.Constants;
import com.ataybur.pojo.Context;
import com.ataybur.utils.ConsoleWriterToFile;
import com.ataybur.utils.ContextFiller;
import com.ataybur.utils.ContextHelper;
import com.ataybur.utils.ErrorWriterToFile;
import com.ataybur.utils.FileReader;
import com.ataybur.utils.Game;
import com.ataybur.utils.InputRetriever;
import com.ataybur.utils.LineChecker;
import com.ataybur.utils.LineInfo;
import com.ataybur.utils.LineParser;

public class Main {
	private static Context context = Context.getInstance();

	public static void main(String[] args) {
		String fileNameOutput = null;
		try {
			InputRetriever inputRetriever = new InputRetriever(args);
			String fileNameInput = inputRetriever.retrieveInputFileName();
			fileNameOutput = inputRetriever.retrieveOutputFileName();
			Optional<Context> context = new FileReader(fileNameInput) //
					.getStream() //
					.map(LineChecker::new) //
					.map(LineChecker::parseForLineType) //
					.map(LineParser::parseLineToInfo) //
					.map(LineInfo::getContextFiller) //
					.map(ContextFiller::applyLineInfo) //
					.map(ContextHelper::toInstance) //
					.findFirst();
					;
			new Game(context).startGame();
			new ConsoleWriterToFile() //
					.setContext(context) //
					.prepareFile() //
					.write();
		} catch (RuntimeException | IOException | InstantiationException | IllegalAccessException e) {
			try {
				if (fileNameOutput == null) {
					fileNameOutput = Constants.ERROR_LOG;
				}
				new ErrorWriterToFile() //
						.setException(e) //
						.setFileName(fileNameOutput) //
						.prepareFile() //
						.write();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
	}

}
