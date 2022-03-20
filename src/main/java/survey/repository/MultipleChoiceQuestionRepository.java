package survey.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import survey.model.MultipleChoiceQuestion;

@RepositoryRestResource(collectionResourceRel = "multiplechoicequestions", path = "multiplechoicequestions")
public interface MultipleChoiceQuestionRepository extends PagingAndSortingRepository<MultipleChoiceQuestion, Integer> {
    MultipleChoiceQuestion findById(@Param("id") int id);
}




