package com.projectBackend.project.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "merch")
public class Merch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;




}
