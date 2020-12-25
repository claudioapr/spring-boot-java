package com.docmanager.directory;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.docmanager.utils.Status;

public class Directory extends ResourceSupport{
	
	private String name;
	private String abslutePath;
	private List<Directory> subDirectories;
	private Status status; 
	private String extension;
	
	private boolean isFile;
	
	public String getName() {
		return name;
	}
	public List<Directory> getSubDirectories() {
		return subDirectories;
	}
	public boolean isFile() {
		return isFile;
	}
	public void setIsFile(boolean isFile) {
		this.isFile = isFile;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSubDirectories(List<Directory> subDirectories) {
		this.subDirectories = subDirectories;
	}
	public String getAbslutePath() {
		return abslutePath;
	}
	public void setAbslutePath(String abslutePath) {
		this.abslutePath = abslutePath;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
