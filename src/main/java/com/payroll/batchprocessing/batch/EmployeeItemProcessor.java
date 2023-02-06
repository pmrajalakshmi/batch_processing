package com.payroll.batchprocessing.batch;

import com.payroll.batchprocessing.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {

    private static final Logger log = LoggerFactory.getLogger(EmployeeItemProcessor.class);

    @Override
    public Employee process(final Employee employee) throws Exception {
        final String id = employee.getId();
        final String name = employee.getName();
        final Integer hours = employee.getHours();
        final Double pay = hours * 12.0;
        final Employee processed = new Employee(id, name, hours,pay);

        log.info("Processing pay roll from (" + employee + ") to (" + processed + ")");

        return processed;
    }

}