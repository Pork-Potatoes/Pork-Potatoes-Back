package com.matzipuniv.sinchon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "report")
public class Report implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_num", nullable = false)
    private Long reportNum;

    @Column(name = "review", nullable = false)
    private Long review;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "user", nullable = false)
    private Long user;

    @Builder
    public Report(Long review, String description, Long user){
        this.review = review;
        this.description = description;
        this.user = user;
    }

}
