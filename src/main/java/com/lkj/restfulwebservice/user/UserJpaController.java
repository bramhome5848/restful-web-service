package com.lkj.restfulwebservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
@RequiredArgsConstructor
public class UserJpaController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) {    //ifPresent() 보다 isEmpty() 추천?
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        //HATEOAS
        EntityModel<User> resource = EntityModel.of(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));

        /**
         * JPA Entity를 JSON으로 변환할 때 발생할 수 있는 문제점
         * 1. Entity를 JSON으로 변환을 했더니 에러가 발생
         * - 엔티티 간의 관계 설정에 의해 서로 참조에 의해 무한루프가 발생
         * - toMany -> @JsonBackReference, toOne -> @JsonManagedReference
         * 2. Fetch Lazy까지 조회
         * - Json serialize 과정에서 엔티티의 모든 필드들을 맵핑 하려고 하면서 Fetch Lazy 설정한 필드들까지 추가로 조회를 해주는 문제가 발생
         * - @JsonBackReference, @JsonManagedReference 는 무한 참조만 해결해주기 때문에
         * - @JsonIgnore 를 사용하거나, DTO 를 통해서 데이터 전달하는 방식이 필요
         * 3. 참고
         * - json 변환을 위해서는 getter 가 반드시 필요
         * - @JsonIgnore 보다는 Dto 를 통한 데이터 전달이 효과적
         * - getList() 에서는 쿼리 실행되지 않으며, entity 의 내용에 대한 get() 함수 또는 list 의 size() 호출시에 쿼리 실행 -> lazy
         */
        return resource;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedUser);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        userRepository.deleteById(id);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable Integer id) {

        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) {    //ifPresent() 보다 isEmpty() 추천?
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable Integer id, @RequestBody Post post) {

        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedPost);
    }
}
