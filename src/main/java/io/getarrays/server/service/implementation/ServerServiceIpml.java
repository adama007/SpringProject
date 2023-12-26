package io.getarrays.server.service.implementation;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.getarrays.server.enumeartion.Status;
import io.getarrays.server.model.Server;
import io.getarrays.server.repo.ServerRepo;
import io.getarrays.server.service.ServerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ServerServiceIpml implements ServerService {

    private final ServerRepo serverrepo;

    @Override
    public Server create(Server server) {
        log.info("Saving a new Server:{}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverrepo.save(server);
    }

    @Override
    public Server ping(String ipAddress) {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverrepo.findByIpAdress(ipAddress);

        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            server.setStatus(address.isReachable(10000) ? Status.SERVER_DOWN : Status.SERVER_UP);
            serverrepo.save(server);
        } catch (IOException e) {
            // Gérer l'exception IOException ici (par exemple, journalisation ou traitement
            // spécifique)
            log.error("Erreur lors du ping du serveur avec l'adresse IP {}", ipAddress, e);
        }

        return server;
    }

    @Override
    public List<Server> getAllServer() {
        return serverrepo.findAll();
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all server");
        return serverrepo.findAll(PageRequest.of(0, limit)).toList();

    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id");
        return serverrepo.findById(id).orElse(null);

    }

    @Override
    public void update(Server server) {
        log.info("Updating server : {}", server.getName());
        serverrepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("deleting server : {}", id);
        try {
            serverrepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String setServerImageUrl() {
        String[] imagesName = { "server1.png", "server2.png", "server3.png", "server4.png" };
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image/" + imagesName[new Random().nextInt(4)]).toUriString();
    }

}
