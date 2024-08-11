package com.hlopez.msvc.personas.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "cuenta-service", url = "localhost:8001")
public interface CuentaClientRest {
}
