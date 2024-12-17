package com.ncp.moeego.pro.repository;

import com.ncp.moeego.category.entity.SubCategory;
import com.ncp.moeego.pro.dto.ItemDetailResponse;
import com.ncp.moeego.pro.entity.ItemStatus;
import com.ncp.moeego.pro.entity.ProItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProItemRepository extends JpaRepository<ProItem, Long> {

    ProItem findProItemsByPro_ProNoAndSubCategory(Long proProNo, SubCategory subCategory);

    @Query(
            """
                    select new com.ncp.moeego.pro.dto.ItemDetailResponse(
                    p.pro.member.name, p.pro.member.profileImage, p.pro.member.address, p.subCategory.mainCategory.mainCateName, p.subCategory.subCateName, p.subject, p.content, p.price, p.pro.star
                    )
                    from ProItem p
                    where p.proItemNo =:proItemNo
                    """)
    ItemDetailResponse getItemDetails(@Param("proItemNo") Long proItemNo);

    boolean existsByProItemNoAndItemStatus(Long proItemNo, ItemStatus itemStatus);
}
