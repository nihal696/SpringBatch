package com.example.springBatch.Service;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.springBatch.Dao.OrgProcessor;
import com.example.springBatch.Entities.Orginizations;


@Configuration
@EnableBatchProcessing
public class ConfigBatch {
	@Autowired
	private DataSource ds;
	
	@Autowired
	private JobBuilderFactory js;
	
	@Autowired
	 private StepBuilderFactory sf;
	
	@Bean
	public FlatFileItemReader<Orginizations> read(){
		FlatFileItemReader<Orginizations> fr = new FlatFileItemReader<>();
		fr.setResource(new ClassPathResource("users.csv"));
		fr.setLineMapper(getMapper());
		fr.setLinesToSkip(1);
		return fr;
		
	}
	
     @Bean
	public LineMapper<Orginizations> getMapper() {
		
		DefaultLineMapper<Orginizations> d=new DefaultLineMapper<>();
		DelimitedLineTokenizer dt=new DelimitedLineTokenizer();
		dt.setNames(new String[] {"Index","Name","Country","Industry"});
		dt.setIncludedFields(new int[] {0,2,4,7});
		
		BeanWrapperFieldSetMapper<Orginizations> br=new BeanWrapperFieldSetMapper<>();
		br.setTargetType(Orginizations.class);
		
		d.setLineTokenizer(dt);
		d.setFieldSetMapper(br);
		
		return d;
	}
     
     @Bean
     public OrgProcessor process() { 
		return new OrgProcessor();  	 
     }
     
     @Bean
     public JdbcBatchItemWriter<Orginizations> write(){
    	 
    	 JdbcBatchItemWriter<Orginizations> jdbc = new JdbcBatchItemWriter<>();
    	 jdbc.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Orginizations>());
    	 jdbc.setSql("insert into organizationdetails(id,name,country,industry) values (:index,:name,:country,:industry)");
		jdbc.setDataSource(ds);
    	 return jdbc;
    	 
     }
     
     @Bean
     public Job userjob() {
		return this.js.get("user_job")
				.incrementer(new RunIdIncrementer())
				.flow(step1())
				.end()
				.build();
    	 
     }
     
     @Bean
	public Step step1() {
		 return this.sf.get("step1").<Orginizations,Orginizations>chunk(10)
				 .reader(read())
				 .processor(process())
		         .writer(write())
		         .build();
		
	}
}
