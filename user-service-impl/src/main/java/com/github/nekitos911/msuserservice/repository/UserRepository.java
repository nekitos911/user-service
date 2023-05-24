package com.github.nekitos911.msuserservice.repository;

import com.github.nekitos911.msuserservice.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("from UserEntity u where " +
            "(:firstName is null or u.firstName = :firstName) and " +
            "(:lastName is null or u.lastName = :lastName) and " +
            "(:middleName is null or u.middleName = :middleName) and " +
            "(:phone is null or u.phone = :phone) and " +
            "(:email is null or u.email = :email)")
    List<UserEntity> search(@Param("firstName") String firstName,
                            @Param("lastName") String lastName,
                            @Param("middleName") String middleName,
                            @Param("phone") String phone,
                            @Param("email") String email);
}
