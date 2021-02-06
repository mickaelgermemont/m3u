package com.mger.m3u.model;

public class DefaultEntry implements Entry {

	private final String id;
	private final String name;
	private final String logo;
	private final String groupTitle;
	private final String description;
	private final String url;

	public DefaultEntry(String id, String name, String logo, String groupTitle, String description, String url) {
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.groupTitle = groupTitle;
		this.description = description;
		this.url = url;
	}

	@Override
	public String getGrouptitle() {
		return groupTitle;
	}

	@Override
	public String getTvgid() {
		return id;
	}

	@Override
	public String getTvglogo() {
		return logo;
	}

	@Override
	public String getTvgname() {
		return name;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return String.format("Entry(%s,%s,%s,%s,%s)", this.getTvgid(), this.getTvgname(), this.getTvglogo(),
				this.getGrouptitle(), this.getDescription(), this.getUrl());
	}
}
