package com.matzipuniv.sinchon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="pin")
public class Pin extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pinNum;

    @ManyToOne
    @JoinColumn(name = "folder")
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Builder
    public Pin(Long pinNum, Folder folder, User user){
        this.pinNum = pinNum;
        this.folder = folder;
        this.user = user;
    }
}
