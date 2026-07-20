package velaire.ecommmerce.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "formats")
@Data
public class Format {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
}
