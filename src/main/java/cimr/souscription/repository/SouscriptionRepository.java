package cimr.souscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cimr.souscription.entity.Subscription;

@Repository
public interface SouscriptionRepository extends JpaRepository<Subscription, Integer> {

	
}
