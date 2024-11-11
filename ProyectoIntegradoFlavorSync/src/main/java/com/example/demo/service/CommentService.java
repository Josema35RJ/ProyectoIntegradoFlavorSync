package com.example.demo.service;

import java.util.List;

import com.example.demo.model.commentModel;
import com.example.demo.model.cookModel;
import com.example.demo.model.recipeModel;

public interface CommentService {
	
	boolean addComment (commentModel comment, int RecipeId);
	boolean updateComment (commentModel comment);
	boolean deleteComment (int id);
	List<commentModel> getListComments();
	List<commentModel> getListCommentsFindByPunctuation(int punctuation);
	commentModel findById(int id);
	boolean findByUserId(cookModel c, recipeModel r);
}
