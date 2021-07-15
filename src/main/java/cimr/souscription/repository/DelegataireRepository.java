package cimr.souscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cimr.souscription.entity.Delegataire;

@Repository
public interface DelegataireRepository extends JpaRepository<Delegataire, Integer> {

}
