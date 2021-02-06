package com.mger.m3u;

import com.mger.m3u.model.Model;

public interface M3uFilterService {
	public Model filterByGrouptitleContains(final Model model, String value);
	public Model filterByDescriptionContains(final Model model, String value);
}
