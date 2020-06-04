package org.formation.controller.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.formation.model.FournisseurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value=FournisseurRestController.class)
@ActiveProfiles("memory")
public class FournisseurRestControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private FournisseurRepository fournisseurRepository;
	
	@Test
//	@WithMockUser
	public void testFindReference() throws Exception {

		mvc.perform(get("/api/fournisseurs/bel/")).andExpect(status().isOk());
	}

}
