package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.CommentConverter;
import com.example.demo.model.commentModel;
import com.example.demo.repository.CommentRepository;
import com.example.demo.service.CommentService;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	  @Autowired
	    @Qualifier("commentRepository")
	    private CommentRepository commentRepository;
	  
	  @Autowired
	    @Qualifier("commentConverter")
	    private CommentConverter commentConverter;

	@Override
	public boolean addComment(commentModel comment) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateComment(commentModel comment) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteComment(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<commentModel> getListComments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<commentModel> getListFindByPunctuationComments() {
		// TODO Auto-generated method stub
		return null;
	}
}
