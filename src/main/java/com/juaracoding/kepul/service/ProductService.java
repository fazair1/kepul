package com.juaracoding.kepul.service;

import com.juaracoding.kepul.config.OtherConfig;
import com.juaracoding.kepul.core.IReport;
import com.juaracoding.kepul.core.IService;
import com.juaracoding.kepul.dto.report.RepProductDTO;
import com.juaracoding.kepul.dto.response.RespProductCategoryDTO;
import com.juaracoding.kepul.dto.response.RespProductDTO;
import com.juaracoding.kepul.dto.validation.ValProductDTO;
import com.juaracoding.kepul.handler.GlobalResponse;
import com.juaracoding.kepul.model.Product;
import com.juaracoding.kepul.model.ProductCategory;
import com.juaracoding.kepul.repositories.ProductCategoryRepo;
import com.juaracoding.kepul.repositories.ProductRepo;
import com.juaracoding.kepul.security.RequestCapture;
import com.juaracoding.kepul.util.GlobalFunction;
import com.juaracoding.kepul.util.LoggingFile;
import com.juaracoding.kepul.util.PdfGenerator;
import com.juaracoding.kepul.util.TransformPagination;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.*;

/**
 *  Platform Code  - KPL
 *  Modul Code - 02
 *  FV - FE
 */

@Service
@Transactional
public class ProductService implements IService<Product>, IReport<Product> {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductCategoryRepo productCategoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;

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
            LoggingFile.logException("ProductService","save(Product product, HttpServletRequest request) -- Line 71 "+ RequestCapture.allRequest(request),e, OtherConfig.getEnableLog());
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
            LoggingFile.logException("ProductService","update(Long id, Product product, HttpServletRequest request) -- Line 107 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
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
            LoggingFile.logException("ProductService","delete(Long id, HttpServletRequest request) -- Line 129 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
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

    public ResponseEntity<Object> allProduct(HttpServletRequest request){
        List<Product> list = null;
        list = productRepo.findAllByIsDeletedFalse();
        List<RepProductDTO> lt = convertToRepProductDTO(list);
        return GlobalResponse.dataDitemukan(lt,
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
            LoggingFile.logException("ProductService","findById(Long id, HttpServletRequest request) -- Line 156 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
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

    @Override
    public void generateToPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        List<Product> productList = null;
        switch (column){
            case "nama": productList = productRepo.findByNamaContainsIgnoreCase(value);break;
            case "deskripsi": productList = productRepo.findByDeskripsiContainsIgnoreCase(value);break;
            case "category": productList = productRepo.cariCategory(value);break;
            default: productList = productRepo.findAll();
        }

        List<RepProductDTO> lt = convertToRepProductDTO(productList);
        if(lt.isEmpty()){
            GlobalResponse.manualResponse(response,GlobalResponse.dataTidakDitemukan("USM02FV061",request));
            return;
        }
        int intRepProductDTOList = lt.size();
        Map<String,Object> map = new HashMap<>();// ini untuk menampung seluruh data yang akan di oper ke file html
        String strHtml = null;
        Context context = new Context();
        Map<String,Object> mapColumnName = GlobalFunction.convertClassToMap(new RepProductDTO());// ini diubah
        List<String> listTemp = new ArrayList<>();
        List<String> listHelper = new ArrayList<>();
        for (Map.Entry<String,Object> entry : mapColumnName.entrySet()) {
            listTemp.add(GlobalFunction.camelToStandard(entry.getKey()));
            listHelper.add(entry.getKey());
        }
        Map<String,Object> mapTemp = null;
        List<Map<String,Object>> listMap = new ArrayList<>();
        for(int i=0;i<intRepProductDTOList;i++){
            mapTemp = GlobalFunction.convertClassToMap(lt.get(i));
            listMap.add(mapTemp);
        }

        map.put("title","REPORT DATA PRODUCT");
        map.put("listKolom",listTemp);
        map.put("listHelper",listHelper);
        map.put("timestamp",LocalDateTime.now());
        map.put("totalData",intRepProductDTOList);
        map.put("listContent",listMap);
        map.put("username",mapToken.get("namaLengkap"));
        context.setVariables(map);
        strHtml = springTemplateEngine.process("/report/global-report",context);
        pdfGenerator.htmlToPdf(strHtml,"product",response);

    }

    public Product convertToEntity (ValProductDTO valProductDTO) {
//        Product groupMenu = new Product();
//        groupMenu.setNama(valProductDTO.getNama());
//        groupMenu.setDeskripsi(valProductDTO.getDeskripsi());
        Product product = modelMapper.map(valProductDTO, Product.class);
        return product;
    }

    public List<RespProductDTO> convertToRespProductDTO (List<Product> products) {
//        List<RespProductDTO> respProductDTOList = new ArrayList<>();
//
//        for (Product groupMenu: groupMenus) {
//            RespProductDTO respProductDTO = new RespProductDTO();
//            respProductDTO.setId(groupMenu.getId());
//            respProductDTO.setNama(groupMenu.getNama());
//            respProductDTO.setDeskripsi(groupMenu.getDeskripsi());
//            respProductDTOList.add(respProductDTO);
//        }
        List<RespProductDTO> respProductDTOList = modelMapper.map(products, new TypeToken<List<RespProductDTO>>() {}.getType());
        return respProductDTOList;
    }

    public List<RepProductDTO> convertToRepProductDTO(List<Product> products) {
        List<RepProductDTO> lt = new ArrayList<>();
        for (Product product : products) {
            Object object = product.getProductCategory();//untuk handling jika nilainya berisi null
            RepProductDTO repProductDTO = new RepProductDTO();
            repProductDTO.setId(product.getId());
            repProductDTO.setNama(product.getNama());
            repProductDTO.setDeskripsi(product.getDeskripsi());
            repProductDTO.setNamaCategory(object==null?"":product.getProductCategory().getNama());//ternary operator untuk handling null value
            lt.add(repProductDTO);
        }
        return lt;
    }

}
