package com.td.TrenD.service;
import com.td.TrenD.dao.CommunityRepository;
import com.td.TrenD.model.CategoryVO;
import com.td.TrenD.model.TrendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommunityService {

    private final CommunityRepository commRepo;

    @Autowired
    public CommunityService(CommunityRepository repo) {
        this.commRepo = repo;
    }

    public TrendVO commInsert(TrendVO trendVO){
        return commRepo.save(trendVO);
    }

    public List<CategoryVO> findAllCategory(){
        return commRepo.findAllCategory();
    }

    public List<TrendVO> commList(){
        return commRepo.commList();
    }

    public TrendVO commContent(int trNo){
        return commRepo.commContent(trNo);
    }



}
