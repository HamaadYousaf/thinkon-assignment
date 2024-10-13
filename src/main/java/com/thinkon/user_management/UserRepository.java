package com.thinkon.user_management;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Provides CRUD operations on the UserEntity with the database */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {}
