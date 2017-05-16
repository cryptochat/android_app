package app.cryptochat.com.cryptochat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.junit.Test;

import java.util.Dictionary;
import java.util.HashMap;

import app.cryptochat.com.cryptochat.Models.MessageResponse;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {


        String json = "{\n" +
                "    \"identifier\": {\n" +
                "        \"channel\": \"WsChatChannel\"\n" +
                "    },\n" +
                "    \"message\": {\n" +
                "        \"header\": {\n" +
                "            \"method_name\": \"incoming_message\"\n" +
                "        },\n" +
                "        \"body\": {\n" +
                "            \"created_at\": 1469984626,\n" +
                "            \"text\": \"Your message to recipient\",\n" +
                "            \"sender\": {\n" +
                "                \"id\": 7,\n" +
                "                \"first_name\": \"exampleFirstName\",\n" +
                "                \"last_name\": \"exampleLastName\",\n" +
                "                \"username\": \"exampleUsername\",\n" +
                "                \"avatar\": {\n" +
                "                    \"url\": \"http://wishbyte.org/uploads/user/6358d754-eb30-49fd-8638-c49dfc579573/avatar/1547cfa9919ecd59ca143be176037082.jpeg\",\n" +
                "                    \"small\": {\n" +
                "                        \"url\": \"http://wishbyte.org/uploads/user/6358d754-eb30-49fd-8638-c49dfc579573/avatar/small_1547cfa9919ecd59ca143be176037082.jpeg\"\n" +
                "                    },\n" +
                "                    \"medium\": {\n" +
                "                        \"url\": \"http://wishbyte.org/uploads/user/6358d754-eb30-49fd-8638-c49dfc579573/avatar/medium_1547cfa9919ecd59ca143be176037082.jpeg\"\n" +
                "                    },\n" +
                "                    \"big\": {\n" +
                "                        \"url\": \"http://wishbyte.org/uploads/user/6358d754-eb30-49fd-8638-c49dfc579573/avatar/big_1547cfa9919ecd59ca143be176037082.jpeg\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";

      //  json = json.replaceAll("[\\\\]","");
        HashMap<String, Object> dictionary = new Gson().fromJson(json, HashMap.class);
      //  String message1 = new Gson().fromJson(json, String.class);

        //json = json.replaceAll("[\\\\]", "");
        //json = json.replaceAll("[\\\\]", "");
       // json = "{\"test\":\"test\"}";


        //String jsonMessage = jsonObject.get("message").toString();
        //JsonObject header = new Gson().fromJson(jsonMessage, JsonObject.class);


    }
}