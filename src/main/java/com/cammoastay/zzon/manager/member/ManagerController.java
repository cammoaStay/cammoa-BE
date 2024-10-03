package com.cammoastay.zzon.manager.member;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/api/v1/managercheck/{managerId}")
    public ManagerDto managercheck(@PathVariable Long managerId) {
        return managerService.getManagerById(managerId);
    }

    @PutMapping("/api/v1/managerupdate/{managerId}")
    public void managerUpdate(@PathVariable Long managerId, @RequestBody ManagerDto managerDto) {
        managerService.updateById(managerId, managerDto);
    }

    @DeleteMapping("/api/v1/managerdelete/{managerId}")
    public void managerDelete(@PathVariable Long managerId) {
        managerService.deleteById(managerId);
    }

}
