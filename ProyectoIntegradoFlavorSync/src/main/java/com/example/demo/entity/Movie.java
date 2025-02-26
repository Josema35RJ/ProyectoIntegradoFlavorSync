package com.example.demo.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "movie")
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "title", nullable = false, length = 50)
    @Size(max = 50, message = "The title cannot exceed 50 characters")
    @NotBlank(message = "Title is required")
    private String title;

    @Column(name = "description", nullable = false, length = 455)
    @Size(max = 455, message = "The description cannot exceed 455 characters")
    @NotBlank(message = "Description is required")
    private String description;

    private boolean isShort = false; 

    @Column(name = "duration")
    private float duration = 0;

    @Column(name = "video", columnDefinition = "TEXT")
    private String video;

    @Column(name = "likes_count")
    private int likesCount;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;
}
