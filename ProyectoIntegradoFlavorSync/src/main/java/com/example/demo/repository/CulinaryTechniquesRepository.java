package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.culinaryTechniques;

@Repository("culinaryTechniquesRepository")
public interface CulinaryTechniquesRepository extends JpaRepository<culinaryTechniques, Serializable> {

	culinaryTechniques findById(int CulinaryTechniquesId);


}