package com.ncp.moeego.favorite.repository;

import com.ncp.moeego.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @Query("Select f.proNo.proNo FROM Favorite f where f.memberNo.memberNo = :memberNo")
    List<Long> findProNosByMemberNo(@Param("memberNo") Long memberNo);
}