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
import cimr.souscription.repository.CategorieRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api( description="API pour les opérations sur les categories de produits.")
@RequestMapping("/contrat-groupe")
public class CategorieController {
 
	
	@Autowired
    CategorieRepository repository;
	

	@ApiOperation(value = "rechercher toutes les categories de produit")
	@GetMapping("/categories")
	  List<Category> all() { 
	    return repository.findAll();
	 }
	
	@ApiOperation(value = "creer une categorie de produit")
	@PostMapping (value="/addCategorie")
	  public ResponseEntity<Category> newCategorie( @RequestBody Category category) {
		
		Category createdCategorie = repository.save(category);
		
		
		if(createdCategorie==null) {
			return ResponseEntity.noContent().build();
		}else {
			URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("{id}")
					.buildAndExpand(createdCategorie.getId())
					.toUri();
	
			return ResponseEntity.created(uri)
			          .body(createdCategorie);
			
		}
		
	  }
	 @ApiOperation(value = "rechercher une categorie de produits")
	 @GetMapping("/categorie/{id}")
	    public Optional<Category> read(@PathVariable int id) {
	        return repository.findById(id);
	    }

	 
	 
	 @ApiOperation(value = "modifier une categorie de produit")
	 @PutMapping("/updateCategorie/{id}")
	 public ResponseEntity<Category> saveResource(@RequestBody Category categ,
	   @PathVariable("id") int id) {
		 Optional<Category> category = repository.findById(id); 
		 category.get().setNumCategorie(categ.getNumCategorie()); 
		 category.get().setCategorie(categ.getCategorie()); 
		 category.get().setPartsalariale(categ.getPartsalariale()); 
		 category.get().setPartpatronale(categ.getPartpatronale()); 
		 category.get().setMassesalariale(categ.getMassesalariale());  
		 category.get().setEffectif(categ.getEffectif()); 
		 category.get().setOptionX(categ.getOptionX());
	     Category categUpdated = repository.save(category.get());
	      return  ResponseEntity.ok(categUpdated);
	 }

	 @ApiOperation(value = "supprimer une categorie de produits")
	 @DeleteMapping(value = "/deleteCategorie/{id}")
	    public ResponseEntity<Integer> deleteCategory(@PathVariable int id) {

	        Optional<Category> categDel = repository.findById(id);
	        
	        if (categDel==null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        repository.delete(categDel.get());  
	        return new ResponseEntity<>(id, HttpStatus.OK);
	    }
	    

	
}
