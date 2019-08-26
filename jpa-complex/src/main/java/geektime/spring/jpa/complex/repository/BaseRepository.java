package geektime.spring.jpa.complex.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseRepository<T, Long> extends PagingAndSortingRepository<T, Long> {

    List<T> findTop3ByOrderByUpdateTimeDescIdAsc();
}
