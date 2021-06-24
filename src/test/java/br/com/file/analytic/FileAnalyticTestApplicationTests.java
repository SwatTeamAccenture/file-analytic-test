package br.com.file.analytic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.com.fileanalytictest.service.FileProcessService;

class FileAnalyticTestApplicationTests {

	@TempDir
	Path tempDir;

	@Test
	public void testFileProcess() throws IOException {
		List<String> lines = new ArrayList<String>();
		lines.add("001ç1234567891234çAnaç50000");
		lines.add("001ç3245678865434çConceição Conceiçãoç40000.99");
		lines.add("001ç3245678865434çPauloç40000.99");
		lines.add("002ç2345675434544345çJose da SilvaçRural");
		lines.add("002ç2345675434544345çConceição da ConceiçãoçAçougue Açougue");
		lines.add("002ç2345675433444345çEduardo PereiraçRural");
		lines.add("003ç07ç[1-3-100,2-33-1.50,3-40-0.10]çPaulo");
		lines.add("003ç08ç[1-2-100,2-33-1.50,3-40-0.10]çAna");
		lines.add("003ç09ç[1-1-100,2-33-1.50,3-40-0.10]çConceição Conceição");

		Path tempFile = Files.createFile(tempDir.resolve("teste.txt"));
		Files.write(tempFile, lines);

		String processReturn = FileProcessService.processFiles(tempFile.toFile());

		Assertions.assertEquals("3ç3ç07çConceição Conceição", processReturn);
	}

	@Test
	public void testFindValueSeller() {
		String s = "001ç3245678865434çConceição Conceiçãoç40000.99";
		List<String> values = FileProcessService.findValuesOnLine(s);
		Assertions.assertEquals(values.size(), 4);
		Assertions.assertEquals(values.get(0), "001");
		Assertions.assertEquals(values.get(1), "3245678865434");
		Assertions.assertEquals(values.get(2), "Conceição Conceição");
		Assertions.assertEquals(values.get(3), "40000.99");
	}
	
	@Test
	public void testFindValueClient() {
		String s = "002ç2345675434544345çConceição da ConceiçãoçAçougue Açougue";
		List<String> values = FileProcessService.findValuesOnLine(s);
		Assertions.assertEquals(values.size(), 4);
		Assertions.assertEquals(values.get(0), "002");
		Assertions.assertEquals(values.get(1), "2345675434544345");
		Assertions.assertEquals(values.get(2), "Conceição da Conceição");
		Assertions.assertEquals(values.get(3), "Açougue Açougue");
	}
	
	@Test
	public void testFindValueSale() {
		String s = "003ç08ç[1-34-100,2-33-1.50,3-40-0.10]çConceição Conceição";
		List<String> values = FileProcessService.findValuesOnLine(s);
		Assertions.assertEquals(values.size(), 4);
		Assertions.assertEquals(values.get(0), "003");
		Assertions.assertEquals(values.get(1), "08");
		Assertions.assertEquals(values.get(2), "[1-34-100,2-33-1.50,3-40-0.10]");
		Assertions.assertEquals(values.get(3), "Conceição Conceição");
	}

}
