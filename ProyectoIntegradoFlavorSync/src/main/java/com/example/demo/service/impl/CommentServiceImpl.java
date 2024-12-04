package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.CommentConverter;
import com.example.demo.converter.CookConverter;
import com.example.demo.converter.RecipeConverter;
import com.example.demo.entity.comment;
import com.example.demo.entity.recipe;
import com.example.demo.model.commentModel;
import com.example.demo.model.cookModel;
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
	@Qualifier("cookConverter")
	private CookConverter cookConverter;

	@Autowired
	@Qualifier("recipeRepository")
	private RecipeRepository recipeRepository;

	@Autowired
	@Qualifier("recipeConverter")
	private RecipeConverter recipeConverter;

	@Override
	public boolean addComment(commentModel comment, int recipeId) {
		// Validar el comentario y la receta
		if (comment == null || comment.getPunctuation() < -1) {
			throw new IllegalArgumentException("El comentario no es válido.");
		}

		// Establecer la fecha de creación del comentario
		comment.setCreateDate(LocalDateTime.now());

		// Buscar la receta asociada
		recipe r = recipeRepository.findById(recipeId);
		if (r.toString().isEmpty()) {
			throw new IllegalArgumentException("No se encontró la receta con el ID proporcionado.");
		}

		// Convertir y obtener la receta
		recipeModel recipe = recipeConverter.transform(r);

		// Filtrar comentarios válidos para calcular el promedio de puntuación
		List<commentModel> validComments = recipe.getListComments().stream().filter(c -> c.getPunctuation() > 0).collect(Collectors.toList());

		// Calcular el nuevo promedio de puntuación
		int validCommentCount = validComments.size();
		if(validCommentCount==0)
			validCommentCount=+1;
		double updatedAverageRating = (recipe.getAverageRating() + comment.getPunctuation())
				/ validCommentCount;
		recipe.setAverageRating((float)updatedAverageRating);

		// Agregar el nuevo comentario a la receta
		recipe.getListComments().add(comment);

		// Guardar los cambios en la base de datos
		recipeRepository.save(recipeConverter.transform(recipe));

		return true;
	}

	@Override
	public boolean updateComment(commentModel comment) {
		// TODO Auto-generated method stub
		comment c = commentRepository.findById(comment.getId()).get();
		if (!comment.getDescription().equalsIgnoreCase(c.getDescription())) {
			c.setDescription(comment.getDescription());
		} else if (comment.getPunctuation() != (c.getPunctuation())) {
			recipeModel r = recipeConverter.transform(recipeRepository.findById(comment.getId()).get());
			r.setAverageRating((float) ((r.getAverageRating() + comment.getPunctuation()) / 2));
		}
		return true;
	}

	@Override
	public boolean deleteComment(int id) {

		return true;
	}

	@Override
	public List<commentModel> getListComments() {
		// TODO Auto-generated method stub
		List<commentModel> ListComment = new ArrayList<>();
		for (comment c : commentRepository.findAll()) {
			ListComment.add(commentConverter.transform(c));
		}
		return ListComment;
	}

	// Obtener lista de comentarios en base al id de la receta y la puntuacion
	@Override
	public List<commentModel> getListCommentsFindByPunctuation(int punctuation) {
		// TODO Auto-generated method stub
		List<commentModel> ListCommentByPunctuation = new ArrayList<>();
		for (comment c : commentRepository.findByPunctuation(punctuation)) {
			ListCommentByPunctuation.add(commentConverter.transform(c));
		}
		return ListCommentByPunctuation;
	}

	@Override
	public boolean findByUserId(cookModel c, recipeModel r) {
		// TODO Auto-generated method stub
		for (commentModel x : r.getListComments()) {
			if (x.getCookId().getId() == c.getId())
				return true;
			return false;
		}
		return false;
	}

	@Override
	public commentModel findById(int id) {
		// TODO Auto-generated method stub
		return commentConverter.transform(commentRepository.findById(id));
	}

}
