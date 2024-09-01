package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.comment;
import com.example.demo.model.commentModel;

@Repository("commentRepository")
public interface CommentRepository extends JpaRepository<comment, Serializable> {

	comment findById(int CommnetId);
	List<comment> findByRecipeId (int recipeId);
	List<comment> findByRecipeIdAndPunctuation(int recipeId, int Punctuaction);

}