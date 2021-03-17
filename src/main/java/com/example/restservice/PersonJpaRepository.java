package com.example.restservice;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class PersonJpaRepository {

    // connect to the database
    @PersistenceContext
    EntityManager entityManager;

    /*public Person findById(int id) {
        return entityManager.find(Person.class, id);
    }

    public Person update(Person person) {
        return entityManager.merge(person);
    }

    public Person insert(Person person) {
        return entityManager.merge(person);
    }

    public void deleteById(int id) {
        Person person = findById(id);
        entityManager.remove(person);
    }

    public List<Person> findAll() {
        //JPQL query
        TypedQuery<Person> typedQuery = entityManager.createNamedQuery("find_all_persons", Person.class);
        return typedQuery.getResultList();
    }*/
}
