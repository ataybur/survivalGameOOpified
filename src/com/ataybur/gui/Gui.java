package com.ataybur.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.ataybur.main.App;
import com.ataybur.utils.FileGetter;

public class Gui extends JFrame {

    private static final long serialVersionUID = 5422603205441353041L;

    public Gui() {
	super("Survival Game");
	setSize(900, 400);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	Container c = getContentPane();
	c.setLayout(new FlowLayout());
	FileGetter inputFile = new FileGetter();
	FileGetter outputFile = new FileGetter();
	JButton inputButton = new JButton("INPUT");
	inputButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent ae) {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		int option = chooser.showOpenDialog(inputButton);
		if (option == JFileChooser.APPROVE_OPTION) {
		    File file = chooser.getSelectedFile();
		    inputFile.setFile(file);
		}
	    }
	});

	JButton outputButton = new JButton("OUTPUT");
	outputButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent ae) {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		int option = chooser.showOpenDialog(inputButton);
		if (option == JFileChooser.APPROVE_OPTION) {
		    File file = chooser.getSelectedFile();
		    outputFile.setFile(file);
		}
	    }
	});

	JButton runButton = new JButton("RUN");
	runButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent ae) {
		new App(inputFile, outputFile).run();
	    }
	});
	c.add(inputButton);
	c.add(outputButton);
	c.add(runButton);
    }

}
