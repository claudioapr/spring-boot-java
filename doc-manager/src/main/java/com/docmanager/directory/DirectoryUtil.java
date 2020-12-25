package com.docmanager.directory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.docmanager.utils.Status;

public class DirectoryUtil {
    
	protected static List<Directory> retrieveDirectoryFromFilest(List<File> arr, int level) {
		List<Directory> dirs = new ArrayList<Directory>();
		for (File f : arr) {
			Directory dir = new Directory();
			dir.setIsFile(f.isFile());
			dir.setAbslutePath(f.getAbsolutePath());
			dir.setName(f.getName());
			dir.setStatus(Status.CLOSED);
			dir.setExtension((f.isFile() ? getFileExtensionFromName(f.getName()) : ""));
			if (f.isDirectory()) 
				dir.setSubDirectories(retrieveDirectoryFromFilest(Arrays.asList(f.listFiles()), level++));

			dirs.add(dir);
		}
		return dirs;
	}

	public static String getFileExtensionFromName(String name) {
		String extension = "";
		int index = name.lastIndexOf(".");
		extension = name.substring(index + 1);
		return extension;
	}

	public static void watchingDirectory(Path pathToBeMonitored) {
		WatchService watchService;
		try {
			watchService = FileSystems.getDefault().newWatchService();
			Path path = pathToBeMonitored;
			path.register(watchService, 
					StandardWatchEventKinds.ENTRY_CREATE, 
					StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);
			
			WatchKey key;
	        while ((key = watchService.take()) != null) {
	            for (WatchEvent<?> event : key.pollEvents()) {
	                System.out.println(
	                  "Event kind:" + event.kind() 
	                    + ". File affected: " + event.context() + ".");
	            }
	            key.reset();
	        }
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void activateMonitoringPathAndSubPaths(Path path) {
		 try {
			new WatchDirectory(path, true).processEvents();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
