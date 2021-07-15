package cimr.souscription.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String typeContrat;
	
	private String modePaiement;
	private String banque;
	private String numCompte;
	private String fichierRIB;
	
	 @ManyToOne (cascade = CascadeType.PERSIST) 
	 @JoinColumn(name = "delegataire_id")
	 private Delegataire delegataire;
	 
	 @ManyToOne (cascade = CascadeType.PERSIST) 
	 @JoinColumn(name = "categories_id")
	 private Category category;
	 
	
	
	
	
	
}
