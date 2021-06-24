package br.com.fileanalytictest;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import br.com.fileanalytictest.service.FileProcessService;

public class Application {

	public static void main(String[] args) throws IOException, InterruptedException {
		WatchService watchService = FileSystems.getDefault().newWatchService();

		Path path = Paths.get(FileProcessService.HOME + "/in");
		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

		WatchKey key;
		while ((key = watchService.take()) != null) {
			for (WatchEvent<?> event : key.pollEvents()) {
				ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
				scheduledThreadPool.schedule(() -> {
					if (event.context().toString().endsWith(".txt")) {
						File file = new File(FileProcessService.HOME + "/in/" + event.context());
						String processReturn = FileProcessService.processFiles(file);
						FileProcessService.moveFile(file);
						FileProcessService.writeOutFile(processReturn, file.getName());
					}

				}, 2, TimeUnit.SECONDS);
			}
			key.reset();
		}

	}

}
