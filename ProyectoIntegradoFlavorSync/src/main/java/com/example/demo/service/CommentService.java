package com.example.demo.service;

import java.util.List;

import com.example.demo.model.commentModel;

public interface CommentService {
	
	boolean addComment (commentModel comment, int RecipeId);
	boolean updateComment (commentModel comment);
	boolean deleteComment (int id);
	List<commentModel> getListComments();
	List<commentModel> getListCommentsFindByPunctuation(int punctuation);
}
