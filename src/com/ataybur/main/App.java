package com.ataybur.main;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.ataybur.lambda.FunctionThrowing;
import com.ataybur.pojo.Context;
import com.ataybur.utils.ConsoleWriterToFile;
import com.ataybur.utils.ContextHelper;
import com.ataybur.utils.ErrorWriterToFile;
import com.ataybur.utils.FileGetter;
import com.ataybur.utils.FileReader;
import com.ataybur.utils.Game;
import com.ataybur.utils.LineChecker;
import com.ataybur.utils.LineParser;
import com.ataybur.utils.WriterToFile;

public class App {
    private FileGetter inputFile;
    private FileGetter outputFile;

    public App(FileGetter inputFile, FileGetter outputFile) {
	super();
	this.inputFile = inputFile;
	this.outputFile = outputFile;
    }

    public void run() {
	String fileNameInput = inputFile.getFile().getAbsolutePath();
	String fileNameOutput = outputFile.getFile().getAbsolutePath() + File.separatorChar + "output.txt";
	try {
	    FunctionThrowing<Game, Optional<Context>> gameStartGame = Game::startGame;
	    FunctionThrowing<ConsoleWriterToFile, WriterToFile> consoleWriterToFileWrite = ConsoleWriterToFile::write;
	    ContextHelper contextHelper = new ContextHelper(fileNameOutput);
	    // List<LineInfo> list =
	    new FileReader(fileNameInput) //
		    .getStream() //
		    .map(LineChecker::new) //
		    .map(LineChecker::parseForLineType) //
		    .map(LineParser::parseLineToInfo) //
		    // .collect(Collectors.toList());
		    .forEach(contextHelper::applyLineInfo);
	    Optional.of(contextHelper.toInstance()).map(Game::new).map(gameStartGame) //
		    .map(ConsoleWriterToFile::new) //
		    .map(ConsoleWriterToFile::prepareFile) //
		    .map(consoleWriterToFileWrite);

	    // new Game(context).startGame();
	    // new ConsoleWriterToFile(context) //
	    // .prepareFile() //
	    // .write();
	} catch (RuntimeException | IOException e) {
	    try {
		// if (fileNameOutput == null) {
		// fileNameOutput = Constants.ERROR_LOG;
		// }
		e.printStackTrace();
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
