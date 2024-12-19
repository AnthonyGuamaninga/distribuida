package com.programacion.distribuida.service;

import com.programacion.distribuida.db.Book;

import java.util.List;

public interface BookService {

    public Book getById(Integer id);
    public List<Book> getAll();
    public String save(Book book);
    public Book update(Integer id, Book book);
    public String delete(Integer id);
}
