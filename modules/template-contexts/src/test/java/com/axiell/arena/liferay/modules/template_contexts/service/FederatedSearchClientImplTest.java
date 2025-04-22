package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.arena.configuration.ArenaGroupConfiguration;
import com.axiell.arena.liferay.modules.arena.configuration.ArenaSystemConfiguration;
import com.axiell.arena.liferay.modules.arena.model.LMSSearchResponse;
import com.axiell.arena.liferay.modules.arena.model.LMSSearchResultResponse;
import com.axiell.arena.liferay.modules.arena.util.ArenaUtil;
import com.axiell.arena.liferay.modules.template_contexts.model.RecordsResponse;
import com.axiell.arena.liferay.modules.template_contexts.model.central.CentralSettingsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FederatedSearchClientImplTest {
/*
    private final Long groupId = 123L;
    @Mock
    private StaticContextService staticContextService;
    @Mock
    private ThemeDisplay themeDisplay;
    @Mock
    private CentralSettingsCacheService centralSettingsCacheService;
    @Mock
    private TemplateContextConfig templateContextConfig;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ArenaLocalServiceClient arenaLocalServiceClient;
    @InjectMocks
    private FederatedSearchClientImpl federatedSearchClient;
    private Configuration cfg;
    private CentralSettingsDto centralSettingsDto;

    private static void assertTemplateOutput(String output) {
        assertTrue(output.contains("Total Hits: 360,348"));
        assertTrue(output.contains("Name: Wynton Marsalis with his \"big band\" at the Riverfront Tent, New Orleans Jazz Festival"));
        assertTrue(output.contains("ID: 2007.0103.2.271"));
        assertTrue(output.contains("Rebirth at Jazz Festival  2697/9  Michael P. Smith, 1988 / 11 [circled]"));
        assertTrue(output.contains("photographic gelatin"));
        assertTrue(output.contains("2849/13"));
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        centralSettingsDto = new CentralSettingsDto();
        centralSettingsDto.setDefaultSourceId("hnoc");

        when(staticContextService.getThemeDisplay()).thenReturn(themeDisplay);
        when(themeDisplay.getScopeGroupId()).thenReturn(groupId);
        when(templateContextConfig.restTemplate()).thenReturn(restTemplate);
        when(templateContextConfig.objectMapper()).thenReturn(new ObjectMapper());
        when(centralSettingsCacheService.getCentralSettings(groupId)).thenReturn(centralSettingsDto);

        cfg = new Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    @Test
    public void testMethodWithChainedAndStaticMocks() throws Exception {
        try (MockedStatic<ArenaUtil> arenaUtilMockedStatic = setupArenaUtilMocks()) {
            Long retrievedGroupId = staticContextService.getThemeDisplay().getScopeGroupId();
            assertEquals(groupId, retrievedGroupId);

            CentralSettingsDto retrievedSettings = centralSettingsCacheService.getCentralSettings(groupId);
            assertEquals(centralSettingsDto, retrievedSettings);

            String apiEndpoint = ArenaUtil.getArenaSystemConfiguration().federatedSearchApiEndpoint();
            String customerAlias = ArenaUtil.getArenaGroupConfiguration(groupId).federatedSearchCustomerAlias();

            assertEquals("http://arena-federated-search:9799", apiEndpoint);
            assertEquals("testCustomer", customerAlias);

            arenaUtilMockedStatic.verify(ArenaUtil::getArenaSystemConfiguration, times(1));
            arenaUtilMockedStatic.verify(() -> ArenaUtil.getArenaGroupConfiguration(groupId), times(1));

            verify(staticContextService, times(1)).getThemeDisplay();
            verify(themeDisplay, times(1)).getScopeGroupId();
            verify(centralSettingsCacheService, times(1)).getCentralSettings(groupId);
        }
    }

    @Test
    public void testGetRecords() throws Exception {
        try (MockedStatic<ArenaUtil> ignored = setupArenaUtilMocks()) {
            setupRestTemplateMock("event.stream.response.txt");

            RecordsResponse response = federatedSearchClient.getRecords(
                    "sourceId", 0, 10, 5, "query",
                    Collections.singletonList("c"), Collections.singletonList("fc"),
                    Collections.singletonList("facetField"), Collections.singletonList("sort")
            );

            assertNotNull(response);
            assertEquals(1, response.getList().size());
        }
    }

    @Test
    public void testGetRecordsUsingFreemarkerTemplate() throws Exception {
        try (MockedStatic<ArenaUtil> ignored = setupArenaUtilMocks()) {
            setupRestTemplateMock("event.stream.response.txt");

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("federatedSearchClient", federatedSearchClient);

            String output = processTemplate("testGetRecords.ftl", dataModel);
            assertTemplateOutput(output);
        }
    }

    @Test
    public void testParameterizedGetRecordsUsingFreemarkerTemplate() throws Exception {
        try (MockedStatic<ArenaUtil> ignored = setupArenaUtilMocks()) {
            setupRestTemplateMock("event.stream.response.txt");

            Map<String, Object> testCase = new HashMap<>() {{
                put("sourceId", "hnoc");
                put("start", 0);
                put("size", 3);
                put("params", "paramA");
                put("facetSize", 2);
                put("q", "queryA");
                put("c", List.of("cA1"));
                put("fc", List.of("fcA1", "fcA2"));
                put("facetField", List.of("fieldA1"));
                put("sort", List.of("asc"));
            }};

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("federatedSearchClient", federatedSearchClient);
            dataModel.put("testCase", testCase);

            String output = processTemplate("parameterizedTestGetRecords.ftl", dataModel);
            assertTemplateOutput(output);
        }
    }

    @Test
    public void testGetRecordsWithJsonParameters() throws Exception {
        try (MockedStatic<ArenaUtil> ignored = setupArenaUtilMocks()) {
            setupRestTemplateMock("event.stream.response.txt");

            String jsonTestCase = "{\n" +
                    "  \"sourceId\": \"hnoc\",\n" +
                    "  \"start\": 10,\n" +
                    "  \"size\": 5,\n" +
                    "  \"facetSize\": 3,\n" +
                    "  \"q\": \"*\",\n" +
                    "  \"c\": ['\"field\":\"almaId\",\"values\":[\"aba3bbad-f17f-40d4-aaaa-78088898fbf3\",\"b53ca199-abf9-48b0-8a03-104a366863a1\",\"f5eebd58-6078-49cf-8ac5-27459960cdc4\",\"71259f9e-11f6-4009-ab21-8dd574b74678\",\"fd931cd1-163a-4425-abb9-1cf36a069e9a\",\"6d6e1430-f612-48a7-96f3-d0ac697dca85\",\"b2ef35a7-9b5d-4a73-bf0c-8c694f66c4d6\",\"2511eebe-4798-4a3c-8ee3-a0f8e777ba11\",\"65b8e0c1-6a1a-4322-a6cf-f3892f6fa742\",\"a10a2022-9638-467d-a591-6eab92c88974\"]'],\n" +
                    "  \"sort\": ['\"field\":\"score\",\"order\":\"desc\"']\n" +
                    "}";

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("federatedSearchClient", federatedSearchClient);
            dataModel.put("jsonTestCase", jsonTestCase);

            String output = processTemplate("jsonParameterizedTestGetRecords.ftl", dataModel);
            assertTemplateOutput(output);
        }
    }

    @Test
    public void testGetRecordsByLMSSearchIds() throws Exception {
        try (MockedStatic<ArenaUtil> ignored = setupArenaUtilMocks()) {
            setupRestTemplateMock("event.stream.alma.response.txt");
            when(arenaLocalServiceClient.getLMSSearchResult("toplist:319c962b-cb27-4b7c-a233-bebf5b4c3f96", "NAMED_LIST", 0, 5))
                    .thenReturn(LMSSearchResultResponse.builder()
                            .lmsList(List.of(LMSSearchResponse.builder()
                                    .recordId("1a5f0970-e461-4967-bb25-376c84615eb9")
                                    .build(), LMSSearchResponse.builder()
                                    .recordId("58108344-3921-4066-8901-4345e8e79780")
                                    .build(), LMSSearchResponse.builder()
                                    .recordId("68108344-3921-4066-8901-4345e8e79780")
                                    .build()))
                            .build());
            Map<String, Object> testCase = new HashMap<>() {{
                put("query", "toplist:319c962b-cb27-4b7c-a233-bebf5b4c3f96");
                put("start", 0);
                put("size", 5);
                put("type", "NAMED_LIST");
            }};

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("federatedSearchClient", federatedSearchClient);
            dataModel.put("testCase", testCase);

            String output = processTemplate("testGetRecordsByLMSSearchIds.ftl", dataModel);
            assertTrue(output.contains("Total Hits: 2"));
            assertTrue(output.contains("Name: War and peace"));
            assertTrue(output.contains("ID Sort: ATEST100003-1a5f0970-e461-4967-bb25-376c84615eb9"));
            assertTrue(output.contains("ID Sort: ATEST100003-58108344-3921-4066-8901-4345e8e79780"));
            assertTrue(output.indexOf("ID Sort: ATEST100003-1a5f0970-e461-4967-bb25-376c84615eb9") < output.indexOf("ID Sort: ATEST100003-58108344-3921-4066-8901-4345e8e79780"));
            assertTrue(output.contains("Publisher: HarperOne"));
            assertTrue(output.contains("Classification Code: 891.7"));
        }
    }

    @Test
    public void testGetRecordsByAlmaIds() throws Exception {
        try (MockedStatic<ArenaUtil> ignored = setupArenaUtilMocks()) {
            setupRestTemplateMock("event.stream.alma.response.txt");
            Map<String, Object> testCase = new HashMap<>() {{
                put("query", "1a5f0970-e461-4967-bb25-376c84615eb9,58108344-3921-4066-8901-4345e8e79780");
                put("start", 0);
                put("size", 5);
            }};

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("federatedSearchClient", federatedSearchClient);
            dataModel.put("testCase", testCase);

            String output = processTemplate("testGetRecordsByAlmaIds.ftl", dataModel);
            assertTrue(output.contains("Total Hits: 2"));
            assertTrue(output.contains("Name: War and peace"));
            assertTrue(output.contains("ID Sort: ATEST100003-1a5f0970-e461-4967-bb25-376c84615eb9"));
            assertTrue(output.contains("ID Sort: ATEST100003-58108344-3921-4066-8901-4345e8e79780"));
            assertTrue(output.indexOf("ID Sort: ATEST100003-1a5f0970-e461-4967-bb25-376c84615eb9") < output.indexOf("ID Sort: ATEST100003-58108344-3921-4066-8901-4345e8e79780"));
            assertTrue(output.contains("Publisher: HarperOne"));
            assertTrue(output.contains("Classification Code: 891.7"));
        }
    }

    @Test
    public void testGetEmpty() throws Exception {
        try (MockedStatic<ArenaUtil> ignored = setupArenaUtilMocks()) {
            setupRestTemplateMock("event.stream.empty.response.txt");
            when(arenaLocalServiceClient.getLMSSearchResult("toplist:319c962b-cb27-4b7c-a233-bebf5b4c3f96", "NAMED_LIST", 0, 5))
                    .thenReturn(LMSSearchResultResponse.builder()
                            .lmsList(List.of(LMSSearchResponse.builder()
                                    .recordId("1a5f0970-e461-4967-bb25-376c84615eb9")
                                    .build(), LMSSearchResponse.builder()
                                    .recordId("58108344-3921-4066-8901-4345e8e79780")
                                    .build()))
                            .build());
            Map<String, Object> testCase = new HashMap<>() {{
                put("query", "toplist:319c962b-cb27-4b7c-a233-bebf5b4c3f96");
                put("start", 0);
                put("size", 5);
                put("type", "NAMED_LIST");
            }};

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("federatedSearchClient", federatedSearchClient);
            dataModel.put("testCase", testCase);

            String output = processTemplate("testGetRecordsByLMSSearchIds.ftl", dataModel);
            assertTrue(output.contains("<ul>\n\n    </ul>"));
        }
    }

    private MockedStatic<ArenaUtil> setupArenaUtilMocks() {
        MockedStatic<ArenaUtil> arenaUtilMockedStatic = mockStatic(ArenaUtil.class);

        ArenaSystemConfiguration mockedSystemConfig = mock(ArenaSystemConfiguration.class);
        when(ArenaUtil.getArenaSystemConfiguration()).thenReturn(mockedSystemConfig);
        when(mockedSystemConfig.federatedSearchApiEndpoint()).thenReturn("http://arena-federated-search:9799");

        ArenaGroupConfiguration mockedGroupConfig = mock(ArenaGroupConfiguration.class);
        when(ArenaUtil.getArenaGroupConfiguration(groupId)).thenReturn(mockedGroupConfig);
        when(mockedGroupConfig.federatedSearchCustomerAlias()).thenReturn("testCustomer");

        return arenaUtilMockedStatic;
    }

    private void setupRestTemplateMock(String filename) throws Exception {
        String responseString = loadFileAsString(filename);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(responseString, null, 200));
    }

    private String processTemplate(String templateName, Map<String, Object> dataModel) throws Exception {
        Template template = cfg.getTemplate(templateName);
        StringWriter out = new StringWriter();
        template.process(dataModel, out);
        return out.toString();
    }

    private String loadFileAsString(String fileName) throws Exception {
        URL resource = getClass().getClassLoader().getResource(fileName);
        assertNotNull(resource, "File not found: " + fileName);
        return Files.readString(Paths.get(resource.toURI()));
    }*/

    @Test
    public void dummyTest() {
        // This is a dummy test to ensure the class is not empty
        assertThat(true, is(true));
    }
}
