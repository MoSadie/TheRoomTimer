package io.github.mosadie.TheRoomTimer;

import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Main implements Runnable{

	JLabel timer;
	int seconds = 0;
	int minutes = 0;
	int hours = 0;

	@Override
	public void run() {

		//Inital Setup
		String possibleId = "";
		File[] directories = new File("C:\\Program Files (x86)\\Steam\\userdata\\").listFiles(File::isDirectory);
		if (directories.length > 0) possibleId = directories[0].getName();
		Path path = Paths.get("C:\\Program Files (x86)\\Steam\\userdata\\" + JOptionPane.showInputDialog("Enter your steamID (ex 98168481) (Press OK if you have no idea what that is)",possibleId) + "\\288160\\remote\\GlobalData.sav");
		
		if (!Files.exists(path)) {
			JOptionPane.showMessageDialog(null, "Error: Save files not found. Did you type your steam id correctly?","Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//Show the window
		JFrame f = new JFrame("The Room Timer");
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setLayout(new FlowLayout());
		timer = new JLabel("00:00:00");
		timer.setFont(new Font("Calibri",Font.BOLD,90));
		f.add(timer);
		JLabel statusText = new JLabel("Waiting for Chapter 1 (Tutorial) to be completed...");
		f.add(statusText);
		f.pack();
		f.setVisible(true);

		//Backup (if any) existing game save
		System.out.println("Backing up saves. Printing all possible save file paths.");
		String[] saveFileNames = { "GlobalData.sav", "Safe.save", "OuterShell.save", "Orrery.save", "OrreryTop.save", "Epilogue.save" };
		for (int i = 0; i <saveFileNames.length; i++) {
			Path tmpPath = Paths.get(path.getParent().toString()+"\\"+saveFileNames[i]);
			System.out.println(tmpPath.toString());
			if (Files.exists(tmpPath)) {
				try {
					Path backupPath = Paths.get(tmpPath.toString()+".bak");
					Files.deleteIfExists(backupPath);
					Files.move(tmpPath, backupPath);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(f, "An error has occured while backing up your saves: " + e.toString(),"Error Encountered", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}

		//Create "reset" game save
		System.out.println("Creating 'reset' game save file.");
		try {
			List<String> lines = Arrays.asList("MaxLevelCompleted=0", "CurrentPlaythroughLevel=0","SaveVersion=1","Volume=10","HintsEnabled=True");
			Files.write(path, lines, Charset.forName("UTF-8"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(f, "An error has occured creating the 0% save: " + e.toString(), "Error Encountered", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Timer timerThread = new Timer(path, f, statusText, timer);
		timerThread.start();
	}



	public static void main(String[] args) {
		Main main = new Main();
		SwingUtilities.invokeLater(main);
	}
}
