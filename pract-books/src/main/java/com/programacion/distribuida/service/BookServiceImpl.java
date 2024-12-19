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
    public Book getById(int id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public String insertBook(Book book) {
        try{
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
            return "Libro "+book.getIsbn()+ " insertado con exito";
        }catch(Exception e){
            em.getTransaction().rollback();
            return "Error al insertar el libro";
        }

    }

    @Override
    public Book putBook(int id, Book book) {
        try{
            var anterior = em.find(Book.class, id);
            anterior.setIsbn(book.getIsbn());
            anterior.setAuthor(book.getAuthor());
            anterior.setTitle(book.getTitle());
            anterior.setPrice(book.getPrice());

            em.getTransaction().begin();
            em.merge(anterior);
            em.getTransaction().commit();
            return book;
        }catch(Exception e){
            em.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public String deleteBook(int id) {
        try{
            em.getTransaction().begin();
            Book book = em.find(Book.class, id);
            if(book != null){
                em.remove(book);
                em.getTransaction().commit();
                return "Libro "+book.getIsbn()+ " borrado con exito";
            }
            return "El libro no existe";
        }catch(Exception e){
            em.getTransaction().rollback();
            return "Error al borrar el libro";
        }
    }
}
