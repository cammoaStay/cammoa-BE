//package com.cammoastay.zzon.manager.member;
//
//import com.cammoastay.zzon.manager.member.repository.ManagerRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class ManagerRepositoryTest {
//
//    @MockBean
//    private ManagerRepository managerRepository;
//
//    @Test
//    @DisplayName("존재하는 매니저의 정보를 성공적으로 수정한다.")
//    void updateMangerSuccessfully() {
//        //given
//        Long managerId = 1L;
//        ManagerEntity existingManager = new ManagerEntity(managerId, "OldName", "oldEmail@example.com");
//        ManagerEntity updateManager = new ManagerEntity(managerId, "newName", "newEmail@example.com");
//
//        Mockito.when(managerRepository.findById(managerId)).thenReturn(Optional.of(existingManager));
//
//        // when
//        existingManager.setName(updateManager.getName());
//        existingManager.setManagerEmail(updateManager.getManagerEmail());
//        managerRepository.save(existingManager);
//
//        //then
//        assertEquals("newName", existingManager.getName());
//        assertEquals("newEmail@example.com", existingManager.getManagerEmail());
//    }
//}