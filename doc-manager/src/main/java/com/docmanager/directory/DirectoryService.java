package com.docmanager.directory;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DirectoryService {
    private List<File> files;
    private String rootPath;
    
	public DirectoryService(String rootPath) {
		super();
		setRootPath(rootPath);
	}
	public DirectoryService() {
		super();
	}
	public List<File> getFiles() {
		return files;
	}
	
	public List<Directory> getFullDirectoryFromRootPath(){
		File maindir = new File(rootPath);
		files = Arrays.asList(maindir.listFiles());
		return DirectoryUtil.retrieveDirectoryFromFilest(files, 0);
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
		//DirectoryUtil.activateMonitoringPathAndSubPaths(Paths.get(rootPath));
	}
}