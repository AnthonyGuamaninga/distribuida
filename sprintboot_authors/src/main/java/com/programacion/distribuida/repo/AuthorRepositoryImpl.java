package com.programacion.distribuida.repo;

import com.programacion.distribuida.db.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepositoryImpl extends JpaRepository<Author, Integer> {


}
