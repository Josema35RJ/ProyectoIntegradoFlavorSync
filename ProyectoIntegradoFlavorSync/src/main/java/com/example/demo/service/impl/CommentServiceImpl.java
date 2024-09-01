package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.CommentConverter;
import com.example.demo.converter.RecipeConverter;
import com.example.demo.entity.comment;
import com.example.demo.model.commentModel;
import com.example.demo.model.recipeModel;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.service.CommentService;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	  @Autowired
	    @Qualifier("commentRepository")
	    private CommentRepository commentRepository;
	  
	  @Autowired
	    @Qualifier("commentConverter")
	    private CommentConverter commentConverter;
	  
	  @Autowired
	    @Qualifier("recipeRepository")
	    private RecipeRepository recipeRepository;
	  
	  @Autowired
	    @Qualifier("recipeConverter")
	    private RecipeConverter recipeConverter;

	@Override
	public boolean addComment(commentModel comment) {
		// TODO Auto-generated method stub
		if(comment.getDescription().isBlank() || comment.getDescription().isEmpty())
		commentRepository.save(commentConverter.transform(comment));
		recipeModel r = recipeConverter.transform(recipeRepository.findById(comment.getId()).get());
		r.setAverageRating((float) ((r.getAverageRating()+comment.getPunctuation())/2));
		return true;
	}

	@Override
	public boolean updateComment(commentModel comment) {
		// TODO Auto-generated method stub
		comment c = commentRepository.findById(comment.getId()).get();
		if(!comment.getDescription().equalsIgnoreCase(c.getDescription())) {
			c.setDescription(comment.getDescription());
		}else if(comment.getPunctuation()!=(c.getPunctuation())) {
			recipeModel r = recipeConverter.transform(recipeRepository.findById(comment.getId()).get());
			r.setAverageRating((float) ((r.getAverageRating()+comment.getPunctuation())/2));
		}
		return true;
	}

	@Override
	public boolean deleteComment(int id) {
	    // Encuentra el comentario por su ID
	    comment c = commentRepository.findById(id);
	    
	    // Encuentra la receta asociada al comentario
	    recipeModel r = recipeConverter.transform(recipeRepository.findById(c.getRecipeId()));
	    
	    // Calcula la nueva nota media restando la puntuación del comentario a borrar
	    float newAverageRating = (float) ((r.getAverageRating() * r.getListComments().size() - c.getPunctuation()) / (r.getListComments().size() - 1));
	    r.setAverageRating(newAverageRating);
	    
	    // Guarda los cambios en la receta
	    recipeRepository.save(recipeConverter.transform(r));
	    
	    // Borra el comentario
	    commentRepository.delete(c);
	    
	    return true;
	}


	@Override
	public List<commentModel> getListComments() {
		// TODO Auto-generated method stub
		List<commentModel> ListComment = new ArrayList<>();
		for(comment c : commentRepository.findAll()) {
			ListComment.add(commentConverter.transform(c));
		}
		return ListComment;
	}

	//Obtener lista de comentarios en base al id de la receta y la puntuacion
	@Override
	public List<commentModel> getListCommentsFindByRecipeIdAndPunctuation(int recipeId, int punctuation) {
		// TODO Auto-generated method stub
				List<commentModel> ListCommentByRecipeIdAndPunctuation = new ArrayList<>();
				for(comment c : commentRepository.findByRecipeIdAndPunctuation(recipeId, punctuation)) {
					ListCommentByRecipeIdAndPunctuation.add(commentConverter.transform(c));
				}
				return ListCommentByRecipeIdAndPunctuation;
	}
	
	//Obtener lista de comentarios en base al id de la receta
	@Override
	public List<commentModel> getListCommentsFindByRecipeId(int recipeId) {
		// TODO Auto-generated method stub
		List<commentModel> ListCommentByRecipeId = new ArrayList<>();
		for(comment c : commentRepository.findByRecipeId(recipeId)){
			ListCommentByRecipeId.add(commentConverter.transform(c));
		}
		return ListCommentByRecipeId;
	}
}
