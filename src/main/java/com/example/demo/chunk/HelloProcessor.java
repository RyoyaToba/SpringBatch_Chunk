package com.example.demo.chunk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Slf4j
public class HelloProcessor implements ItemProcessor<String, String> {

    @Value("#{JobExecutionContext['jobKey']}")
    private String jobValue;

    @Value("#{StepExecutionContext['stepKey']}")
    private String stepValue;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution){
        log.info("jobKey={}", jobValue);
        log.info("stepKey={}", stepValue);
    }

    @Override
    public String process(String item) throws Exception {
        item = item + "★";
        log.info("Processor:{}", item);
        return item;
    }
}
