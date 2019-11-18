package br.com.bank.rabbitmq.pagamento.service;


import br.com.bank.rabbitmq.pagamento.model.Banco;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import lombok.Data;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
@Data
public class BancoServiceProducer  {
    public void producer(Banco banco) throws IOException, TimeoutException {
        final String QUEUE_VISA = "fila_visa";
        final String QUEUE_MASTER = "fila_master";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);

        Gson gson = new Gson();
        String value = gson.toJson(banco);

        try (
                Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()){
            if(banco.getBandeira() == 0){
                channel.queueDeclare(QUEUE_VISA, true, false, false, null);
                channel.basicPublish("", QUEUE_VISA, MessageProperties.PERSISTENT_TEXT_PLAIN, value.getBytes());
                System.out.println("Enviando " + value + " para a fila do VISA");
            }else{
                channel.queueDeclare(QUEUE_MASTER, true, false, false, null);
                channel.basicPublish("", QUEUE_MASTER, MessageProperties.PERSISTENT_TEXT_PLAIN, value.getBytes());
                System.out.println("Enviando " + value + " para a fila do MASTER");
            }

        }

    }
}
