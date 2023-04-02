package com.example.csvfileapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface csvRepository extends JpaRepository<csvBean,Integer>{

}
