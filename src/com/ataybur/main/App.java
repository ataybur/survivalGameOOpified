package com.ataybur.main;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.ataybur.lambda.FunctionThrowing;
import com.ataybur.lambda.LineInfoCollector;
import com.ataybur.lambda.RunnableThrowing;
import com.ataybur.pojo.Context;
import com.ataybur.utils.ConsoleWriterToFile;
import com.ataybur.utils.ErrorWriterToFile;
import com.ataybur.utils.FileReader;
import com.ataybur.utils.Game;
import com.ataybur.utils.LineChecker;
import com.ataybur.utils.LineParser;

public class App {
    private Optional<Context> context = Optional.ofNullable(Context.getInstance());

    public App() {
	super();
    }

    public void load(File inputFile) {
	handleException(() -> {
	    String fileNameInput = inputFile.getAbsolutePath();
	    new FileReader(fileNameInput) //
		    .getStream() //
		    .map(LineChecker::new) //
		    .map(LineChecker::parseForLineType) //
		    .map(LineParser::parseLineToInfo) //
		    .collect(new LineInfoCollector());
	});
    }

    public void run() {
	FunctionThrowing<Game, Optional<Context>> gameStartGame = Game::startGame;
	context.map(Game::new) //
		.map(gameStartGame);
    }

    public void write(File outputFile) {
	handleException(() -> {
	    String fileNameOutput = outputFile.getAbsolutePath() + File.separatorChar + "output.txt";
	    new ConsoleWriterToFile(context) //
		    .setFileName(fileNameOutput) //
		    .prepareFile() //
		    .write();
	});
    }

    private void handleException(RunnableThrowing runnable) {
	try {
	    runnable.run();
	} catch (Exception e) {
	    try {
		e.printStackTrace();
		new ErrorWriterToFile() //
			.setException(e) //
			.setFileName(new File(".").getAbsolutePath()) //
			.prepareFile() //
			.write();
	    } catch (IOException e1) {
		e1.printStackTrace();
	    }
	}
    }
}
