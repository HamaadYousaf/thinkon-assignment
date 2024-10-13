package com.thinkon.user_management;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/** Provides REST API interface and interacts with the {@link UserService} to perform operations */
@RestController
public class UserController {

    private final UserService userService;

    /**
     * Constructs a new UserController with the specified {@link UserService}.
     *
     * @param userService the service used to manage user entities.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user.
     *
     * @param userEntity the user entity to be created.
     * @return a {@link ResponseEntity} containing the created user and an HTTP status of CREATED.
     */
    @PostMapping(path = "/users")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity) {
        UserEntity createdUser = userService.createUser(userEntity);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Retrieves all users.
     *
     * @return a {@link ResponseEntity} containing the list of all users and an HTTP status of OK.
     */
    @GetMapping(path = "/users")
    public ResponseEntity<List<UserEntity>> getUsers() {
        List<UserEntity> allUsers = userService.getUsers();

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    /**
     * Retrieves a user by its id.
     *
     * @param id the unique identifier of the user to retrieve.
     * @return a {@link ResponseEntity} containing the user and an HTTP status of OK.
     * @throws UserNotFoundException if the user with the specified id does not exist.
     */
    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("id") Long id) {
        Optional<UserEntity> userById = userService.getUserById(id);

        if (userById.isEmpty()) {
            throw new UserNotFoundException();
        }

        return new ResponseEntity<>(userById.get(), HttpStatus.OK);
    }

    /**
     * Updates an existing user by its id.
     *
     * @param userEntity the updated user entity.
     * @param id the unique identifier of the user to update.
     * @return a {@link ResponseEntity} containing the updated user and an HTTP status of OK.
     * @throws UserNotFoundException if the user with the specified id does not exist.
     */
    @PutMapping(path = "/users/{id}")
    public ResponseEntity<UserEntity> updateUserById(
            @RequestBody UserEntity userEntity, @PathVariable("id") Long id) {

        if (userService.userDoesNotExists(id)) {
            throw new UserNotFoundException();
        } else {
            UserEntity updatedUser = userService.updateUser(userEntity, id);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
    }

    /**
     * Deletes a user by its id.
     *
     * @param id the unique identifier of the user to delete.
     * @return a {@link ResponseEntity} with an HTTP status of NO_CONTENT.
     * @throws UserNotFoundException if the user with the specified id does not exist.
     */
    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<UserEntity> deleteUserById(@PathVariable("id") Long id) {

        if (userService.userDoesNotExists(id)) {
            throw new UserNotFoundException();
        }

        userService.deleteUserById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
