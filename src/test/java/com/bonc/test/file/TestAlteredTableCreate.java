package com.bonc.test.file;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bblee.data.service.Application;
import com.bblee.data.service.structure.Field;
import com.bblee.data.service.structure.Table;
import com.bblee.data.service.structure.FieldType;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class TestAlteredTableCreate {
	
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private ObjectMapper objectMapper;

	@Bean
	public Table getAlteredTable() {
		Table table = new Table();
		table.setFilePath("C:\\Users\\Administrator\\Desktop\\excelTest\\excel\\insert.xlsx");
		table.setTableName("testInsert");

		Field field1 = new Field();
		field1.setFieldType(FieldType.VARCHAR);
		field1.setInsert(false);
		field1.setName("field1");

		Field field2 = new Field();
		field2.setFieldType(FieldType.VARCHAR);
		field2.setInsert(false);
		field2.setName("field2");
		table.setFields(new Field[] { field1, field2 });
		return table;
	}
	
    @Test
    public void testDetailQuery() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        System.out.println(
                mockMvc.perform(post("/excel/test").contentType("application/json")
                        .content(objectMapper.writeValueAsString(getAlteredTable()))).andReturn().getResponse()
                        .getContentAsString());
    }
}
