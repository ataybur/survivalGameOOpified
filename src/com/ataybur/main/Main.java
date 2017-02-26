package com.ataybur.main;

import java.io.IOException;

import com.ataybur.constants.Constants;
import com.ataybur.lambda.ConsumerThrowing;
import com.ataybur.pojo.Context;
import com.ataybur.utils.ConsoleWriterToFile;
import com.ataybur.utils.ErrorWriterToFile;
import com.ataybur.utils.FileReader;
import com.ataybur.utils.Game;
import com.ataybur.utils.InputRetriever;
import com.ataybur.utils.ParserUtils;

public class Main {
	private static Context context = Context.getInstance();

	public static void main(String[] args) {
		String fileNameOutput = null;
		try {
			InputRetriever inputRetriever = new InputRetriever(args);
			String fileNameInput = inputRetriever.retrieveInputFileName();
			fileNameOutput = inputRetriever.retrieveOutputFileName();
			ConsumerThrowing<String> consumer = ParserUtils::parseIntoContext;
			new FileReader(fileNameInput) //
					.getStream() //
					.forEach(consumer); //
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
