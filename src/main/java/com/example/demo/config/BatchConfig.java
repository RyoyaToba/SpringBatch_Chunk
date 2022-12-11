package com.example.demo.config;

import com.example.demo.chunk.HelloProcessor;
import com.example.demo.chunk.HelloReader;
import com.example.demo.chunk.HelloWriter;
import com.example.demo.listener.HelloJobListener;
import com.example.demo.listener.HelloStepListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemReader<String> reader;

    @Autowired
    private ItemProcessor<String, String> processor;

    @Autowired
    private ItemWriter<String> writer;

    @Autowired
    private JobExecutionListener jobListener;

    @Autowired
    private StepExecutionListener stepListener;

    @Bean
    public Step chunkStep(){
        return stepBuilderFactory.get("HelloChunkStep")
                .<String, String>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(stepListener)
                .build();
    }

    @Bean
    public Job chunkJob() throws Exception{
        return jobBuilderFactory.get("HelloWorldChunkJob")
                .incrementer(new RunIdIncrementer())
                .start(chunkStep())
                .listener(jobListener)
                .build();
    }
}
