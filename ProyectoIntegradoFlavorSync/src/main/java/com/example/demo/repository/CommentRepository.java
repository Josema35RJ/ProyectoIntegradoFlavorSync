package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.comment;
import com.example.demo.entity.cook;

@Repository("commentRepository")
public interface CommentRepository extends JpaRepository<comment, Serializable> {

	comment findById(int CommnetId);
	List<comment> findByPunctuation( int Punctuaction);
	List<comment> findByCookId(cook cook);

}