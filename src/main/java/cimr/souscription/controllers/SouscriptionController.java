package cimr.souscription.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cimr.souscription.entity.Category;
import cimr.souscription.entity.Delegataire;
import cimr.souscription.entity.Subscription;
import cimr.souscription.repository.CategorieRepository;
import cimr.souscription.repository.DelegataireRepository;
import cimr.souscription.repository.SouscriptionRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Delegate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Api( description="API pour les opérations sur la souscription.")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/contrat-groupe")
public class SouscriptionController {
 
	@Autowired
	SouscriptionRepository subsRepository;
	
	@Autowired
	DelegataireRepository delegRepository;
	
	@Autowired
	CategorieRepository categRepository;
	
	

	@ApiOperation(value = "rechercher toutes les souscriptions")
	@GetMapping("/souscriptions")
	  List<Subscription> all() { 
	    return subsRepository.findAll();
	  }
	
	
	@ApiOperation(value = "creer une nouvelle souscription")
	@PostMapping("/addSouscription") 
	public ResponseEntity<Subscription>  newSubscription(@RequestBody Subscription souscription) {
		
	
	  if (souscription!=null) {
		  
		  //categorie
		  Optional<Category> categorie = categRepository.findById(souscription.getCategory().getId());
		  souscription.setCategory(categorie.get());
		  
		  //gestionnaire-compte
		  Optional<Delegataire> delegataire = delegRepository.findById(souscription.getDelegataire().getId());
		  souscription.setDelegataire(delegataire.get());
		  
		  
		  //save adherents
		  Subscription subscription = subsRepository.save(souscription);		
		  
		  URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("{id}")
					.buildAndExpand(subscription.getId())
					.toUri();
	
			return ResponseEntity.created(uri)
			          .body(subscription);
		  
	  }else {
		  return ResponseEntity.noContent().build();
	  }
	  
		
	}

     @ApiOperation(value = "rechercher une souscription")
	 @GetMapping("/souscription/{id}")
	    public Optional<Subscription> searchSubcription(@PathVariable int id) {
	        return subsRepository.findById(id);
	    }
	
     @ApiOperation(value = "modifier souscription")
	 @PutMapping("/updateSouscription/{id}")
	 public ResponseEntity<Subscription> updateAdherent(@RequestBody Subscription souscription,
	   @PathVariable("id") int id) {
		 Optional<Subscription> subscriptionToUpdate = subsRepository.findById(id); 
		 
		 subscriptionToUpdate.get().setTypeContrat(souscription.getTypeContrat());
		 subscriptionToUpdate.get().setModePaiement(souscription.getModePaiement());
		 subscriptionToUpdate.get().setBanque(souscription.getBanque());
		 subscriptionToUpdate.get().setNumCompte(souscription.getNumCompte());
		 subscriptionToUpdate.get().setFichierRIB(souscription.getFichierRIB());
			
		 
		//categorie
		  Optional<Category> categoryToUpdate = categRepository.findById(souscription.getCategory().getId());
		  subscriptionToUpdate.get().setCategory(categoryToUpdate.get());
		  
		//delegataire
		  Optional<Delegataire> delegataireToUpdate = delegRepository.findById(souscription.getDelegataire().getId());
		  subscriptionToUpdate.get().setDelegataire(delegataireToUpdate.get()); 
		  
		  
		   Subscription subscriptionUpdated = subsRepository.save(subscriptionToUpdate.get());
	      return  ResponseEntity.ok(subscriptionUpdated);
	 }

     @ApiOperation(value = "supprimer une souscription")
	 @DeleteMapping(value = "/deleteSouscription/{id}")
	    public ResponseEntity<Integer> deleteAdherent(@PathVariable int id) {

	        Optional<Subscription> subscriptionToDel = subsRepository.findById(id);
	        
	        if (subscriptionToDel==null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        subsRepository.delete(subscriptionToDel.get());  
	        return new ResponseEntity<>(id, HttpStatus.OK);
	    }

	
}
