package com.mger.m3u;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mger.m3u.model.DefaultEntry;
import com.mger.m3u.model.DefaultModel;
import com.mger.m3u.model.Entry;
import com.mger.m3u.model.Model;

@Service
public class DefaultM3uService implements M3uService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultM3uService.class);

	@Override
	public Model fromFile(final File file) {
		final DeSerializer deSerializer = new DeSerializer();

		if (!file.exists()) {
			LOGGER.error("file not found");
			return deSerializer.getResult();
		}

		deSerializer.execute(file);
		
		if(!deSerializer.getRejected().isEmpty()) {
			LOGGER.error("rejected={}", deSerializer.getRejected());
		}
		
		
		return deSerializer.getResult();
	}

	private final static class Serializer {

	}

	public final static class DeSerializer {

		private final DefaultModel result;
		private final Pattern pattern;
		private final List<String> rejected;

		public DeSerializer() {
			final String arabic = "\\u0600-\\u06FF";
			final String validtextchars = "\\w-| \\/\\.:#²!\\*\\+%\\?´=\\(\\)&@'¹\\[\\],;"+arabic;
			pattern = Pattern.compile("#EXTINF:-1 tvg-id=\"(["+validtextchars+"]*)\" tvg-name=\"(["+validtextchars+"]*)\" tvg-logo=\"(["+validtextchars+"]*)\" group-title=\"(["+validtextchars+"]*)\",(["+validtextchars+"]*)");
			result = new DefaultModel();
			this.rejected = new LinkedList<String>();
		}
		
		public List<String> getRejected() {
			return rejected;
		}

		public void execute(final File file) {
			try {
				final BufferedReader reader = Files.newBufferedReader(file.toPath());
				int i = 1;
				final String header = reader.readLine();

				if (!checkFirstLine(header)) {
					LOGGER.error(String.format("error. line=%i invalid format", i));
				}

				boolean eof = false;
				do {
					final String line1 = reader.readLine();
					final String line2 = reader.readLine();

					if (line1 == null && line2 == null) {
						// this is the end of the file
						eof = true;
					} else if (line1 == null || line2 == null) {
						// else end of file but invalid format
						LOGGER.error("error. invalid end of file, expected to find 2 lines");
						eof = true;
					} else {
						this.result.add(makeEntry(i, line1, line2));
					}

					i += 2;

				} while (!eof);

			} catch (final Exception e) {
				LOGGER.error("cant deserialize, reading file failed", e);
			}

		}

		public Entry makeEntry(final int lineNumber, final String line1, final String line2) {

			final Matcher matcher = pattern.matcher(line1);

			String id = "";
			String name = "";
			String logo = "";
			String groupTitle = "";
			String description = "";

			if (matcher.find()) {
//				LOGGER.debug("Match found at line {}", lineNumber);
				id = matcher.group(1);
				name = matcher.group(2);
				logo = matcher.group(3);
				groupTitle = matcher.group(4);
				description = matcher.group(5);
				
				if(name==null || name.isEmpty()) {
					LOGGER.debug("name is empty, lineContent {}", name);
					this.rejected.add(line1);
				}
//			} else if(line1.contains("tvg-logo=\"data:image")){
//				LOGGER.debug("Match not found, known issue 1, at line {}", lineNumber);
				//this.rejected.add(line1);
			} else {
				LOGGER.debug("Match not found at line {}", lineNumber);
				this.rejected.add(line1);
			}

			return new DefaultEntry(id, name, logo, groupTitle, description, line2);
		}

		public DefaultModel getResult() {
			return result;
		}

		public boolean checkFirstLine(final String line) {
			return line != null && line.equals("#EXTM3U");
		}
	}

	@Override
	public void toFile(Model model, File file) {
		// TODO Auto-generated method stub

	}
}
