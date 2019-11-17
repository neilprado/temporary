package br.com.bank.rabbitmq.pagamento.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class BancoServiceConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        final String QUEUE_VISA = "client_visa";
        final String QUEUE_MASTER = "client_master";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel canal = connection.createChannel();


    }
}
