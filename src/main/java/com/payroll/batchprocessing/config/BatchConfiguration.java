package com.payroll.batchprocessing.config;

import com.payroll.batchprocessing.batch.EmployeeItemProcessor;
import com.payroll.batchprocessing.batch.JobCompletionNotificationListener;
import com.payroll.batchprocessing.model.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
@Configuration
public class BatchConfiguration {
    @Bean
    public FlatFileItemReader<Employee> reader(){
        return new FlatFileItemReaderBuilder<Employee>()
                .name("personItemReader")
                .resource(new ClassPathResource("data.csv"))
                .delimited()
                .names(new String[]{"id","name","hours"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Employee>
                        (){{setTargetType(Employee.class);}})
                .build();
    }
    @Bean
    public ItemProcessor<Employee, Employee> processor(){
        return new EmployeeItemProcessor();
    }
    @Bean
    public JdbcBatchItemWriter<Employee> writer(DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<Employee>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO people (id, name,hours) VALUES (:id, :name,:hours)")
                .dataSource(dataSource)
                .build();
    }
    @Bean
    public Job importUserJob(JobRepository jobRepository,
                             JobCompletionNotificationListener listener, Step step)
    {
    return new JobBuilder("importUserJob",jobRepository)
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step)
            .end()
            .build();
    }
    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                     JdbcBatchItemWriter<Employee> writer){
        return new StepBuilder("step",jobRepository)
                .<Employee, Employee> chunk(10,platformTransactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }


}
