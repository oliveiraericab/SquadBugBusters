package apiTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

    public class TestBooking {   //Início da classe
        //Atributos
        static String ct = "application/json";  //content type
        static String uriUser = "https://restful-booker.herokuapp.com/";

        //Funções e métodos
        //Funções de apoio
        public static String lerArquivoJson(String arquivoJson) throws IOException {
            return new String(Files.readAllBytes(Paths.get(arquivoJson)));
        }
        //Funções  de Testes

        @Test
        public void testarCreateToken() throws IOException {
            String jsonBody = lerArquivoJson("src/test/resources/json/user2.json");

            given()
                    .contentType(ct)                             // tipo de conteúdo
                    .log().all()                                 // mostre tudo
                    .body(jsonBody)                              // corpo da requisição
                    .when()
                    .post(uriUser + "auth")
                    .then()
                    .contentType(ct)
                    .log().all()
                    .body("token", hasLength(15))
            ;
        }

        @Test
        public void testarCreateBooking() throws IOException {
            // carregar os dados do nosso Json
            String jsonBody = lerArquivoJson("src/test/resources/json/user1.json");
            String username = "Malagueta";

            //realizar o teste
            given()                                              // Dado que
                    .contentType(ct)                             // tipo de conteúdo
                    .log().all()                                 // mostre tudo
                    .body(jsonBody)                              // corpo da requisição
            .when()
                    .post(uriUser)                               //Endpoint / Onde
            .then()
                    //.accept(ac)
                    .contentType(ct)
                    .log().all()                                 // mostre tudo na volta
                    .body("booking.firstname", is("Malagueta"))
                    .body("booking.lastname", is("Oliveira"))
                    .body("booking.bookingdates.checkin", is("2018-01-01"))
                    .body("booking.bookingdates.checkout", is("2019-01-01"))
            ;
        }

        @Test
        public void testarGetBooking(){
            String id = "5973";

                given()
                    .contentType(ct)
                    .log().all()
                .when()
                    .get("booking/" + uriUser + id)

                .then()
                    .contentType(ct)
                    .log().all()
                    .body("firstname", is("Malagueta"))
                    .body("lastname", is("Oliveira"))
                    //.body("depositpaid", is("true"))
                    .body("bookingdates.checkin", is("2018-01-01"))
                    .body("bookingdates.checkout", is("2019-01-01"))
            ;

        }
    }
    /*

        @Test
        public void testarAlterarUser() throws IOException {
            String jsonBody = lerArquivoJson("src/test/resources/json/user2.json");
            String userId = "15215912";
            String username = "malagueta";

            given()
                    .contentType(ct)
                    .log().all()
                    .body(jsonBody)
                    .when()
                    .put(uriUser + username)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("code", is(200))
                    .body("type", is("unknown"))
                    .body("message", is(userId))
            ;
        }

        @Test @Order(4)
        public void testarDeletarUser(){
            String username = "malagueta";

            given()
                    .contentType(ct)
                    .log().all()
                    .when()
                    .delete(uriUser + username)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("code", is(200))
                    .body("type", is("unknown"))
                    .body("message", is(username))
            ;
        }

        @ParameterizedTest @Order(6)
        @CsvFileSource(resources = "csv/massaUser.csv", numLinesToSkip = 1, delimiter = ',')
        public void testarIncluirUserCSV(
                String id,
                String username,
                String firstName,
                String lastName,
                String email,
                String password,
                String phone,
                String userStatus){

            User user = new User();
            user.id = id;
            user.username = username;
            user.firstName = firstName;
            user.lastName = lastName;
            user.email = email;
            user.password = password;
            user.phone = phone;
            user.userStatus = userStatus;

            Gson gson = new Gson();
            String jsonBody = gson.toJson(user);

            //realizar o teste
            given()                                              // Dado que
                    .contentType(ct)                             // tipo de conteúdo
                    .log().all()                                 // mostre tudo
                    .body(jsonBody)                              // corpo da requisição
                    .when()
                    .post(uriUser)                               //Endpoint / Onde
                    .then()
                    .log().all()                                 // mostre tudo na volta
                    .statusCode(200)                          // comunicação ida e volta está ok
                    .body("code", is(200))              // tag code é 200
                    .body("type", is("unknown"))        // tag type é "unknown"
                    .body("message", is(id))              // message é userId
            ;
        }



    }
*/