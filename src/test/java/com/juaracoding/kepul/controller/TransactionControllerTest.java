package com.juaracoding.kepul.controller;

import com.juaracoding.kepul.model.Akses;
import com.juaracoding.kepul.model.Product;
import com.juaracoding.kepul.model.Status;
import com.juaracoding.kepul.model.Transaction;
import com.juaracoding.kepul.repositories.ProductRepo;
import com.juaracoding.kepul.repositories.StatusRepo;
import com.juaracoding.kepul.repositories.TransactionRepo;
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

import java.util.*;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TransactionControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    private JSONObject req;
    private Product product;
    private Transaction transaction;
    private Random rand;//optional (KTP 16 Digit rand.nextLong(1111111111111111L,9999999999999999L) )
    private String token;
    private DataGenerator dataGenerator;

    @BeforeClass
    private void init(){
        token = new TokenGenerator(AuthControllerTest.authorization).getToken();
        rand = new Random();
        req = new JSONObject();
        product = new Product();
        transaction = new Transaction();
        dataGenerator  = new DataGenerator();
        Optional<Product> op = productRepo.findTopByOrderByIdDesc();
        product = op.get();
        Optional<Transaction> optionalTransaction = transactionRepo.findTopByOrderByIdDesc();
        transaction = optionalTransaction.get();

    }

    @BeforeTest
    private void setup(){
        /** OPTIONAL == untuk kebutuh environment protocol */
    }

    @Test(priority = 0)
    void save(){
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        req.put("ltProduct",productList);

        String pathVariable = "/transaction";
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

        Optional<Status> optionalStatus = statusRepo.findById(2L);
        Status status = optionalStatus.get();
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        transaction.setStatus(status);
//        transaction.setLtProduct(productList);

        req.put("status",status);
        req.put("ltProduct",productList);

        String pathVariable = "/transaction/"+transaction.getId();
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
        String pathVariable = "/transaction/"+ transaction.getId();
        Response response = given().
                header(AuthControllerTest.AUTH_HEADER, token).
                header("Content-Type", "application/json").
                header("Accept", "*/*").
                request(Method.GET,pathVariable);
        int responseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();//body
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        /** karena menu berelasi dengan group menu maka data nya berbentuk object, sehingga kita tampung ke dalam object Map<String,Object> agar dapat di extract */
        Map<String,Object> status = (Map<String,Object>)jsonPath.get("data.status");
        Map<String,Object> divisionId = (Map<String,Object>)jsonPath.get("data.divisionId");
        Map<String,Object> adminId = (Map<String,Object>)jsonPath.get("data.adminId");
        List<Map<String,Object>> productList = (List<Map<String, Object>>)jsonPath.get("data.ltProduct");

        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,200);//kalau 201
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),200);
        Assert.assertEquals(jsonPath.get("message"),"Data Ditemukan");
        Assert.assertNotNull(jsonPath.get("timestamp"));
        /** compare isi data kembalian dari server dengan data yang diquery di fungsi init */
        Assert.assertEquals(Long.parseLong(jsonPath.get("data.id").toString()), transaction.getId());
        Assert.assertEquals(Long.parseLong(divisionId.get("id").toString()), transaction.getDivisionId().getId());
//        Assert.assertEquals(Long.parseLong(adminId.get("id").toString()), transaction.getAdminId().getId());
        int i=0;
        for (Map<String, Object> map : productList) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Assert.assertEquals(Long.parseLong(entry.getValue().toString()), transaction.getLtProduct().get(i).getId());
            }
            i++;
        }
        Assert.assertEquals(Long.parseLong(status.get("id").toString()), transaction.getStatus().getId());

    }

    @Test(priority = 30)
    void findAll(){
        String pathVariable = "/transaction";
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
        String pathVariable = "/transaction/asc/id/0";
        String strValue = transaction.getStatus().getNama();
        Response response = given().
                header(AuthControllerTest.AUTH_HEADER, token).
                header("Content-Type", "application/json").
                header("Accept", "*/*").
                param("column","status").//menggunakan kolom nama , karena unik
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
        Assert.assertEquals(jsonPath.get("data.column-name").toString(),"status");//sudah di set di query param nya
        Assert.assertEquals(jsonPath.get("data.value").toString(),strValue);//sudah di set di query param nya

        /** compare content data nya */
        int i=0;
        for (Map<String, Object> map : lt) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equals("status")) {
                    Map<String, Object> mapStatus = (Map<String, Object>) entry.getValue();
                    Assert.assertEquals(mapStatus.get("nama"), transaction.getStatus().getNama());
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
        String pathVariable = "/transaction/pdf";
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
        String pathVariable = "/transaction/"+ transaction.getId();
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
