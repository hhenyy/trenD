package com.td.TrenD.service;

import com.td.TrenD.dao.CommunityRepository;
import com.td.TrenD.model.CategoryVO;
import com.td.TrenD.model.TrendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    @Autowired
    private CommunityRepository commRepo;

    public List<CategoryVO> findAllCategory(){
        return commRepo.findAllCategory();
    }

    public Page<TrendVO> findCommList(PageRequest pageable){
        return commRepo.findCommList(pageable);
    }

    public Page<TrendVO> findCategoryList(String cateCd, PageRequest pageable){
        return commRepo.findCategoryList(cateCd, pageable);
    }

    public Page<TrendVO> searchCommList(String keyword, String search, PageRequest pageable){
        return commRepo.searchCommList(keyword, search, pageable);
    }
}
