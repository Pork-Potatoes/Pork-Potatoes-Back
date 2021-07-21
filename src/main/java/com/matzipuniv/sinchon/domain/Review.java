package com.matzipuniv.sinchon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNum;

    @ManyToOne
    @JoinColumn(name = "restaurant")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "content")
    private String content;

    @Column
    private Double score;

    @Column
    private Boolean anonymousFlag;

    @Column(name = "menu_name")
    private String menuName;

    @Column
    private Integer likedCnt;

    @Column
    private Integer report;

    @Column
    private Boolean deleteFlag;

    @Column
    private String tagFood;

    @Column
    private String tagMood;

//
//    @OneToMany(
//            mappedBy = "review",
//            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
//            orphanRemoval = true
//    )
//    private List<Image> image = new ArrayList<>();
//
  @Builder
    public Review(Restaurant restaurant, User user, String content, Double score, Boolean anonymousFlag, String menuName, Integer likedCnt, Integer report,
                  Boolean deleteFlag, String tagFood, String tagMood){
        this.restaurant = restaurant;
        this.user = user;
        this.content = content;
        this.score = score;
        this.anonymousFlag = anonymousFlag;
        this.menuName = menuName;
        this.likedCnt = likedCnt;
        this.report = report;
        this.deleteFlag = deleteFlag;
        this.tagFood = tagFood;
        this.tagMood = tagMood;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
//
//    public void addImage(Image image){
//        this.image.add(image);
//
//        if(image.getReview() != this){
//            image.setReview(this);
//        }
//    }
//
//    public void setImage(List<Image> image){
//        this.image = image;
//    }


}

