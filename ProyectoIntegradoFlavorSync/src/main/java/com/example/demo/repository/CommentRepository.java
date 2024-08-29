package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.comment;
import com.example.demo.model.commentModel;

@Repository("commentRepository")
public interface CommentRepository extends JpaRepository<comment, Serializable> {

	boolean addCommnet (commentModel comment);
	boolean updateComment (commentModel comment);
	boolean deleteComment (int commentId);
	List<comment> findByCommentId(int CommnetId);
	List<comment> findByCommentRecipe (int recipeId);

}