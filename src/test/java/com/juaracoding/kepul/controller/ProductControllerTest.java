package com.juaracoding.kepul.controller;

import com.juaracoding.kepul.model.Product;
import com.juaracoding.kepul.model.ProductCategory;
import com.juaracoding.kepul.repositories.ProductCategoryRepo;
import com.juaracoding.kepul.repositories.ProductRepo;
import com.juaracoding.kepul.util.DataGenerator;
import com.juaracoding.kepul.util.TokenGenerator;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ProductCategoryRepo productCategoryRepo;

    @Autowired
    private ProductRepo productRepo;

    private JSONObject req;
    private ProductCategory productCategory;
    private Product product;
    private Random rand;//optional (KTP 16 Digit rand.nextLong(1111111111111111L,9999999999999999L) )
    private String token;
    private DataGenerator dataGenerator;

    @BeforeClass
    private void init(){
        token = new TokenGenerator(AuthControllerTest.authorization).getToken();
        rand = new Random();
        req = new JSONObject();
        productCategory = new ProductCategory();
        product = new Product();
        dataGenerator  = new DataGenerator();
        Optional<ProductCategory> op = productCategoryRepo.findTopByOrderByIdDesc();
        productCategory = op.get();
        Optional<Product> optionalProduct = productRepo.findTopByOrderByIdDesc();
        product = optionalProduct.get();

    }

    @BeforeTest
    private void setup(){
        /** OPTIONAL == untuk kebutuh environment protocol */
    }

    @Test(priority = 0)
    void save(){
        req.put("nama",dataGenerator.dataNamaLengkap());
        req.put("deskripsi",dataGenerator.dataNamaLengkap());
        req.put("productCategory",productCategory);

        String pathVariable = "/product";
        Response response = given().
                header(AuthControllerTest.AUTH_HEADER, token).
                header("Content-Type", "application/json").
                header("Accept", "*/*").
                body(req).request(Method.POST,pathVariable);
        int responseCode = response.statusCode();
        JsonPath jPath = response.jsonPath();//body

        ResponseBody responseBody = response.getBody();
        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        Assert.assertTrue(Boolean.parseBoolean(jPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,201);//kalau 201
    }

    @Test(priority = 10)
    void update(){
        String nama = dataGenerator.dataNamaLengkap();
        String deskripsi = dataGenerator.dataNamaLengkap();

        req.put("nama",nama);
        req.put("deskripsi",deskripsi);
        req.put("productCategory",productCategory);

        product.setNama(nama);
        product.setDeskripsi(deskripsi);

        String pathVariable = "/product/"+product.getId();
        Response response = given().
                header(AuthControllerTest.AUTH_HEADER, token).
                header("Content-Type", "application/json").
                header("Accept", "*/*").
                body(req).request(Method.PUT,pathVariable);
        int responseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();//body
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,200);//kalau 201
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),200);
        Assert.assertEquals(jsonPath.get("message"),"Data Berhasil Diubah");
        Assert.assertEquals(jsonPath.get("data"),"");
        Assert.assertNotNull(jsonPath.get("timestamp"));
    }

    @Test(priority = 20)
    void findById(){
        String pathVariable = "/product/"+ product.getId();
        Response response = given().
                header(AuthControllerTest.AUTH_HEADER, token).
                header("Content-Type", "application/json").
                header("Accept", "*/*").
                request(Method.GET,pathVariable);
        int responseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();//body
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string

        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,200);//kalau 201
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),200);
        Assert.assertEquals(jsonPath.get("message"),"Data Ditemukan");
        Assert.assertNotNull(jsonPath.get("timestamp"));
        /** compare isi data kembalian dari server dengan data yang diquery di fungsi init */
        Assert.assertEquals(Long.parseLong(jsonPath.get("data.id").toString()), product.getId());
        Assert.assertEquals((jsonPath.get("data.nama").toString()), product.getNama());

    }

    @Test(priority = 30)
    void findAll(){
        String pathVariable = "/product";
        Response response = given().
                header(AuthControllerTest.AUTH_HEADER, token).
                header("Content-Type", "application/json").
                header("Accept", "*/*").
                request(Method.GET,pathVariable);
        int responseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();//body
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        /** untuk case ini pengambilan datanya menggunakan List , Jadi dipassing ke object List<Map<String,Object>> */
        List<Map<String,Object>> lt = jsonPath.getList("data.content");

        Assert.assertEquals(Integer.parseInt(jsonPath.get("data.total-data").toString()),lt.size());//info total Data dengan content yang dikirim harus sama
        Assert.assertEquals(Integer.parseInt(jsonPath.get("data.current-page").toString()),0);//default untuk findAll
        Assert.assertEquals(jsonPath.get("data.sort").toString(),"asc");//default untuk findAll
        Assert.assertEquals(jsonPath.get("data.sort-by").toString(),"id");//default untuk findAll
        Assert.assertEquals(jsonPath.get("data.column-name").toString(),"");//default untuk findAll
        Assert.assertEquals(jsonPath.get("data.value").toString(),"");//default untuk findAll


        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,200);//kalau 200
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),200);
        Assert.assertEquals(jsonPath.get("message"),"Data Ditemukan");
        Assert.assertNotNull(jsonPath.get("timestamp"));
    }

    @Test(priority = 40)
    void findByParam(){
        String pathVariable = "/product/asc/id/0";
        String strValue = product.getNama();
        Response response = given().
                header(AuthControllerTest.AUTH_HEADER, token).
                header("Content-Type", "application/json").
                header("Accept", "*/*").
                param("column","nama").//menggunakan kolom nama , karena unik
                        param("value",strValue).
                param("size",10).
                request(Method.GET,pathVariable);

        int responseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();//body
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        /** untuk case ini pengambilan datanya menggunakan List , Jadi dipassing ke object List<Map<String,Object>> */
        List<Map<String,Object>> lt = jsonPath.getList("data.content");


        Assert.assertEquals(Integer.parseInt(jsonPath.get("data.total-data").toString()),lt.size());//info total Data dengan content yang dikirim harus sama

        Assert.assertEquals(Integer.parseInt(jsonPath.get("data.current-page").toString()),0);//sudah di set di path variable
        Assert.assertEquals(jsonPath.get("data.sort").toString(),"asc");//sudah di set di path variable
        Assert.assertEquals(jsonPath.get("data.sort-by").toString(),"id");//sudah di set di path variable
        Assert.assertEquals(Integer.parseInt(jsonPath.get("data.size-per-page").toString()),10);//sudah di set di query param nya
        Assert.assertEquals(jsonPath.get("data.column-name").toString(),"nama");//sudah di set di query param nya
        Assert.assertEquals(jsonPath.get("data.value").toString(),strValue);//sudah di set di query param nya

        /** compare content data nya */
        int i=0;
        for (Map<String, Object> map : lt) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equals("nama")) {
//                    Map<String, Object> mapTemp = (Map<String, Object>) entry.getValue();
                    Assert.assertEquals(entry.getValue(), product.getNama());
                }
            }
            i++;
        }
        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,200);//kalau 200
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),200);
        Assert.assertEquals(jsonPath.get("message"),"Data Ditemukan");
        Assert.assertNotNull(jsonPath.get("timestamp"));
    }

    @Test(priority = 50)
    private void downloadPDF(){
        String pathVariable = "/product/pdf";
        Response response = given().
                header("accept","application/pdf").
                param("column","").
                header(AuthControllerTest.AUTH_HEADER,token).
                param("value","").
                request(Method.GET, pathVariable);
        int responseCode = response.statusCode();
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        Assert.assertEquals(responseCode,200);
        /** khusus untuk download file harus di cek header nya */
        Assert.assertTrue(response.getHeader("Content-Disposition").contains(".pdf"));// file nya memiliki extension .xlsx
        Assert.assertEquals(response.getHeader("Content-Type"),"application/pdf");// content type wajib ini untuk excel
    }

    @Test(priority = 999)
    private void delete(){
        String pathVariable = "/product/"+ product.getId();
        /** jika ingin menjalankan suite / integration test fungsional delete di taruh pada urutan paling akhir , karena data yang dipilih di awal di gunakan untuk validasi di fungsi-fungsi sebelumnya */
        Response response = given().
                header("Content-Type","application/json").
                header("accept","*/*").
                header(AuthControllerTest.AUTH_HEADER,token).
                request(Method.DELETE, pathVariable);
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        int responseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();
        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,200);
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),200);
        Assert.assertEquals(jsonPath.get("message"),"Data Berhasil Dihapus");
        Assert.assertEquals(jsonPath.get("data"),"");
        Assert.assertNotNull(jsonPath.get("timestamp"));
    }

}
