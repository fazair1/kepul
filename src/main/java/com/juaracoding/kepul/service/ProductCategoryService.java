package com.juaracoding.kepul.service;

import com.juaracoding.kepul.core.IService;
import com.juaracoding.kepul.dto.response.RespProductCategoryDTO;
import com.juaracoding.kepul.dto.validation.ValProductCategoryDTO;
import com.juaracoding.kepul.handler.GlobalResponse;
import com.juaracoding.kepul.model.Product;
import com.juaracoding.kepul.model.ProductCategory;
import com.juaracoding.kepul.repositories.ProductCategoryRepo;
import com.juaracoding.kepul.repositories.ProductRepo;
import com.juaracoding.kepul.util.TransformPagination;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *  Platform Code  - KPL
 *  Modul Code - 01
 *  FV - FE
 */

@Service
@Transactional
public class ProductCategoryService implements IService<ProductCategory> {

    @Autowired
    private ProductCategoryRepo productCategoryRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;

    @Override
    public ResponseEntity<Object> save(ProductCategory productCategory, HttpServletRequest request) {
//        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        try {
//            groupMenu.setCreatedBy(Long.parseLong(mapToken.get("userId").toString()));
            productCategory.setCreatedBy(1L);
            productCategoryRepo.save(productCategory);
        }catch (Exception e) {
//            LoggingFile.logException("GroupMenuService","save(GroupMenu groupMenu, HttpServletRequest request) -- Line 59 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDisimpan("KPL01FE001",request);

        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, ProductCategory productCategory, HttpServletRequest request) {
//        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        if (productCategory == null) {
            return GlobalResponse.dataTidakValid("KPL01FV011",request);
        }

        try {
            Optional<ProductCategory> optionalProductCategory = productCategoryRepo.findById(id);
            if (!optionalProductCategory.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("KPL01FV012",request);
            }

            ProductCategory nextProductCategory = optionalProductCategory.get();
//            nextProductCategory.setModifiedBy(Long.parseLong(mapToken.get("userId").toString()));
            nextProductCategory.setModifiedBy(1L);
            nextProductCategory.setNama(productCategory.getNama());

        }catch (Exception e) {
//            LoggingFile.logException("GroupMenuService","update(Long id, GroupMenu groupMenu, HttpServletRequest request) -- Line 81 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDiubah("KPL01FE011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            Optional<ProductCategory> optionalProductCategory = productCategoryRepo.findById(id);
            if (!optionalProductCategory.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("KPL01FV021",request);
            }
            productCategoryRepo.deleteById(id);
        }catch (Exception e) {
//            LoggingFile.logException("GroupMenuService","delete(Long id, HttpServletRequest request) -- Line 95 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDihapus("KPL01FE021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<ProductCategory> page = null;
        List<ProductCategory> list = null;
        page = productCategoryRepo.findAll(pageable);
        list = page.getContent();
        List<RespProductCategoryDTO> lt = convertToRespProductCategoryDTO(list);

        return GlobalResponse.dataDitemukan(transformPagination.transformPagination(lt,page,null,null),
                request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        return null;
    }

    public List<RespProductCategoryDTO> convertToRespProductCategoryDTO (List<ProductCategory> productCategories) {
//        List<RespGroupMenuDTO> respGroupMenuDTOList = new ArrayList<>();
//
//        for (GroupMenu groupMenu: groupMenus) {
//            RespGroupMenuDTO respGroupMenuDTO = new RespGroupMenuDTO();
//            respGroupMenuDTO.setId(groupMenu.getId());
//            respGroupMenuDTO.setNama(groupMenu.getNama());
//            respGroupMenuDTO.setDeskripsi(groupMenu.getDeskripsi());
//            respGroupMenuDTOList.add(respGroupMenuDTO);
//        }
        List<RespProductCategoryDTO> respProductCategoryDTOList = modelMapper.map(productCategories, new TypeToken<List<RespProductCategoryDTO>>() {}.getType());
        return respProductCategoryDTOList;
    }

    public ProductCategory convertToEntity (ValProductCategoryDTO valProductCategoryDTO) {
//        GroupMenu groupMenu = new GroupMenu();
//        groupMenu.setNama(valGroupMenuDTO.getNama());
//        groupMenu.setDeskripsi(valGroupMenuDTO.getDeskripsi());
        ProductCategory productCategory = modelMapper.map(valProductCategoryDTO, ProductCategory.class);
        return productCategory;
    }

}
