package com.matzipuniv.sinchon.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionRepository extends JpaRepository<Addition, Long> {
    @Query("select a.restaurant from Addition a where a.folderNum=?1 and a.deleteFlag=false")
    List<Restaurant> findByFolderNum(Long folderNum);
}
