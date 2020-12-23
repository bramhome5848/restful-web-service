package com.lkj.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(of = "id")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    //User : Post -> 1:N , N쪽이 fk를 관리하기 때문에 연관관계의 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
}
