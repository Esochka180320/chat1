package com.deveducation.chat.repository;



import com.deveducation.chat.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {

    public Group findByName(String name);
}
