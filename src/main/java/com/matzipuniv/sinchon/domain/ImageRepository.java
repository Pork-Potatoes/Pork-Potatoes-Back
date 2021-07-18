package com.matzipuniv.sinchon.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByReviewReviewNum(Long reviewNum);
}
