package RabbitMQ;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import alignment.Alignment;

import java.io.IOException;

@Component
public class Controller {
    Logger logger = Logger.getLogger(Controller.class);

    @RabbitListener(queues = "query")
    public String worker(String message) throws Exception {
        logger.info("Received on worker : " + message);

        try {
            JSONObject json = new JSONObject(message);

            int existence = json.getInt("existence");
            int extension = json.getInt("extension");
            String sequence1 = json.getString("sequence1");
            String sequence2 = json.getString("sequence2");

            Alignment alignment = new Alignment(sequence1, sequence2, existence, extension);
            alignment.align();

            json.put("existence", alignment.getExistence());
            json.put("extension", alignment.getExtention());
            json.put("result1", alignment.getResultA());
            json.put("result2", alignment.getResultB());
            json.put("length", alignment.getLength());
            json.put("identities", alignment.getIdentities());
            json.put("gaps", alignment.getGaps());

            return json.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Что-то пошло не так: \n" + e.getMessage();
        }
    }
}