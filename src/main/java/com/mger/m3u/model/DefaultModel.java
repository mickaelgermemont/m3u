package com.mger.m3u.model;

import java.util.LinkedList;
import java.util.List;

public class DefaultModel implements Model {

	private final List<Entry> entries;
	
	public DefaultModel() {
		this.entries = new LinkedList<Entry>();
	}
	
	public boolean add(final Entry entry) {
		return this.entries.add(entry);
	}
	
	@Override
	public List<Entry> getEntries() {
		return entries;
	}

}
