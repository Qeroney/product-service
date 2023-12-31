package com.example.demo.service.category;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.category.argument.CreateCategoryArgument;
import com.example.demo.service.category.argument.SearchCategoryArgument;
import com.example.demo.service.category.argument.UpdateCategoryArgument;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Category> list(@NonNull SearchCategoryArgument argument) {
        return repository.findAll();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Category create(@NonNull CreateCategoryArgument categoryArgument) {
        return repository.save(Category.builder()
                                       .title(categoryArgument.getTitle())
                                       .build());
    }

    @Override
    @Transactional(readOnly = true)
    public Category getExisting(@NonNull UUID id) {
        return repository.findById(id)
                         .orElseThrow(() -> new RuntimeException("категория не найдена"));
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Category update(@NonNull UUID id, @NonNull UpdateCategoryArgument categoryArgument) {
        Category category = getExisting(id);

        category.setTitle(categoryArgument.getTitle());

        return repository.save(category);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(@NonNull UUID id) {
        repository.deleteById(id);
    }
}
