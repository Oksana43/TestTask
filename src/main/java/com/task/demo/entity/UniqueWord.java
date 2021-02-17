package com.task.demo.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class UniqueWord {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer count;
}
