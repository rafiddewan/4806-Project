package survey.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import survey.model.NumericalRangeQuestion;

@RepositoryRestResource(collectionResourceRel = "numericalrangequestions", path = "numericalrangequestions")
public interface NumericalRangeQuestionRepository extends PagingAndSortingRepository<NumericalRangeQuestion, Integer> {
    NumericalRangeQuestion findById(@Param("id") int id);
}




