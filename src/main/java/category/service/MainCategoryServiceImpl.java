package category.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import category.bean.MainCategoryDTO;
import category.repository.MainCategoryRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MainCategoryServiceImpl implements MainCategoryService{


	private final MainCategoryRepository mainCategoryRepository;
	
	@Override
	public List<MainCategoryDTO> getMainCategoryList() {
	
		return mainCategoryRepository.findAll()
                .stream()
                .map(mainCategory -> new MainCategoryDTO(
                    mainCategory.getMainCateNo(),
                    mainCategory.getMainCateName()
                ))
                .collect(Collectors.toList());
	}

}
