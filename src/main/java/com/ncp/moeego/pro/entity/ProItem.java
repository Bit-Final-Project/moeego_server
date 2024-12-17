package com.ncp.moeego.pro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ncp.moeego.category.entity.SubCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "pro_item")
@Data
public class ProItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_item_no")
    private Long proItemNo;

	@ManyToOne
	@JoinColumn(name = "pro_no")
	@JsonIgnore
	@ToString.Exclude
	private Pro pro;

	@ManyToOne
	@JoinColumn(name = "sub_cate_no")
	private SubCategory subCategory;

	@Column(name = "subject")
	private String subject;

	@Column(name = "content")
	private String content;

	@Column(name = "price")
	private Long price;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private ItemStatus itemStatus;


	/*
	@OneToOne // 해당 고수의 카테고리 전용 게시판이 존재함
    @JoinColumn(name = "main_cate_no", nullable = false)
    private MainCategory mainCateNo;

	@OneToOne // 해당 고수의 카테고리 전용 게시판이 존재함
	@JoinColumn(name = "sub_cate_no", nullable = false)
	private SubCategory subCateNo;

	@ManyToOne // 고수 게시판에 사용자는 여러개의 게시글을 작성할수있음
    @JoinColumn(name = "member_no", nullable = false)
    private Member memberNo;
    */

}