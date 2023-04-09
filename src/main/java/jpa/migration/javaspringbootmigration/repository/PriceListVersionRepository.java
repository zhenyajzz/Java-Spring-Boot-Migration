package jpa.migration.javaspringbootmigration.repository;

import jpa.migration.javaspringbootmigration.entity.PriceListVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceListVersionRepository extends JpaRepository<PriceListVersion,String > {

    List<PriceListVersion> findAllByPriceListCodeAndIsActive(String priceListCode, boolean isActive);
}