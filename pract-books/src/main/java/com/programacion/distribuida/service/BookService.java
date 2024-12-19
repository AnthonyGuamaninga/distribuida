package com.programacion.distribuida.service;

import com.programacion.distribuida.db.Book;

import java.util.List;

public interface BookService {
    public Book getById(int id);
    public List<Book> getAll();
    public String insertBook(Book book);
    public Book putBook(int id, Book book);
    public String deleteBook(int id);
}
