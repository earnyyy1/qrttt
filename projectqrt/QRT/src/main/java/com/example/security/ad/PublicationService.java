package com.example.security.ad;

import com.example.security.user.User;
import com.example.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicationService {
    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Publication> findAll() {
        return publicationRepository.findAll();
    }

    public Optional<Publication> findById(Long id) {
        return publicationRepository.findById(id);
    }



    public Publication save(Publication publication) {
        return publicationRepository.save(publication);
    }


    public Optional<User> findByFirstname(String username) {
        return userRepository.findByFirstname(username);
    }


    public Publication updateAd(Publication existingPublication, Publication updatedPublication) {
        existingPublication.setTitle(updatedPublication.getTitle());
        existingPublication.setDescription(updatedPublication.getDescription());
        return publicationRepository.save(existingPublication);
    }

    public void deleteById(Long id) {
        publicationRepository.deleteById(id);
    }

    public List<Publication> findAllSortedByTitleAsc() {
        return findAll().stream()
                .sorted(Comparator.comparing(Publication::getTitle))
                .collect(Collectors.toList());
    }

    public List<Publication> findAllSortedByTitleDesc() {
        return findAll().stream()
                .sorted(Comparator.comparing(Publication::getTitle).reversed())
                .collect(Collectors.toList());
    }

    public List<Publication> findAdsCheaperThan(double price) {
        return publicationRepository.findByPriceLessThan(price);
    }

    public List<Publication> findAdsMoreExpensiveThan(double price) {
        return publicationRepository.findByPriceGreaterThan(price);
    }

}



