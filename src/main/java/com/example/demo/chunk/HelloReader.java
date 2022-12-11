package com.example.demo.chunk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Slf4j
public class HelloReader implements ItemReader<String> {

    private String[] input = {"Hello", "World", "hoge", "fuga", null, "The World"};
    private int index = 0;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution){
        ExecutionContext jobContext = stepExecution.getJobExecution().getExecutionContext();
        jobContext.put("jobKey", "jobValue");

        ExecutionContext stepContext = stepExecution.getExecutionContext();
        stepContext.put("stepKey", "stepValue");
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String message = input[index++];
        log.info("Read:{}", message);
        return message;
    }
}
