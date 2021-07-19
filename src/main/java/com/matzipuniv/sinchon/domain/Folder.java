package com.matzipuniv.sinchon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="folder")
public class Folder extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long folderNum;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Integer pinnedCnt;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Column
    private Boolean deleteFlag;

    @Builder
    public Folder(String title, String description, Integer pinnedCnt, User user, Boolean deleteFlag){
        this.title = title;
        this.description = description;
        this.pinnedCnt = pinnedCnt;
        this.user = user;
        this.deleteFlag = deleteFlag;
    }

    public void updateDeleteFlag(Boolean flag){
        this.deleteFlag = flag;
    }
}
