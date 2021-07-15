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

import cimr.souscription.entity.Delegataire;
import cimr.souscription.repository.DelegataireRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api( description="API pour les opérations sur les delegataires.")
@RequestMapping("/contrat-groupe")
public class DelegataireController {

	
	@Autowired
    DelegataireRepository repository;
	

	@ApiOperation(value = "rechercher toutes les delegataires")
	@GetMapping("/delegataires")
	  List<Delegataire> all_() { 
	    return repository.findAll();
	 }
	
	@ApiOperation(value = "creer un delegataire")
	@PostMapping (value="/addDelegataire")
	  public ResponseEntity<Delegataire> newCategorie( @RequestBody Delegataire delegataire) {
		
		Delegataire createdDelegataire = repository.save(delegataire);
		
		
		if(createdDelegataire==null) {
			return ResponseEntity.noContent().build();
		}else {
			URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("{id}")
					.buildAndExpand(createdDelegataire.getId())
					.toUri();
	
			return ResponseEntity.created(uri)
			          .body(createdDelegataire);
			
		}
		
	  }
	 @ApiOperation(value = "rechercher un delegataire")
	 @GetMapping("/delegataire/{id}")
	    public Optional<Delegataire> read(@PathVariable int id) {
	        return repository.findById(id);
	    }

	 
	 
	 @ApiOperation(value = "modifier un delegataire")
	 @PutMapping("/updateDelegataire/{id}")
	 public ResponseEntity<Delegataire> saveResource(@RequestBody Delegataire deleg,
	   @PathVariable("id") int id) {
		 Optional<Delegataire> delegataireOp = repository.findById(id);
		 Delegataire delegataire = delegataireOp.get();
		 delegataire.setNom(deleg.getNom()); 
		 delegataire.setPrenom(deleg.getPrenom());  
		 delegataire.setCin(deleg.getCin()); 
		 delegataire.setNationalite(deleg.getCin()); 
		 delegataire.setFonction(deleg.getFonction());   
		 delegataire.setEmail(deleg.getEmail()); 
		 delegataire.setNumeroMobilegsm(deleg.getNumeroMobilegsm()); 
		 delegataire.setNumeroTelFixe(deleg.getNumeroTelFixe()); 
		 delegataire.setDateDebutValidite(deleg.getDateDebutValidite()); 
		 delegataire.setDateFinValidite(deleg.getDateFinValidite()); 
		 delegataire.setAffilieSalaires(deleg.getAffilieSalaires());
		 delegataire.setReglementEasyPayment(deleg.getReglementEasyPayment());
		 delegataire.setReglementEasyPaymentConjoint(deleg.getReglementEasyPaymentConjoint());
		 delegataire.setLiquidation(deleg.getLiquidation());
		 
	     Delegataire delegUpdated = repository.save(delegataire);
	      return  ResponseEntity.ok(delegUpdated);
	 }

	 @ApiOperation(value = "supprimer un delegataire")
	 @DeleteMapping(value = "/deleteDelegataire/{id}")
	    public ResponseEntity<Integer> deleteDelegataire(@PathVariable int id) {

	        Optional<Delegataire> delegDel = repository.findById(id);
	        
	        if (delegDel==null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        repository.delete(delegDel.get());  
	        return new ResponseEntity<>(id, HttpStatus.OK);
	    }

}
