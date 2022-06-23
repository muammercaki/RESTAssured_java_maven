package steps;

import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

public class QuickEditJson {

    @Test
    public void readJSONfile() throws IOException {
        String bdy = new String(Files.readAllBytes(Paths.get("src/test/java/userData.json")));
        given().header("Content-type", "application/json").body(bdy)
                .when().post("/posts").then().log().all()
                .assertThat().statusCode(201);
    }

    @Test
    public void quickEditToJsonId() throws IOException {
        String bdy = new String(Files.readAllBytes(Paths.get("src/test/java/userData.json")));
        String uniqueID = UUID.randomUUID().toString();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.readValue(bdy, ObjectNode.class);
        objectNode.with("messages").put("id", uniqueID);

        System.out.println(objectNode.toPrettyString());
        try {
            FileWriter file = new FileWriter("src/test/java/userData.json");
            file.write(objectNode.toPrettyString());
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
