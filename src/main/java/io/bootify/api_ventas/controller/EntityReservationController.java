package io.bootify.api_ventas.controller;

import io.bootify.api_ventas.domain.User;
import io.bootify.api_ventas.model.EntityReservationDTO;
import io.bootify.api_ventas.repos.UserRepository;
import io.bootify.api_ventas.service.EntityReservationService;
import io.bootify.api_ventas.util.CustomCollectors;
import io.bootify.api_ventas.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/entityReservations")
public class EntityReservationController {

    private final EntityReservationService entityReservationService;
    private final UserRepository userRepository;

    public EntityReservationController(final EntityReservationService entityReservationService,
            final UserRepository userRepository) {
        this.entityReservationService = entityReservationService;
        this.userRepository = userRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("userValues", userRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(User::getId, User::getNombre)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("entityReservations", entityReservationService.findAll());
        return "entityReservation/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("entityReservation") final EntityReservationDTO entityReservationDTO) {
        return "entityReservation/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("entityReservation") @Valid final EntityReservationDTO entityReservationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "entityReservation/add";
        }
        entityReservationService.create(entityReservationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("entityReservation.create.success"));
        return "redirect:/entityReservations";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("entityReservation", entityReservationService.get(id));
        return "entityReservation/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("entityReservation") @Valid final EntityReservationDTO entityReservationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "entityReservation/edit";
        }
        entityReservationService.update(id, entityReservationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("entityReservation.update.success"));
        return "redirect:/entityReservations";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        entityReservationService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("entityReservation.delete.success"));
        return "redirect:/entityReservations";
    }

}
