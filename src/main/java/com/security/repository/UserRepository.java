package com.security.repository;

import com.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//CRUD 함수를 JpaRepository가 들고 있음
//@Repository라는 어노테이션이 없어도 ioc가 된다
public interface UserRepository extends JpaRepository<User,Integer> {

    //findBy 규칙 -> Username 문법
    //select * from user where username = ?
    public User findByUsername(String username);
}
