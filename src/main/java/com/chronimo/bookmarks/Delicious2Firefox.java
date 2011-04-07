package com.chronimo.bookmarks;

import java.io.File;
import java.io.IOException;

import com.chronimo.bookmarks.delicious.DeliciousManager;
import com.chronimo.bookmarks.firefox.FirefoxManager;

public class Delicious2Firefox {
	
	public static void main(String [] args) throws IOException {
		if (args.length < 3) {
			System.err.println("Usage: java -jar delicious2firefox.jar <delicious .htm file> <firefox .json file> <new .json file>");
			return;
		}
		
		File deliciousFile = new File(args[0]);
		if (!deliciousFile.exists() || !deliciousFile.canRead()) {
			System.err.println("Can't read the delicious file.");
			return;
		}
		File firefoxFile = new File(args[1]);
		if (!firefoxFile.exists() || !firefoxFile.canRead()) {
			System.err.println("Can't read the firefoxFile file.");
			return;
		}
		File outFile = new File(args[2]);
		if ((outFile.exists() && !outFile.canWrite()) || (!outFile.exists() && !outFile.createNewFile())) {
			System.err.println("Can't write to outFile.");
			return;
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