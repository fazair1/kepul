package com.juaracoding.kepul.service;

import com.juaracoding.kepul.config.OtherConfig;
import com.juaracoding.kepul.core.IService;
import com.juaracoding.kepul.dto.response.RespProductCategoryDTO;
import com.juaracoding.kepul.dto.response.RespProductDTO;
import com.juaracoding.kepul.dto.validation.ValProductCategoryDTO;
import com.juaracoding.kepul.dto.validation.ValProductDTO;
import com.juaracoding.kepul.handler.GlobalResponse;
import com.juaracoding.kepul.model.Product;
import com.juaracoding.kepul.model.ProductCategory;
import com.juaracoding.kepul.repositories.ProductCategoryRepo;
import com.juaracoding.kepul.repositories.ProductRepo;
import com.juaracoding.kepul.security.RequestCapture;
import com.juaracoding.kepul.util.GlobalFunction;
import com.juaracoding.kepul.util.LoggingFile;
import com.juaracoding.kepul.util.TransformPagination;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *  Platform Code  - KPL
 *  Modul Code - 02
 *  FV - FE
 */

@Service
@Transactional
public class ProductService implements IService<Product> {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductCategoryRepo productCategoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;

    @Override
    public ResponseEntity<Object> save(Product product, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        if (product == null) {
            return GlobalResponse.dataTidakValid("KPL02FV001",request);
        }

        try {
            Optional<ProductCategory> optionalProductCategory = productCategoryRepo.findById(product.getProductCategory().getId());
            if (!optionalProductCategory.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("KPL02FV002",request);
            }

            product.setCreatedBy(Long.parseLong(mapToken.get("userId").toString()));
            productRepo.save(product);
        }catch (Exception e) {
            LoggingFile.logException("ProductService","save(Product product, HttpServletRequest request) -- Line 48 "+ RequestCapture.allRequest(request),e, OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDisimpan("KPL02FE003",request);

        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Product product, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        if (product == null) {
            return GlobalResponse.dataTidakValid("KPL02FV011",request);
        }

        try {
            Optional<Product> optionalProduct = productRepo.findById(id);
            if (!optionalProduct.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("KPL02FV012",request);
            }

            Optional<ProductCategory> optionalProductCategory = productCategoryRepo.findById(product.getProductCategory().getId());
            if (!optionalProductCategory.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("KPL02FV013",request);
            }

            LocalDateTime now = LocalDateTime.now();

            Product nextProduct = optionalProduct.get();
            nextProduct.setModifiedBy(Long.parseLong(mapToken.get("userId").toString()));
            nextProduct.setModifiedDate(now);
            nextProduct.setNama(product.getNama());
            nextProduct.setDeskripsi(product.getDeskripsi());
            nextProduct.setProductCategory(product.getProductCategory());

        }catch (Exception e) {
            LoggingFile.logException("ProductCategoryService","update(Long id, ProductCategory productCategory, HttpServletRequest request) -- Line 102 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDiubah("KPL02FE014",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        try {
            Optional<Product> optionalProduct = productRepo.findById(id);
            if (!optionalProduct.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("KPL02FV021",request);
            }
            LocalDateTime now = LocalDateTime.now();

            Product nextProduct = optionalProduct.get();
            nextProduct.setModifiedBy(Long.parseLong(mapToken.get("userId").toString()));
            nextProduct.setModifiedDate(now);
            nextProduct.setDeleted(true);
        }catch (Exception e) {
            LoggingFile.logException("ProductCategoryService","delete(Long id, HttpServletRequest request) -- Line 117 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDihapus("KPL02FE022",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Product> page = null;
        List<Product> list = null;
        page = productRepo.findAllByIsDeletedFalse(pageable);
        list = page.getContent();
        List<RespProductDTO> lt = convertToRespProductDTO(list);

        return GlobalResponse.dataDitemukan(transformPagination.transformPagination(lt,page,null,null),
                request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Optional<Product> optionalProduct = null;
        try {
            optionalProduct = productRepo.findById(id);
            if (!optionalProduct.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("KPL02FV041",request);
            }
        }catch (Exception e) {
            LoggingFile.logException("ProductCategoryService","findById(Long id, HttpServletRequest request) -- Line 144 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.terjadiKesalahan("KPL02FE042",request);
        }
        return GlobalResponse.dataDitemukan(modelMapper.map(optionalProduct.get(),RespProductCategoryDTO.class),request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<Product> page = null;
        List<Product> list = null;
        switch (columnName) {
            case "nama": page = productRepo.findByNamaContainsIgnoreCase(value,pageable);break;
            case "deskripsi": page = productRepo.findByDeskripsiContainsIgnoreCase(value,pageable);break;
            case "category": page = productRepo.cariCategory(value,pageable);break;
            default: page = productRepo.findAll(pageable);
        }
        list = page.getContent();
        List<RespProductDTO> lt = convertToRespProductDTO(list);

        return GlobalResponse.dataDitemukan(transformPagination.transformPagination(lt,page,columnName,value),
                request);
    }

    public Product convertToEntity (ValProductDTO valProductDTO) {
//        GroupMenu groupMenu = new GroupMenu();
//        groupMenu.setNama(valGroupMenuDTO.getNama());
//        groupMenu.setDeskripsi(valGroupMenuDTO.getDeskripsi());
        Product product = modelMapper.map(valProductDTO, Product.class);
        return product;
    }

    public List<RespProductDTO> convertToRespProductDTO (List<Product> products) {
//        List<RespGroupMenuDTO> respGroupMenuDTOList = new ArrayList<>();
//
//        for (GroupMenu groupMenu: groupMenus) {
//            RespGroupMenuDTO respGroupMenuDTO = new RespGroupMenuDTO();
//            respGroupMenuDTO.setId(groupMenu.getId());
//            respGroupMenuDTO.setNama(groupMenu.getNama());
//            respGroupMenuDTO.setDeskripsi(groupMenu.getDeskripsi());
//            respGroupMenuDTOList.add(respGroupMenuDTO);
//        }
        List<RespProductDTO> respProductDTOList = modelMapper.map(products, new TypeToken<List<RespProductDTO>>() {}.getType());
        return respProductDTOList;
    }

}
