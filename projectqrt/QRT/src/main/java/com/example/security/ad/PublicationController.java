package com.example.security.ad;

import com.example.security.user.User;
import com.example.security.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ads")
public class PublicationController {
    @Autowired
    private PublicationService publicationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Publication>> getAllAds() {
        List<Publication> publications = publicationService.findAll();
        return ResponseEntity.ok(publications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publication> getAdById(@PathVariable Long id) {
        Optional<Publication> ad = publicationService.findById(id);
        return ad.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }



    @PostMapping("/{userId}")
    public ResponseEntity<Publication> createAd(@PathVariable Long userId, @RequestBody Publication publication) {
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        publication.setUser(user);

        Publication createdPublication = publicationService.save(publication);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPublication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdById(@PathVariable Long id) {
        Optional<Publication> ad = publicationService.findById(id);
        if (ad.isPresent()) {
            publicationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publication> updateAd(@PathVariable Long id, @RequestBody Publication updatedPublication) {
        Optional<Publication> existingAdOptional = publicationService.findById(id);
        if (existingAdOptional.isPresent()) {
            Publication existingPublication = existingAdOptional.get();
            existingPublication.setTitle(updatedPublication.getTitle());
            existingPublication.setDescription(updatedPublication.getDescription());

            Publication savedPublication = publicationService.save(existingPublication);
            return ResponseEntity.ok(savedPublication);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sorted/asc")
    public ResponseEntity<List<Publication>> getAdsSortedByTitleAsc() {
        List<Publication> publications = publicationService.findAllSortedByTitleAsc();
        return ResponseEntity.ok(publications);
    }

    @GetMapping("/sorted/desc")
    public ResponseEntity<List<Publication>> getAdsSortedByTitleDesc() {
        List<Publication> publications = publicationService.findAllSortedByTitleDesc();
        return ResponseEntity.ok(publications);
    }

    @PostMapping("/cheaper")
    public ResponseEntity<List<Publication>> getAdsCheaperThan(@RequestBody PriceFilterDTO filter) {
        List<Publication> publications = publicationService.findAdsCheaperThan(filter.getPrice());
        return ResponseEntity.ok(publications);
    }

    @PostMapping("/expensive")
    public ResponseEntity<List<Publication>> getAdsMoreExpensiveThan(@RequestBody PriceFilterDTO filter) {
        List<Publication> publications = publicationService.findAdsMoreExpensiveThan(filter.getPrice());
        return ResponseEntity.ok(publications);
    }





}

