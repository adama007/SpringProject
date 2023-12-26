package io.getarrays.server.resources;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.getarrays.server.enumeartion.Status;
import io.getarrays.server.model.Response;
import io.getarrays.server.model.Server;
import io.getarrays.server.service.ServerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@AllArgsConstructor
@RequestMapping("/server")

public class ServerResource {
    private final ServerService serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() {
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(of("server", serverService.list(30)))
                        .message("Servers retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()

        );

    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(of("server", server))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping succes" : "Error lors de la ping")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()

        );

    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(of("server", serverService.create(server)))
                        .message("server Created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()

        );

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(of("server", serverService.get(id)))
                        .message("Fetched server by id")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()

        );

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(of("server", serverService.delete(id)))
                        .message("deleted server")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()

        );

    }

    @GetMapping(path = "/images/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getServerImages(@PathVariable("fileName") String fileName) throws IOException {
        byte[] imageBytes = Files
                .readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/images/" + fileName));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
    }

}
