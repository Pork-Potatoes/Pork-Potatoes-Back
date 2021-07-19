package com.matzipuniv.sinchon.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionRepository extends JpaRepository<Addition, Long> {
    List<Restaurant> findByFolderNumAndDeleteFlagFalse(Long folderNum);
}
