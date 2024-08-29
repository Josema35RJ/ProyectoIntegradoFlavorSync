package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.comment;
import com.example.demo.model.commentModel;

@Component("commentConverter")
public class CommentConverter {

	public comment transform(commentModel co) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(co, comment.class);
	}

	public commentModel transform(comment co) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(co, commentModel.class);
	}

}
