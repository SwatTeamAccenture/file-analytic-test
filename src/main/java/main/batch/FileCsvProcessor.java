package main.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import main.entidade.CsvMapper;

public class FileCsvProcessor implements ItemProcessor<CsvMapper, CsvMapper> {

    private static final Logger log = LoggerFactory.getLogger(FileCsvProcessor.class);

    @Override
    public CsvMapper process(CsvMapper csvMapperLine) throws Exception {
    	
    	log.info("[info] removendo colchetes da coluna 2 do CSV");
    	
    	csvMapperLine.setColuna2(csvMapperLine.getColuna2().replace("[","").replace("]", ""));
    	
        return csvMapperLine;
    }
    
    
}