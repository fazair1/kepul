package com.juaracoding.kepul.controller;

import com.juaracoding.kepul.util.CredentialsAuth;
import com.juaracoding.kepul.util.DataGenerator;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthControllerTest extends AbstractTestNGSpringContextTests {

    private JSONObject req = new JSONObject();
    private String token;
    private Random rand = new Random();
    public static String authorization;
    private DataGenerator dataGenerator;
    private String username;//nanti ini digunakan untuk flow regis -> verify -> login
    private String password;//nanti ini digunakan untuk flow regis -> verify -> login
    private String email;//nanti ini digunakan untuk flow verify -> login
    private Integer otp;//nanti ini digunakan untuk flow verify -> login
    private Boolean isOk = false;//untuk menjaga setiap step estafet, jika maju ke langkah berikutnya tetapi isOk == false maka proses nya di skip

    /** karena gak ada standard yang pasti untuk header key token
     * maka key nya dibuat variable agar suatu saat jika berubah semisal menjadi JwtToken atuapun jwt_token
     * tinggal diubah disini saja value AUTH_HEADER nya.....
     */
    public static final String AUTH_HEADER = "Authorization";

    @BeforeClass
    void init(){
        RestAssured.baseURI = "http://localhost:8081";
        dataGenerator = new DataGenerator();
    }

    @Test(priority = 0)
    void loginAdmin(){
        /** masukkan credentials admin sebagai default untuk proses login */
        req.put("username", CredentialsAuth.ADMIN_USER_NAME);
        req.put("password", CredentialsAuth.ADMIN_PASSWORD);
        Response response =  given().
                header("Content-Type","application/json").
                header("accept","*/*").body(req).
                request(Method.POST, "auth/login");
        int intResponse = response.getStatusCode();
        JsonPath jPath = response.jsonPath();
        this.token = "Bearer "+jPath.getString("token");
        if(intResponse != 200 || token==null){
            System.out.println("ADMIN USERNAME DAN PASSWORD TIDAK ADA, SISTEM AKAN DIHENTIKAN PROSES NYA");
            System.exit(0);
        }
        setToken();
    }

    void setToken(){
        authorization = this.token;
    }

    @Test(priority = 10)
    void regis(){
        isOk = false;
        username = dataGenerator.dataUsername();
        password = dataGenerator.dataPassword();
        email = dataGenerator.dataEmail();

        req.put("username", username);
        req.put("password", password);
        req.put("email", email);
        req.put("nama", dataGenerator.dataNamaLengkap());

        Response response =  given().
                header("Content-Type","application/json").
                header("accept","*/*").body(req).
                request(Method.POST, "auth/regis");
        int intResponse = response.getStatusCode();
        JsonPath jPath = response.jsonPath();
        Map<String,Object> data = jPath.getMap("data");
        ResponseBody responseBody = response.getBody();

        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        otp = Integer.parseInt(data.get("otp").toString());
        if(otp!=null && intResponse == 201 && email!=null){
            isOk=true;
        }
        Assert.assertEquals(intResponse,201);
        Assert.assertEquals(data.get("email"),email);
    }

    @Test(priority = 20)
    void verifyRegis(){
        if(!isOk){//artinya step sebelumnya gagal, jadi step ini tidak perlu dieksekusi
            return;
        }
        isOk = false;
        /** masukkan credentials admin sebagai default untuk proses login */
        req.put("otp", otp==null?rand.nextInt(111111,999999):otp);
        req.put("email", email==null?dataGenerator.dataEmail():email);

        Response response =  given().
                header("Content-Type","application/json").
                header("accept","*/*").body(req).
                request(Method.POST, "auth/verify-regis");
        int intResponse = response.getStatusCode();
        JsonPath jPath = response.jsonPath();
        Map<String,Object> data = jPath.get();
        ResponseBody responseBody = response.getBody();
        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        if(intResponse == 201){
            isOk=true;
        }
        Assert.assertEquals(data.get("message"),"Data Berhasil Disimpan");
        Assert.assertEquals(intResponse,201);
    }

    @Test(priority = 30)
    void loginEstafet(){
        if(!isOk){//artinya step sebelumnya gagal, jadi step ini tidak perlu dieksekusi
            return;
        }
        /** masukkan credentials admin sebagai default untuk proses login */
        req.put("username", username);//melakukan login dari proses estafet registrasi
        req.put("password",password);//melakukan login dari proses estafet registrasi
        Response response =  given().
                header("Content-Type","application/json").
                header("accept","*/*").body(req).
                request(Method.POST, "auth/login");
        int intResponse = response.getStatusCode();
        JsonPath jPath = response.jsonPath();
        Map<String,Object> data = jPath.getMap("data");
        Assert.assertNotNull(data.get("akses"));
        Assert.assertNotNull(data.get("token"));
        Assert.assertEquals(intResponse,200);
    }

}
