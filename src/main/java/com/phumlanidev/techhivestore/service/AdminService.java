package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {

  private final UsersRepository usersRepository;

    /**
     * <p> This method deletes a user by their ID from the repository. </p>
     *
     * @param id the ID of the user to delete.
     */
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}
