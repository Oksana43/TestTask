package com.task.demo.repository;


import com.task.demo.entity.UniqueWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniqueWordRepository extends JpaRepository<UniqueWord, Long> {

}
