package com.alligator.alligatorapi.model.deserializer;

import com.alligator.alligatorapi.model.dto.EntityHrefLink;
import com.alligator.alligatorapi.model.entity.sprint.Sprint;
import com.alligator.alligatorapi.model.repository.sprint.SprintRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@RequiredArgsConstructor
public class SprintDeserializer {
    private final SprintRepository sprintRepository;

    public Sprint deserialize(EntityHrefLink entityHrefLink) {
        // Extract ID from href
        try {
            URI uri = new URI(entityHrefLink.getHref());
            String path = uri.getPath();
            String idStr = path.substring(path.lastIndexOf('/') + 1);
            Long sprintId = Long.parseLong(idStr);

            // Fetch Sprint entity from repository
            return sprintRepository.findById(sprintId)
                    .orElseThrow(() -> new EntityNotFoundException("Sprint not found"));

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
