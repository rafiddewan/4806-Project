package survey.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import survey.model.Survey;

@RepositoryRestResource(collectionResourceRel = "surveys", path = "surveys")
public interface SurveyRepository extends PagingAndSortingRepository<Survey, Integer> {
    Survey findById(@Param("id") int id);
    Survey findTopByOrderByIdDesc();
}




