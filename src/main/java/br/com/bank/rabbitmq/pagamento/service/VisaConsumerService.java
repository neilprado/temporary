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
public class VisaConsumerService {
    public static void main(String[] args) throws IOException, TimeoutException {
        final String QUEUE_VISA = "fila_visa";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_VISA, true, false, false, null);
        DeliverCallback callback = (consumerTag, delivery) -> {
            String json = new String(delivery.getBody());
            System.out.println("Recebendo dados da fila " + json);
            Gson gson = new Gson();
            Banco banco = gson.fromJson(json, Banco.class);

            System.out.println(banco.toString());
            String response = "Pagamento para o cartão visa " + banco.getCartao() + " realizado com sucesso";
            try {
                new VisaProducerService(response);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        };
        channel.basicConsume(QUEUE_VISA, true, callback, (consumerTag) -> {});
    }
}
