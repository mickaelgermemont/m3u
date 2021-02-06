package com.mger.m3u;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mger.m3u.model.DefaultModel;
import com.mger.m3u.model.Model;

@Service
public class DefaultM3uFilterService implements M3uFilterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultM3uFilterService.class);

	@Override
	public Model filterByGrouptitleContains(Model model, String value) {
		final DefaultModel result = new DefaultModel();
		
// 		model.getEntries().stream().map(e -> e.getDescription()).distinct().forEach(System.out::println);
		model.getEntries().stream().filter(e -> e.getGrouptitle().toLowerCase().contains(value.toLowerCase())).forEach(e -> result.add(e));
		
		return result;
	}

	@Override
	public Model filterByDescriptionContains(Model model, String value) {
		final DefaultModel result = new DefaultModel();
		
// 		model.getEntries().stream().map(e -> e.getDescription()).distinct().forEach(System.out::println);
		model.getEntries().stream().filter(e -> e.getDescription().toLowerCase().contains(value.toLowerCase())).forEach(e -> result.add(e));
		
		return result;
	}

}
