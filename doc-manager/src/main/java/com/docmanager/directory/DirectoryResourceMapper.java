package com.docmanager.directory;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

@Component
public class DirectoryResourceMapper {

	private EntityLinks entityLinks;
	private  static final String UPDATE = "update";
	private static final String DELETE = "delete";
	
	@Autowired
	public DirectoryResourceMapper(EntityLinks entityLinks){
		this.entityLinks = entityLinks;
	}

	public Directory toResource(Directory directory) {
		final Link selfLink = entityLinks.linkToSingleResource(Directory.class, directory);
		directory.add(selfLink.withSelfRel());
		directory.add(selfLink.withRel(UPDATE));
		directory.add(selfLink.withRel(DELETE));
		return directory;
	}
	public Collection<Directory> toResourceCollection(Collection<Directory> directories){
		return directories.stream().map(
				 dir -> toResource(dir))
				.collect(Collectors.toList());
	}
}
