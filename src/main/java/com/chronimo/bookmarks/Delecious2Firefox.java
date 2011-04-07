package com.chronimo.bookmarks;

import java.io.File;
import java.io.IOException;

import com.chronimo.bookmarks.delicious.DeliciousManager;
import com.chronimo.bookmarks.firefox.FirefoxManager;

public class Delecious2Firefox {
	
	public static void main(String [] args) throws IOException {
		if (args.length < 3) {
			System.err.println("Usage: java -jar delicious2firefox.jar <delicous.htm> <firefox.json> <outfile.json>");
		}
		
		File deliciousFile = new File(args[0]);
		if (!deliciousFile.exists() || !deliciousFile.canRead()) {
			System.err.println("Can't read the delicious file.");
		}
		File firefoxFile = new File(args[1]);
		if (!firefoxFile.exists() || !firefoxFile.canRead()) {
			System.err.println("Can't read the firefoxFile file.");
		}
		File outFile = new File(args[2]);
		if (!outFile.canWrite()) {
			System.err.println("Can't write to outFile.");
		}
		
		DeliciousManager delicious = new DeliciousManager();
		delicious.read(deliciousFile);
		
		FirefoxManager firefox = new FirefoxManager();
		firefox.read(firefoxFile);
				
		for (Bookmark bookmark : delicious.getBookmarks()){
			firefox.addBookmark(bookmark);
		}
		
		firefox.write(outFile);
	}
}