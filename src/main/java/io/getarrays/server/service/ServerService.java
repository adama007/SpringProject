package io.getarrays.server.service;

import java.util.Collection;
import java.util.List;

import io.getarrays.server.model.Server;

public interface ServerService {

    public Server create(Server server);

    public Server ping(String ipAddress);

    public List<Server> getAllServer();

    public Collection<Server> list(int limit);

    public Server get(Long id);

    public void update(Server server);

    public Boolean delete(Long id);

}
