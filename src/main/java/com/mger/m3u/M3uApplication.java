package com.mger.m3u;

import java.io.File;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mger.m3u.model.Model;

@SpringBootApplication
public final class M3uApplication implements CommandLineRunner {

	public final static void main(final String[] args) {
		SpringApplication.run(M3uApplication.class, args);
	}

	@Bean
	public M3uService getHelloService() {
		return new DefaultM3uService();
	}

	@Override
	public void run(final String... args) throws Exception {
		final String pathname = "";
		final File file = new File(pathname);
		final Model model = getHelloService().fromFile(file);
		
		model.getEntries().forEach(e -> System.out.println(e));
	}

}
