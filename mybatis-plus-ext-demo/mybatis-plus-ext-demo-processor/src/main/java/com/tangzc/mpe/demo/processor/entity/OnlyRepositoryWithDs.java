package com.tangzc.mpe.demo.processor.entity;

import com.tangzc.mpe.autotable.annotation.Table;
import com.tangzc.mpe.processer.annotation.AutoRepository;
import lombok.Data;

@Table(dsName = "test")
@AutoRepository(withDSAnnotation = true)
@Data
public class OnlyRepositoryWithDs {

    private String id;
    private String name;
    private int age;
}