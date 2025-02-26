package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Movie;
import com.example.demo.model.MovieModel;

@Component("movieConverter")
public class MovieConverter {

	public Movie transform(MovieModel co) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(co, Movie.class);
	}

	public MovieModel transform(Movie co) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(co, MovieModel.class);
	}

}
