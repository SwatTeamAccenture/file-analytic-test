package main.batch;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import main.entidade.CsvMapper;
import main.negocio.ArquivoSaidaBusiness;

/**
 * Classe para executar funções antes e depois da execução do JOB principal
 */
@Component
public class JobNotificationListeners extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobNotificationListeners.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void beforeJob(JobExecution jobExecution) {
    	if (jobExecution.getStatus() == BatchStatus.STARTED) {
    		log.info("!!! JOB INICIADO");
    		System.out.println("INÍCIO JOB");
    		
    	}
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            
            List<CsvMapper> linhasCSV = this.jdbcTemplate.query("SELECT identificador, coluna1, coluna2, coluna3 FROM csvmapper",
                    (rs, row) -> new CsvMapper(rs.getString(1), rs.getString(2),  rs.getString(3),  rs.getString(4)));

            for (CsvMapper csvMapper : linhasCSV) {
                log.info("Linha <" + csvMapper.toString() + "> encontrada.");
            }
            
            try {
				gerarArquivoSaida(linhasCSV);
			} catch (IOException e) {
				e.printStackTrace();
			}

            this.jdbcTemplate.execute("TRUNCATE TABLE csvmapper;");

            log.info("!!! JOB FINALIZADO!");
            System.out.println("FIM JOB");

        }
    }

	private void gerarArquivoSaida(List<CsvMapper> linhasCSV) throws IOException {
		
		ArquivoSaidaBusiness arquivoSaidaBusiness = new ArquivoSaidaBusiness(linhasCSV);
		arquivoSaidaBusiness.gerarSaida();
		
	}
    
}
