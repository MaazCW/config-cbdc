package com.opl.cbdc.config.thirdparty.digio;

import com.opl.cbdc.config.domain.*;
import com.opl.cbdc.config.repository.*;
import com.opl.cbdc.config.utils.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.text.*;
import java.util.*;

@Component
@Slf4j
public class DigioAPI {

	private static final String INVALID_PAN = "INVALID PAN";
	@Autowired
	AnsProperties ansProperties;

	@Autowired
	DIGIOAPIAuditRepo digioAPIAuditRepo;

	public DigioPanResponse panVerificationDIGIO(DigioPanRequest panProxy) throws Exception {
		DIGIOAPIAudit digioAPIAudit = new DIGIOAPIAudit();
		try {

			digioAPIAudit.setPan(panProxy.getPan());
			digioAPIAudit.setCreatedDate(new Date());
			digioAPIAudit.setIsActive(Boolean.FALSE);
			digioAPIAudit.setRequest(panProxy.toString());
			digioAPIAudit.setApplicationId(panProxy.getApplicationId());
			digioAPIAudit.setCoAppId(panProxy.getCoAppId());
			
			String url = ansProperties.getValue("DIGIO_PAN_VALID_API");
			String encodeToString = Base64.getEncoder().encodeToString(
					(ansProperties.getValue("DIGIO_CLIENT_ID") + ":" + ansProperties.getValue("DIGIO_CLIENT_SECRET"))
							.getBytes());

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("authorization", "Basic " + encodeToString);

			Map<String, Object> map = new HashMap<>();
			map.put("pan_no", panProxy.getPan());
			map.put("full_name", panProxy.getName());
			map.put("date_of_birth", new SimpleDateFormat("YYYY-MM-dd").format(panProxy.getDob()));

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

			
			DigioPanResponse digioPanResponse = restTemplate
					.exchange(url, HttpMethod.POST, entity, DigioPanResponse.class).getBody();

			if (digioPanResponse != null) {
				
				if (!INVALID_PAN.equalsIgnoreCase(digioPanResponse.getError_message())) {
					digioAPIAudit.setMessage(digioPanResponse.getError_message());
					digioAPIAudit.setIsActive(Boolean.TRUE);
					digioPanResponse.setFlag(Boolean.TRUE);
				} else {
					digioAPIAudit.setMessage(digioPanResponse.getError_message());
					digioPanResponse.setFlag(Boolean.FALSE);
				}
				digioAPIAudit.setResponse(digioPanResponse.toString());
				return digioPanResponse;
			}
			digioAPIAudit.setMessage("No Data Found");
			log.info("DIGIO PAN No data found for " + panProxy.getPan() + " ===> Response Is Null ====>");

		} catch (Exception e) {
			digioAPIAudit.setMessage(e.getMessage());
			log.error("Exception while Get DIGIO PAN API For " + panProxy.getPan(), e);
		} finally {
			digioAPIAudit.setModifiedDate(new Date());
			digioAPIAuditRepo.save(digioAPIAudit);
		}
		return null;

	}



}
