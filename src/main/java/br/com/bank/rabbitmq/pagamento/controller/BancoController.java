package br.com.bank.rabbitmq.pagamento.controller;

import br.com.bank.rabbitmq.pagamento.model.Banco;
import br.com.bank.rabbitmq.pagamento.service.BancoServiceProducer;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@CrossOrigin
@RequestMapping("/api/cliente")
public class BancoController {
    private BancoServiceProducer producer;

    public BancoController(BancoServiceProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public Banco pagar(@RequestBody Banco banco) throws IOException, TimeoutException {
            producer.producer(banco);
        return banco;

    }
}
