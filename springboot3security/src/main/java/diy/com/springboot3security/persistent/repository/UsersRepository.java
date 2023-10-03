package diy.com.springboot3security.persistent.repository;


import java.util.List;
import java.util.Optional;

import diy.com.springboot3security.persistent.entity.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    //the EntityGraph is sayings that when Users are fetched, the associated Authorities will be fetched as well
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"authorities"})
    Optional<Users> findByUsername(String username);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"authorities"})
    List<Users> findAll();
}
