package jpa.migration.javaspringbootmigration.controller;

import jpa.migration.javaspringbootmigration.entity.PriceListVersion;
import jpa.migration.javaspringbootmigration.repository.PriceListVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/service/PriceListVersion")
public class PriceListVersionController {

    @Autowired
    private PriceListVersionRepository priceListVersionRepository;

    @GetMapping
    public ResponseEntity<List<PriceListVersion>> getAllPriceListVersions() {
        return ResponseEntity.ok(priceListVersionRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceListVersion> getPriceListVersionById(@PathVariable String id) {
        Optional<PriceListVersion> priceListVersion = priceListVersionRepository.findById(id);
        if (priceListVersion.isPresent()) {
            return ResponseEntity.ok(priceListVersion.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<PriceListVersion> createPriceListVersion(@RequestBody PriceListVersion priceListVersion) {
        PriceListVersion savedPriceListVersion = priceListVersionRepository.save(priceListVersion);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPriceListVersion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriceListVersion> updatePriceListVersion(@PathVariable String id, @RequestBody PriceListVersion priceListVersion) {
        Optional<PriceListVersion> existingPriceListVersion = priceListVersionRepository.findById(id);
        if (existingPriceListVersion.isPresent()) {
            PriceListVersion updatedPriceListVersion = existingPriceListVersion.get();
            updatedPriceListVersion.setVersion(priceListVersion.getVersion());
            updatedPriceListVersion.setActiveFrom(priceListVersion.getActiveFrom());
            updatedPriceListVersion.setActiveTo(priceListVersion.getActiveTo());
            updatedPriceListVersion.setPriceList(priceListVersion.getPriceList());
            updatedPriceListVersion.setIsActive(priceListVersion.getIsActive());
            priceListVersionRepository.save(updatedPriceListVersion);
            return ResponseEntity.ok(updatedPriceListVersion);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePriceListVersion(@PathVariable String id) {
        if (priceListVersionRepository.existsById(id)) {
            priceListVersionRepository.deleteById(id);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}