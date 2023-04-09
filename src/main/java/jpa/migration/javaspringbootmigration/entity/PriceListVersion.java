package jpa.migration.javaspringbootmigration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;


@Entity
@Table(name = "price_list_version")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceListVersion {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "version")
    private Integer version;

    @Column(name = "active_from")
    private LocalDate activeFrom;

    @Column(name = "active_to")
    private LocalDate activeTo;

    @ManyToOne
    @JoinColumn(name = "price_list_id")
    private PriceList priceList;

    @Column(name = "is_active")
    private Boolean isActive;

}