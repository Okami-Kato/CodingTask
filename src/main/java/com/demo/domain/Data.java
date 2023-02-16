package com.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "data")
@Getter
@Setter
public class Data {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "updated_timestamp")
    private LocalDateTime updatedTimestamp;
}
