package com.lkj.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
//@JsonIgnoreProperties(value = {"password", "ssn"})
//@JsonFilter -> Filter ID를 문자열로 지정, 해당 어노테이션을 사용하면 무조건 FilterProvider 와 해당 ID를 처리하는 필터를 제공해야 함
//@JsonFilter("UserInfo")
@ApiModel(description = "사용자 상세 정보를 위한 객체") //swagger
@Entity

public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 2, message = "Name 은 2글자 이상 입력해 주세요.")
    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요")
    private String name;

    @Past   //미래 데이터를 쓸 수 없고 과거 데이터만
    @ApiModelProperty(notes = "사용자 등록일을 입력해주세요.")
    private Date joinDate;

    //@JsonIgnore
    @ApiModelProperty(notes = "사용자 패스워드 입력해주세요.")
    private String password;

    //@JsonIgnore
    @ApiModelProperty(notes = "사용자 주민번호를 입력해주세요.")
    private String ssn;

    @OneToMany(mappedBy = "user")   //양방향 연관관계에서 주인이 아닌쪽, 조회를 위한 JPQL 이나 객체 그래프 탐색할 때 사용
    private List<Post> posts;

    public User(Integer id, @Size(min = 2, message = "Name 은 2글자 이상 입력해 주세요.") String name, @Past Date joinDate, String password, String ssn) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
    }
}
