package com.mger.m3u;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

import com.mger.m3u.model.Entry;
import com.mger.m3u.model.Model;

@ActiveProfiles("test") // https://www.baeldung.com/spring-junit-prevent-runner-beans-testing-execution
@SpringBootTest
class M3uApplicationTests {

	@Test
	void contextLoads() {
	}

	@Bean
	public M3uService getM3uService() {
		return new DefaultM3uService();
	}

	@Test
	void deser() throws IOException {
		final DefaultM3uService.DeSerializer deser = new DefaultM3uService.DeSerializer();
		final Entry entry = deser.makeEntry(1, "#EXTINF:-1 tvg-id=\"\" tvg-name=\"-------------- |SSSS| zzzzz SSSS --------------\" tvg-logo=\"https://zzzz.zzzz.com/ssss/sss/fla0.png\" group-title=\"AAAA BBBBB\",-------------- |zzzz| zzzz zzz --------------", "http://ww.wwwwwww.ww:11/11111/W11WwWw1ww/11111");
		System.out.println(entry);
		
		final Resource resource = new ClassPathResource("test.m3u");
		final File file = resource.getFile();
		final Model model = getM3uService().fromFile(file);
		
		model.getEntries().forEach(e -> System.out.println(e));
		
		assertEquals(3, model.getEntries().size());
		assertEquals(3, model.getEntries().stream().filter(e -> e.getTvgname()!=null &&  !e.getTvgname().isBlank()).count());
	}
	
}
