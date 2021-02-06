package com.mger.m3u;

import java.io.File;

import com.mger.m3u.model.Model;

public interface M3uService {
	public Model fromFile(final File file);
	public void toFile(final Model model, final File file);
}
