package com.ncp.moeego.favorite.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import com.ncp.moeego.member.entity.Member;
import com.ncp.moeego.pro.bean.Pro;

@Entity
@Data
public class Favorite {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long favorite;

	@ManyToOne // 여러개의 즐겨찾기를 한 사용자가 할수있음
    @JoinColumn(name="member_no", nullable = false)
    private Member memberNo;
	
	@ManyToOne // 즐겨찾기에 여러명의 고수가 담길수있음
    @JoinColumn(name = "pro_no", nullable = false)
    private Pro proNo;
	
}
