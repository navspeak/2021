package com.pluralsight.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="application_id")
    private Long id;

    @Column(name = "app_name", nullable = false)
    @NonNull private String name;

    @NonNull private String owner;

    @Column(length = 2000)
    @NonNull private String description;


}
