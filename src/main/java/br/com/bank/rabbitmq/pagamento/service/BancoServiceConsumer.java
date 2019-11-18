package br.com.bank.rabbitmq.pagamento.service;

import br.com.bank.rabbitmq.pagamento.model.Banco;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class BancoServiceConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        final String QUEUE_RESPONSE = "fila_resposta";
        final String QUEUE_VISA = "client_visa";
        final String QUEUE_MASTER = "client_master";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel canal = connection.createChannel();
        canal.queueDeclare(QUEUE_RESPONSE, true, false, false, null);

        DeliverCallback callback = (consumerTag, delivery) -> {

            String json = new String(delivery.getBody());
            Gson gson = new Gson();
            Banco banco = gson.fromJson(json, Banco.class);
        };

        canal.basicConsume(QUEUE_RESPONSE, true, callback, (consumerTag) -> {});


    }
}
