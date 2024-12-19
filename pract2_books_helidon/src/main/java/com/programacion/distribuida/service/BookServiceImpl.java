package com.programacion.distribuida.service;

import com.programacion.distribuida.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class BookServiceImpl implements BookService {

    @Inject
    private EntityManager em;

    @Override
    public Book getById(Integer id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("select b from Book b order by b.id asc", Book.class).getResultList();
    }

    @Override
    public String save(Book book) {
        try{
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
            return "El libro "+book.getIsbn()+" ha sido guardado correctamente";
        }catch(Exception e){
            em.getTransaction().rollback();
            return e.getMessage();
        }
    }

    @Override
    public Book update(Integer id, Book book) {
        var actualizar = em.find(Book.class, id);
        if(actualizar != null){
            actualizar.setIsbn(book.getIsbn());
            actualizar.setAuthor(book.getAuthor());
            actualizar.setTitle(book.getTitle());
            actualizar.setPrice(book.getPrice());
            return em.merge(actualizar);
        }
        return null;
    }

    @Override
    public String delete(Integer id) {
        try{
            em.getTransaction().begin();
            Book book = em.find(Book.class, id);
            em.remove(book);
            em.getTransaction().commit();
            return "Se eliminado el libro "+book.getIsbn();
        }catch(Exception e){
            em.getTransaction().rollback();
            return e.getMessage();
        }

    }
}
