package io.github.mosadie.TheRoomTimer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Timer extends Thread {
	
	public int hours = 0;
	public int minutes = 0;
	public int seconds = 0;
	
	private Path path;
	private JFrame f;
	private JLabel statusText;
	private JLabel timer;
	
	public Timer(Path pathOfSave, JFrame frame, JLabel statusText, JLabel timer) {
		this.path = pathOfSave;
		this.f = frame;
		this.statusText = statusText;
		this.timer = timer;
	}
	
	public void run() {
		int currentPlaythroughLevel;
		int maxLevelCompleted;

		System.out.println("Starting tutorial finish loop");
		
		//Start loop, waiting for CurrentPlaythroughLevel to increment to 1 to indicate the completion of the tutorial.
		do {
			try {
				List<String> lines = Files.readAllLines(path);
				maxLevelCompleted = Integer.parseInt(lines.get(0).split("=")[1]);
				currentPlaythroughLevel = Integer.parseInt(lines.get(1).split("=")[1]);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(f, "An error has occured: " + e.toString(), "Error Encountered", JOptionPane.ERROR_MESSAGE);
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		} while (currentPlaythroughLevel < 1);
		//Tutorial Complete. Indicate status update.
		statusText.setText("Chapter 1 (Tutorial) Finished. Timer started.");
		System.out.println("Chapter 1 (Tutorial) Finished. Timer started.");
		f.pack();

		//Timer for first level after tutorial

		do {
			try {
				List<String> lines = Files.readAllLines(path);
				maxLevelCompleted = Integer.parseInt(lines.get(0).split("=")[1]);
				currentPlaythroughLevel = Integer.parseInt(lines.get(1).split("=")[1]);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(f, "An error has occured: " + e.toString(), "Error Encountered", JOptionPane.ERROR_MESSAGE);
				break;
			}
			try {
				incrementAndUpdateTimer();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		} while (maxLevelCompleted < 1);

		//Finished level 1. Update status.
		statusText.setText("<html><p>The Outer Shell (Chapter 2) finished at "+doubleNumber(hours)+":"+doubleNumber(minutes)+":"+doubleNumber(seconds)+"</p>");
		System.out.println("The Outer Shell (Chapter 2) finished at "+doubleNumber(hours)+":"+doubleNumber(minutes)+":"+doubleNumber(seconds));
		f.pack();

		//Timer for second level after tutorial

		do {
			try {
				List<String> lines = Files.readAllLines(path);
				maxLevelCompleted = Integer.parseInt(lines.get(0).split("=")[1]);
				currentPlaythroughLevel = Integer.parseInt(lines.get(1).split("=")[1]);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(f, "An error has occured: " + e.toString(), "Error Encountered", JOptionPane.ERROR_MESSAGE);
				break;
			}
			try {
				incrementAndUpdateTimer();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		} while (maxLevelCompleted < 2);

		//Finished level 2. Update status.
		statusText.setText(statusText.getText()+"<p>The Orrery (Chapter 3) finished at "+doubleNumber(hours)+":"+doubleNumber(minutes)+":"+doubleNumber(seconds)+"</p>");
		System.out.println("The Orrery (Chapter 3) finished at "+doubleNumber(hours)+":"+doubleNumber(minutes)+":"+doubleNumber(seconds));
		f.pack();

		//Timer for third level after tutorial

		do {
			try {
				List<String> lines = Files.readAllLines(path);
				maxLevelCompleted = Integer.parseInt(lines.get(0).split("=")[1]);
				currentPlaythroughLevel = Integer.parseInt(lines.get(1).split("=")[1]);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(f, "An error has occured: " + e.toString(), "Error Encountered", JOptionPane.ERROR_MESSAGE);
				break;
			}
			try {
				incrementAndUpdateTimer();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		} while (maxLevelCompleted < 3);

		//Finished level 3. Update status.
		statusText.setText(statusText.getText()+"<p>The Orrery Top (Chapter 4) finished at "+doubleNumber(hours)+":"+doubleNumber(minutes)+":"+doubleNumber(seconds)+"</p>");
		System.out.println("The Orrery Top (Chapter 4) finished at "+doubleNumber(hours)+":"+doubleNumber(minutes)+":"+doubleNumber(seconds));
		f.pack();

		//Timer for fourth (and final) level after tutorial

		do {
			try {
				List<String> lines = Files.readAllLines(path);
				maxLevelCompleted = Integer.parseInt(lines.get(0).split("=")[1]);
				currentPlaythroughLevel = Integer.parseInt(lines.get(1).split("=")[1]);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(f, "An error has occured: " + e.toString(), "Error Encountered", JOptionPane.ERROR_MESSAGE);
				break;
			}
			try {
				incrementAndUpdateTimer();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		} while (maxLevelCompleted < 4);

		//Finished level 4. Update status.
		statusText.setText(statusText.getText()+"<p>The Epilogue (Epilogue) finished at "+doubleNumber(hours)+":"+doubleNumber(minutes)+":"+doubleNumber(seconds)+"</p>");
		System.out.println("The Epilogue (Epilogue) finished at "+doubleNumber(hours)+":"+doubleNumber(minutes)+":"+doubleNumber(seconds));
		f.pack();
		
		//Display Finish message
		System.out.println("Finished! Time: "+hours+":"+minutes+":"+seconds);
		JOptionPane.showMessageDialog(f, "Finished! Your time is "+doubleNumber(hours)+":"+doubleNumber(minutes)+":"+doubleNumber(seconds)+"!");
	}
	
	public void incrementAndUpdateTimer() {
		seconds++;
		if (seconds >= 60) {
			seconds = 0;
			minutes++;
		}
		if (minutes >= 60) {
			minutes = 0;
			hours++;
		}
		timer.setText(doubleNumber(hours)+":"+doubleNumber(minutes)+":"+doubleNumber(seconds));
		f.pack();
	}
	
	public String doubleNumber(int numberIn) {
		if (numberIn == 0) return "00";
		if (numberIn > 0 && numberIn < 10) return "0"+numberIn;
		return String.valueOf(numberIn);
	}
}
