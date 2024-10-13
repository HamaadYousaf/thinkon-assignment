package com.thinkon.user_management;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/** Service class responsible for interacting with the {@link UserRepository}. */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructs a new UserService with the specified {@link UserRepository}.
     *
     * @param userRepository the repository used to manage user entities.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user in the database.
     *
     * @param userEntity the {@link UserEntity} object containing user details to be saved.
     * @return the {@link UserEntity} object saved into the database.
     */
    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of all the {@link UserEntity} objects in the databsae.
     */
    public List<UserEntity> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single user from the database by the id.
     *
     * @param id the unique identifier of the user to retrieve.
     * @return an {@link Optional<UserEntity>} containing the user if found, or an empty if not
     *     found.
     */
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Updates an existing user in the database by the id.
     *
     * @param userEntity the {@link UserEntity} object containing the updated user data.
     * @param id the unique identifier of the user to update.
     * @return the updated {@link UserEntity} object after it has been saved to the database.
     * @throws RuntimeException if the user with the specified id does not exist.
     */
    public UserEntity updateUser(UserEntity userEntity, Long id) {
        return userRepository
                .findById(id)
                .map(
                        existingUser -> {
                            existingUser.setId(id);
                            Optional.ofNullable(userEntity.getUsername())
                                    .ifPresent(existingUser::setUsername);
                            Optional.ofNullable(userEntity.getFirstName())
                                    .ifPresent(existingUser::setFirstName);
                            Optional.ofNullable(userEntity.getLastName())
                                    .ifPresent(existingUser::setLastName);
                            Optional.ofNullable(userEntity.getEmail())
                                    .ifPresent(existingUser::setEmail);
                            Optional.ofNullable(userEntity.getPhone())
                                    .ifPresent(existingUser::setPhone);
                            return userRepository.save(existingUser);
                        })
                .orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    /**
     * Deletes a user in the database by the id.
     *
     * @param id the unique identifier of the user to delete.
     */
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Checks if a user with the specified id does not exist in the repository.
     *
     * @param id the unique identifier of the user to check.
     * @return True if the user does not exist, or False if the user exists.
     */
    public boolean userDoesNotExists(Long id) {
        return !userRepository.existsById(id);
    }
}
