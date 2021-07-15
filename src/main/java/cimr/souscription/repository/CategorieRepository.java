package cimr.souscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cimr.souscription.entity.Category;

@Repository
public interface CategorieRepository  extends JpaRepository<Category, Integer>{

}
