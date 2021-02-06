package com.mger.m3u;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

import com.mger.m3u.model.Model;

@Profile("!test")
@SpringBootApplication
public class M3uApplication implements CommandLineRunner {

	public final static void main(final String[] args) {
		SpringApplication.run(M3uApplication.class, args);
	}

	@Autowired
	M3uService m3uService;

	@Autowired
	M3uFilterService m3uFilterService;

	@Override
	public void run(final String... args) throws Exception {
		final File file = new File(args[0]);
		final Model model = m3uService.fromFile(file);

		final Model modelFiltered = m3uFilterService.filterByGrouptitleContains(model, args[1]);
		
		//model.getEntries().forEach(e -> System.out.println(e));
		//modelFiltered.getEntries().forEach(e -> System.out.println(e));

		final File fileOut = new File(args[0]+".filtered.m3u");
		m3uService.toFile(modelFiltered, fileOut);
	}

}
