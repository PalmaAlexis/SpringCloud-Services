package com.user.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.usercommons.model.entity.User;

@RepositoryRestResource(path = "users") //CRUD IMPORTADO *MENOS CODIGO/MENOS ERRORES*
public interface UserDao extends PagingAndSortingRepository<User, Long> {

	// public User findByNameAndLastName(String name, String lastName);

	@RestResource(path = "findUser")
	public User findByUsername(@Param("user")String username);

	@Query(value = "select u from User u where u.username=?1")
	public User buscarPorUsuario(/* @Param("username") */ String username);
	
	

}
