package com.example.springmvcrest.api.v1.mapper;


import com.example.springmvcrest.api.v1.model.CategoryDTO;
import com.example.springmvcrest.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDTO(Category category);

}
