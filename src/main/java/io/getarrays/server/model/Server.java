package io.getarrays.server.model;

import io.getarrays.server.enumeartion.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import static jakarta.persistence.GenerationType.AUTO;

/**
 * @author Get Arrays (https://www.getarrays.io/)
 * @version 1.0
 * @since 15/11/2023
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(unique=true)
    @NotEmpty(message="ip addresse cannot be empty")
    private String ipAdress;
    private String name;
    private String memory;
    private String type;
    private String imageUrl;
    private Status status; 
    
}
