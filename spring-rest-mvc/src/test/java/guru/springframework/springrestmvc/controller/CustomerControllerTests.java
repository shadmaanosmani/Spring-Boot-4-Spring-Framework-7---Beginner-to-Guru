package guru.springframework.springrestmvc.controller;

import java.util.List;
import java.util.Map;
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

import guru.springframework.springrestmvc.model.Customer;
import guru.springframework.springrestmvc.service.CustomerService;
import guru.springframework.springrestmvc.service.impl.CustomerServiceImpl;
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
	ArgumentCaptor<Customer> customerArgumentCaptor;

	CustomerServiceImpl customerServiceImpl;
	
	@BeforeEach
	void setUp() {
		
		this.customerServiceImpl = new CustomerServiceImpl();
		
	}
	
	@Test
	void patchById() throws JacksonException, Exception {
		
		Customer customer = customerServiceImpl.listCustomers().getFirst();
		
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
		
		Customer customer = customerServiceImpl.listCustomers().get(0);
		
		mockMvc.perform(MockMvcRequestBuilders.delete(CustomerController.CUSTOMER_PATH_WITH_ID, customer.getId())
											  .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isNoContent());
		
		Mockito.verify(customerService).deleteById(uuidArgumentCaptor.capture());
		AssertionsForClassTypes.assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
		
	}
	
	@Test
	void updateCustomer() throws JacksonException, Exception {
		
		Customer customer = customerServiceImpl.listCustomers().getFirst();
		
		mockMvc.perform(MockMvcRequestBuilders.put(CustomerController.CUSTOMER_PATH_WITH_ID, customer.getId())
											  .contentType(MediaType.APPLICATION_JSON)
											  .content(objectMapper.writeValueAsString(customer))
											  .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isNoContent());
		
		Mockito.verify(customerService).updateCustomer(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(Customer.class));
		
	}
	
	@Test
	void handlePost() throws JacksonException, Exception {
		
		Customer customer = customerServiceImpl.listCustomers().getFirst();
		
		BDDMockito.given(customerService.saveNewCustomer(ArgumentMatchers.any(Customer.class)))
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
		
		List<Customer> customers = customerServiceImpl.listCustomers();
		
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
		
		Customer customer = customerServiceImpl.listCustomers().getFirst();
		
		BDDMockito.given(customerService.getCustomerById(customer.getId()))
				  .willReturn(customer);
		
		mockMvc.perform(MockMvcRequestBuilders.get(CustomerController.CUSTOMER_PATH_WITH_ID, customer.getId())
											  .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(customer.getId().toString())))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is(customer.getName())));
		
	}
	
}
