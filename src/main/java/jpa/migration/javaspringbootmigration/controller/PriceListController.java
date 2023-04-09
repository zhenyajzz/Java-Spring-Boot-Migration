package jpa.migration.javaspringbootmigration.controller;

import jpa.migration.javaspringbootmigration.entity.PriceList;
import jpa.migration.javaspringbootmigration.entity.PriceListVersion;
import jpa.migration.javaspringbootmigration.repository.PriceListRepository;
import jpa.migration.javaspringbootmigration.repository.PriceListVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/service/PriceList")
public class PriceListController {

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private PriceListVersionRepository priceListVersionRepository;

    @GetMapping
    public ResponseEntity<List<PriceList>> getAllPriceLists() {
        return ResponseEntity.ok(priceListRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceList> getPriceListById(@PathVariable String id) {
        Optional<PriceList> priceList = priceListRepository.findById(id);

        if (priceList.isPresent()) {
            return ResponseEntity.ok(priceList.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<PriceList> createPriceList(@RequestBody PriceList priceList) {
        PriceList savedPriceList = priceListRepository.save(priceList);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPriceList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriceList> updatePriceList(@PathVariable String id, @RequestBody PriceList priceList) {
        Optional<PriceList> existingPriceList = priceListRepository.findById(id);
        if (existingPriceList.isPresent()) {
            PriceList updatedPriceList = existingPriceList.get();
            updatedPriceList.setCode(priceList.getCode());
            updatedPriceList.setDescription(priceList.getDescription());
            updatedPriceList.setIsActive(priceList.getIsActive());
            priceListRepository.save(updatedPriceList);
            return ResponseEntity.ok(updatedPriceList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePriceList(@PathVariable String id) {
        if (priceListRepository.existsById(id)) {
            priceListRepository.deleteById(id);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/alternative")
    public ResponseEntity<PriceList> savePriceList(@RequestParam("code") String code,
                                                   @RequestParam("description") String description,
                                                   @RequestParam("id") String idPriceList) {


        PriceList priceList;
        boolean existing = priceListRepository.existsById(idPriceList);
        boolean isActive = false;

        if (existing) {
            List<PriceListVersion> activeVersions = priceListVersionRepository.findAllByPriceListCodeAndIsActive(code, true);
            isActive = !activeVersions.isEmpty();
            priceList = priceListRepository.findById(idPriceList).orElse(null);
        } else {
            isActive = true;
            priceList = new PriceList();
        }
        if (priceList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        priceList.setCode(code);
        priceList.setDescription(description);
        priceList.setIsActive(isActive);

        PriceList savedPriceList = priceListRepository.save(priceList);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPriceList);
    }
}