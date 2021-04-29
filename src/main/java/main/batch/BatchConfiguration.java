package main.batch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.PathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import main.entidade.CsvMapper;

@EnableBatchProcessing
@SpringBootApplication
@EnableScheduling
public class BatchConfiguration {

	private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	JobNotificationListeners listener;
	
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private DataSource dataSource;

	private static String PATH_IN = System.getProperty("user.home") + "\\data\\in\\";

	private static List<String> arquivoLidoList = new ArrayList<String>();
	private File proximoArquivoEntrada;

	/**
	 * Método para executar aplicação SPRING BATCH
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		SpringApplication.run(BatchConfiguration.class, args);
	}

	/**
	 * Método para ler arquivo CSV
	 */
	@StepScope
	@Bean
	public FlatFileItemReader<CsvMapper> reader() {
		System.out.println("Início reader");
		FlatFileItemReader<CsvMapper> reader = new FlatFileItemReader<CsvMapper>();
		reader.setEncoding("ISO-8859-1");
		reader.setResource(new PathResource(proximoArquivoEntrada.getAbsolutePath()));

		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setNames(new String[] { "identificador", "coluna1", "coluna2", "coluna3" });
		delimitedLineTokenizer.setDelimiter("ç");
		delimitedLineTokenizer.setStrict(false);

		BeanWrapperFieldSetMapper<CsvMapper> beanWrapper = new BeanWrapperFieldSetMapper<CsvMapper>();
		beanWrapper.setTargetType(CsvMapper.class);

		DefaultLineMapper<CsvMapper> defaultLineMapper = new DefaultLineMapper<CsvMapper>();
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		defaultLineMapper.setFieldSetMapper(beanWrapper);

		reader.setLineMapper(defaultLineMapper);

		System.out.println("fim reader");

		return reader;
	}

	/**
	 * Método para processar arquivo CSV
	 */
	@StepScope
	@Bean
	public FileCsvProcessor processor() {
		System.out.println("Início processor");
		System.out.println("Fim processor");
		return new FileCsvProcessor();
	}

	/**
	 * Método para escrever em banco de dados H2 as linhas lidas do arquivo CSV
	 */
	@StepScope
	@Bean
	public JdbcBatchItemWriter<CsvMapper> writer() {
		System.out.println("Início writer");
		JdbcBatchItemWriter<CsvMapper> writer = new JdbcBatchItemWriter<>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		writer.setSql("INSERT INTO csvmapper (identificador, coluna1, coluna2, coluna3) VALUES (:identificador, :coluna1, :coluna2, :coluna3);");
		writer.setDataSource(this.dataSource);
		System.out.println("Fim writer");
		return writer;
	}

	/**
	 * Etapa única do JOB a ser executado (reader > processor > writer)
	 */
	@Bean
	public Step step() {
		System.out.println("início step");
		return stepBuilderFactory.get("step1").<CsvMapper, CsvMapper>chunk(1).reader(reader()).processor(processor())
				.writer(writer()).allowStartIfComplete(true).build();
	}

	private static File verificarArquivoEntrada() throws IOException {
		File fileIn = new File(PATH_IN);
		for (String file : fileIn.list()) {
			if (!arquivoLidoList.contains(file)) {
				arquivoLidoList.add(file);
				return new File(PATH_IN + file);
			} else {
				continue;
			}

		}
		return null;
	}

	/**
	 * JOB principal a ser executado repetidas vezes para ficar escutando a pasta de
	 * entrada
	 * 
	 * @throws IOException
	 */
	public Job job() throws IOException {

		System.out.println("rodou job");
		proximoArquivoEntrada = verificarArquivoEntrada();

		if (proximoArquivoEntrada != null) {
			return this.jobBuilderFactory.get("job1").listener(listener).start(step()).incrementer(new RunIdIncrementer()).build();
		} else {
			return null;
		}
	}

	//Método que roda de 2 em 2 segundos e reinicia o Job 
	@Scheduled(fixedRate = 2000)
	public void executeJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {
		JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
		JobExecution execution = null;
		Job job = this.job();
		
		if(job != null) {
			execution = jobLauncher.run(job, jobParameters);
			execution.getExitStatus();
		}
		
	}
}
