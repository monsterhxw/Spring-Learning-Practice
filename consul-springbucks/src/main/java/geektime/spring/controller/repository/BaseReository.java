package geektime.spring.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseReository<T, R> extends JpaRepository<T, R> {

}
