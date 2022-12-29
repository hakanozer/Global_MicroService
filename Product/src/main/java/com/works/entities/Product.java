package com.works.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Length(min = 2, max = 100)
    @NotEmpty
    @NotNull
    @Column(unique = true, length = 100)
    private String title;

    @Max(10000)
    @Min(1)
    @NotNull
    private Integer price;

    @ManyToMany
    private List<Category> categories;


}
