package org.example.humanity.userFunctionality.items.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    @Enumerated(EnumType.STRING)
    private ItemCategory category;
    private String description;
    private String phoneNumber;
    private String email;
    private String location;
    @Enumerated(EnumType.STRING)
    private YearsUsed yearsUsed;
    private String image;

}
