package guru.springframework.springrestmvc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.AssertionsForClassTypes;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import guru.springframework.springrestmvc.exception.NotFoundException;
import guru.springframework.springrestmvc.model.dto.CustomerDTO;
import guru.springframework.springrestmvc.service.CustomerService;
import guru.springframework.springrestmvc.service.impl.CustomerServiceMapImpl;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(CustomerController.class)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockitoBean
	CustomerService customerService;
	
	@Captor
	ArgumentCaptor<UUID> uuidArgumentCaptor;
	
	@Captor
	ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

	CustomerServiceMapImpl customerServiceImpl;
	
	@BeforeEach
	void setUp() {
		
		this.customerServiceImpl = new CustomerServiceMapImpl();
		
	}
	
	@Test
	void getByIdNotFound() throws Exception {
		
		BDDMockito.given(customerService.getCustomerById(ArgumentMatchers.any(UUID.class)))
				  .willThrow(NotFoundException.class);
		
		mockMvc.perform(get(CustomerController.CUSTOMER_PATH_WITH_ID, UUID.randomUUID())
					   .accept(MediaType.APPLICATION_JSON))
			   .andExpect(status().isNotFound());
		
	}
	
	@Test
	void patchById() throws JacksonException, Exception {

		CustomerDTO customer = customerServiceImpl.listCustomers().getFirst();

		BDDMockito.given(customerService.patchCustomer(ArgumentMatchers.any(), ArgumentMatchers.any()))
				  .willReturn(true);
		
		Map<String, String> request = Map.of("name", "New Customer Name");
		
		mockMvc.perform(MockMvcRequestBuilders.patch(CustomerController.CUSTOMER_PATH_WITH_ID, customer.getId())
											  .contentType(MediaType.APPLICATION_JSON)
											  .content(objectMapper.writeValueAsString(request))
											  .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isNoContent());

		Mockito.verify(customerService).patchCustomer(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());
		AssertionsForClassTypes.assertThat(uuidArgumentCaptor.getValue()).isEqualTo(customer.getId());
		AssertionsForClassTypes.assertThat(customerArgumentCaptor.getValue().getName()).isEqualTo(request.get("name"));

	}
	
	@Test
	void deletedById() throws Exception {
		
		CustomerDTO customer = customerServiceImpl.listCustomers().get(0);
		
		BDDMockito.given(customerService.deleteById(ArgumentMatchers.any()))
				  .willReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.delete(CustomerController.CUSTOMER_PATH_WITH_ID, customer.getId())
											  .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isNoContent());
		
		Mockito.verify(customerService).deleteById(uuidArgumentCaptor.capture());
		AssertionsForClassTypes.assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
		
	}
	
	@Test
	void updateCustomer() throws JacksonException, Exception {
		
		CustomerDTO customer = customerServiceImpl.listCustomers().getFirst();
		
		BDDMockito.given(customerService.updateCustomer(ArgumentMatchers.any(), ArgumentMatchers.any()))
				  .willReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.put(CustomerController.CUSTOMER_PATH_WITH_ID, customer.getId())
											  .contentType(MediaType.APPLICATION_JSON)
											  .content(objectMapper.writeValueAsString(customer))
											  .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isNoContent());
		
		Mockito.verify(customerService).updateCustomer(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(CustomerDTO.class));
		
	}
	
	@Test
	void handlePost() throws JacksonException, Exception {
		
		CustomerDTO customer = customerServiceImpl.listCustomers().getFirst();
		
		BDDMockito.given(customerService.saveNewCustomer(ArgumentMatchers.any(CustomerDTO.class)))
				  .willReturn(customer);
		
		mockMvc.perform(MockMvcRequestBuilders.post(CustomerController.CUSTOMER_PATH)
											  .contentType(MediaType.APPLICATION_JSON)
											  .content(objectMapper.writeValueAsString(customer))
											  .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isCreated())
			   .andExpect(MockMvcResultMatchers.header().exists("Location"));
		
	}
	
	@Test
	void listCustomers() throws Exception {
		
		List<CustomerDTO> customers = customerServiceImpl.listCustomers();
		
		BDDMockito.given(customerService.listCustomers())
				  .willReturn(customers);
		
		mockMvc.perform(MockMvcRequestBuilders.get(CustomerController.CUSTOMER_PATH)
											  .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Is.is(customers.size())));
		
	}
	
	@Test
	void getCustomerById() throws Exception {
		
		CustomerDTO customer = customerServiceImpl.listCustomers().getFirst();
		
		BDDMockito.given(customerService.getCustomerById(customer.getId()))
				  .willReturn(Optional.ofNullable(customer));
		
		mockMvc.perform(MockMvcRequestBuilders.get(CustomerController.CUSTOMER_PATH_WITH_ID, customer.getId())
											  .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(customer.getId().toString())))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is(customer.getName())));
		
	}
	
}
