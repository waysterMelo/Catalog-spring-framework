package com.wayster.catalog.services;

import com.wayster.catalog.dto.CategoryDTO;
import com.wayster.catalog.dto.RoleDto;
import com.wayster.catalog.dto.UserDto;
import com.wayster.catalog.entity.CategoryEntity;
import com.wayster.catalog.entity.Role;
import com.wayster.catalog.entity.User;
import com.wayster.catalog.entity.User;
import com.wayster.catalog.repositories.RoleRepository;
import com.wayster.catalog.repositories.UserRepository;
import com.wayster.catalog.services.servicesExceptions.DatabaseException;
import com.wayster.catalog.services.servicesExceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Transactional(readOnly = true)
    public Page<UserDto> findAllPaged(Pageable pageable) {
        Page<User> list = userRepository.findAll(pageable);
        return list.map(x -> new UserDto(x));
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id){
        Optional<User> rs = userRepository.findById(id);
        User entity = rs.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserDto(entity);
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<User> lista = userRepository.findAll();
        return lista.stream().map(UserDto::new).collect(Collectors.toList());
     }


     @Transactional
    public UserDto insert(UserDto dto) {
        User User = new User();
        copyDtoToEntity(dto, User);
        User = userRepository.save(User);
        return new UserDto(User);
    }

    private void copyDtoToEntity(UserDto dto, User User) {
        User.setFirst_name(dto.getFirstName());
        User.setLast_name(dto.getLastName());
        User.setEmail(dto.getEmail());
        User.setPassword(dto.getPassword());
        User.getRoles().clear();
        for (RoleDto roleDto : dto.getRoles()){
        //este método busca uma entidade Role no banco de dados
            // (por meio do repositório) com base no ID fornecido pelo roleDto
          Role roleEntity = roleRepository.getReferenceById(roleDto.getId());
          User.getRoles().add(roleEntity);

      }

    }

    @Transactional
    public UserDto update (UserDto dto, Long id){
       try {
           User entity = userRepository.getOne(id);
           copyDtoToEntity(dto, entity);
           entity = userRepository.save(entity);
           return new UserDto(entity);

       }catch (EntityNotFoundException ex){
           throw new ResourceNotFoundException("Id not found" + id);
       }

    }

    @Transactional
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
                throw new ResourceNotFoundException("Id not found" + id);
        }catch (DataIntegrityViolationException data_ex){
            throw new DatabaseException("Integrity Violation");
        }
    }


}
