package sia.tacocloud.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.Taco;
import sia.tacocloud.data.TacoRepository;

import java.util.Optional;

@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoController {

    @Autowired
    private TacoRepository tacoRepository;

    @Autowired
    private EntityLinks entityLinks;

    @GetMapping("/recent")
    public Iterable<Taco> recentTacos() {
        PageRequest pageRequest = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return tacoRepository.findAll(pageRequest).getContent();
    }

    @GetMapping("/{id}")
    public Taco tacoById(@PathVariable Long id) {
        Optional<Taco> optionalTaco = tacoRepository.findById(id);
        return optionalTaco.orElse(null);
    }

}