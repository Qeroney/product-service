package com.example.demo.service.user;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.CustomUser;
import com.example.demo.model.Role;
import com.example.demo.repository.CustomUserRepository;
import com.example.demo.service.user.argument.CreateCustomUserArgument;
import com.example.demo.service.user.argument.UpdateCustomUserArgument;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl implements CustomUserService {

    private final CustomUserRepository repository;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public CustomUser create(@NonNull CreateCustomUserArgument argument) {
        return repository.save(CustomUser.builder()
                                         .email(argument.getEmail())
                                         .password(argument.getPassword())
                                         .cart(argument.getCart())
                                         .balance(0L)
                                         .role(Role.USER)
                                         .build());
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public CustomUser update(@NonNull UpdateCustomUserArgument argument) {
        return repository.save(CustomUser.builder()
                                         .email(argument.getEmail())
                                         .password(argument.getPassword())
                                         .balance(argument.getBalance())
                                         .build());
    }

    @Override
    @Transactional(readOnly = true)
    public CustomUser getExisting(@NonNull UUID id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found: ", id));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomUser findByEmail(@NonNull String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found", null));
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public CustomUser changeBalance(@NonNull UUID id, @NonNull Long balance) {
        CustomUser customUser = getExisting(id);
        customUser.setBalance(balance);
        return repository.save(customUser);
    }
}
