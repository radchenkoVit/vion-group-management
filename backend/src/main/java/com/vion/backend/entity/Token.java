package com.vion.backend.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@EqualsAndHashCode(of = "token")
public class Token {
    @Id
    @Column(nullable = false)
    private String token;
}
