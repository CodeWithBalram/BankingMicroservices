package com.ultimate.loans.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@MappedSuperclass
public class BaseEntity
{
    @Column(updatable=false)
    private LocalDateTime createdAt;
    @Column(updatable=false)
    private String creatBy;
    @Column(insertable = false)
    private LocalDateTime updatedAt;
    @Column(insertable = false)
    private String updatedBy;
}
