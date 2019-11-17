package br.com.bank.rabbitmq.pagamento.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class VisaProducerService {
    public VisaProducerService(String response) throws IOException, TimeoutException {
        final String QUEUE_CLIENT_VISA = "client_visa";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);

        try(
                Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()){
            channel.queueDeclare(QUEUE_CLIENT_VISA, true, false, false, null);
            channel.basicPublish("", QUEUE_CLIENT_VISA, MessageProperties.PERSISTENT_TEXT_PLAIN, response.getBytes());
        }
    }
}
