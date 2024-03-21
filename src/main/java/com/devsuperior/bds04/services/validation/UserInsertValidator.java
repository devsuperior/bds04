package com.devsuperior.bds04.services.validation;

import com.devsuperior.bds04.dto.UserInsertDTO;
import com.devsuperior.bds04.controllers.exceptions.FieldMessage;
import com.devsuperior.bds04.entities.User;
import com.devsuperior.bds04.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {


    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        User user = repository.findByEmail(dto.getEmail());

        // Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista

        if(user != null) {
            list.add(new FieldMessage("email", "Este email já existe."));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}